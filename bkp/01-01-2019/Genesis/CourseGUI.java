package Genesis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openqa.selenium.WebDriverException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CourseGUI
{
	//private Button button;
	private static GenesisAccessor accessor;
	private boolean debug;
	
	private static VBox[] layouts;
	private static Button[] buttons;
	private static Scene[] courseScenes;
	private static String[] courseIDs;
	
	private Button goToLogsButton;
	
	private Stage primaryStage;
	
	public CourseGUI(String usr, String pass, boolean fast, boolean time, String markingPeriod, boolean debug, Stage primaryStage)
	{
		LoginGUI.clearPassField();
		
		for (int attempt = 0; attempt < 3; attempt++)
		{
			try
			{
				accessor = new GenesisAccessor(usr, pass, fast, time, markingPeriod, debug);
				break;
			}
			catch (WebDriverException e)
			{
				System.out.println("Attempt " + attempt + " to initialize failed."
						+ ((attempt == 2) ? " Please try again later." : " Commencing attempt " + (attempt + 1)));
			}
			
			if (attempt == 2)
			{
				System.out.println("Failed.");
			}
		}
		
		this.primaryStage = primaryStage;
		
		start();
	}

	private static int x;
	
	public void start()
	{
		layouts = new VBox[accessor.getNumCourses() + 1];
		
		buttons = new Button[accessor.getNumCourses() + 1];
		
		courseScenes = new Scene[accessor.getNumCourses() + 1];
		
		layouts[0] = new VBox(10);
		
		buttons[0] = new Button();
		buttons[0].setOnAction(e -> primaryStage.setScene(courseScenes[++x]));
		
		layouts[0].getChildren().add(buttons[x]);
		
		courseScenes[0] = new SummaryScene(layouts[0], 1280, 720, accessor.getCourses());
		
		buttons[0].setAlignment(Pos.TOP_CENTER);
		
		courseIDs = accessor.getCourseIDs();
		
		if (debug)
		{
			System.out.println(accessor.getNumCourses() + " courses.\n");
		
			for (String id : courseIDs)
			{
				System.out.println(accessor.getCourse(id));
			}
		}
		
		/*
		 * ExecutorService executor = Executors.newFixedThreadPool(5);
		 */
		
		long startTime = System.nanoTime();
		
		ExecutorService executor = Executors.newFixedThreadPool(4);
		
		for (x = 1; x < courseScenes.length; x++)
		{
			Runnable worker = new courseSceneSetupper(x);
            executor.execute(worker);
		}
		
		executor.shutdown();
		
	    while (!executor.isTerminated())
	    {
	    }
	    
	    double duration = System.nanoTime() - startTime;
	    duration /= 1000000000;
	    
	    System.out.println("Completed task in " + duration + " seconds");
	        
		buttons[0].setText(accessor.getCourseNames()[0]);
		
		x = 0;
	}
	
	public Scene[] getCourseScenes()
	{
		return courseScenes;
	}
	
	private class courseSceneSetupper implements Runnable
	{
		private int index;
		
		public courseSceneSetupper(int index)
		{
			this.index = index;
		}
		
		@Override
		public void run()
		{
			layouts[index] = new VBox(10);
			buttons[index] = new Button(index == courseScenes.length - 1 ? "Summary" : accessor.getCourseNames()[index]);
			//courseScenes includes summary scene, so courseScenes - 2 is the last course
			//SUM 	1 2 3 4 5 6 7 8
			//1 	2 3 4 5 6 7 8		getCourseNames()
			
			if (index < (courseScenes.length - 1))
			{
				//System.out.println(x + " < " + (courseScenes.length - 1));
				buttons[index].setOnAction(e -> primaryStage.setScene(courseScenes[++x]));
			}
			else
			{
				//System.out.println(x + " >= " + (courseScenes.length - 1));
				buttons[index].setOnAction(e ->
				{
					primaryStage.setScene(courseScenes[0]);
					x = 0;
				});
			}
			
			buttons[index].setAlignment(Pos.TOP_CENTER);
			
			layouts[index].getChildren().add(buttons[index]);
			layouts[index].getChildren().add(new goToLogsButton());
			//layouts[x].getChildren().add(labels[x]);
			
			courseScenes[index] = new CourseScene(layouts[index], 1280, 720, accessor.getCourse(courseIDs[index - 1]));
		}
	}
	
	private class goToLogsButton extends Button
	{
		public goToLogsButton()
		{
			super();
			
			setText("Go to logs");
			
			setOnAction(e ->
			{
				Platform.runLater(() -> primaryStage.setScene(LoginGUI.logs));
			});
		}
	}
}