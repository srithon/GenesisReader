package Genesis;

import org.openqa.selenium.WebDriverException;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CourseGUI extends Application
{
	//private Button button;
	private static GenesisAccessor accessor;
	private boolean debug;
	
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
		
		try
		{
			start(primaryStage);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}

	private int x;
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		//StackPane layout = new StackPane();
		
		primaryStage.setTitle("Grades");
		
		VBox[] layouts = new VBox[accessor.getNumCourses() + 1];
		
		Button[] buttons = new Button[accessor.getNumCourses() + 1];
		
		Scene[] courseScenes = new Scene[accessor.getNumCourses() + 1];
		
		layouts[0] = new VBox(10);
		
		buttons[0] = new Button();
		buttons[0].setOnAction(e -> primaryStage.setScene(courseScenes[++x]));
		
		layouts[0].getChildren().add(buttons[x]);
		
		courseScenes[0] = new SummaryScene(layouts[0], 1280, 720, accessor.getCourses());
		
		buttons[0].setAlignment(Pos.TOP_CENTER);
		
		String[] courseIDs = accessor.getCourseIDs();
		
		if (debug)
		{
			System.out.println(accessor.getNumCourses() + " courses.\n");
		
			for (String id : courseIDs)
			{
				System.out.println(accessor.getCourse(id));
			}
		}
		
		for (x = 1; x < courseScenes.length; x++)
		{
			layouts[x] = new VBox(10);
			buttons[x] = new Button(x == courseScenes.length - 1 ? "Summary" : accessor.getCourseNames()[x]);
			//courseScenes includes summary scene, so courseScenes - 2 is the last course
			//SUM 	1 2 3 4 5 6 7 8
			//1 	2 3 4 5 6 7 8		getCourseNames()
			
			if (x < (courseScenes.length - 1))
			{
				//System.out.println(x + " < " + (courseScenes.length - 1));
				buttons[x].setOnAction(e -> primaryStage.setScene(courseScenes[++x]));
			}
			else
			{
				//System.out.println(x + " >= " + (courseScenes.length - 1));
				buttons[x].setOnAction(e ->
				{
					primaryStage.setScene(courseScenes[0]);
					x = 0;
				});
			}
			
			buttons[x].setAlignment(Pos.TOP_CENTER);
			
			layouts[x].getChildren().add(buttons[x]);
			//layouts[x].getChildren().add(labels[x]);
			
			courseScenes[x] = new CourseScene(layouts[x], 1280, 720, accessor.getCourse(courseIDs[x - 1]));
		}
		
		buttons[0].setText(accessor.getCourseNames()[1]);
		
		x = 0;
		
		primaryStage.setScene(courseScenes[0]);
		primaryStage.show();
	}
}