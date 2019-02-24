package Genesis;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Scanner;

public class CourseGUI extends Application implements EventHandler
{
	private Button button;
	private static GenesisAccessor accessor;
	
	public CourseGUI(String usr, String pass, boolean fast, boolean time, String markingPeriod, Stage primaryStage)
	{
		accessor = new GenesisAccessor(usr, pass, false, time, markingPeriod);
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
	{ //String usr, String pass, boolean fast, boolean time, String markingPeriod
		/*String pd = PasswordField.readPassword("Enter pass- ");
		
		if (pd.charAt(0) == '*')
		{
			pd = pd.substring(1);
		}
		
		String markingPeriod = "MP2";
		boolean time = false;
		
		for (String arg : args)
		{
			if (arg.equals("-t"))
			{
				time = true;
			}
			else if (arg.contains("MP"))
			{
				markingPeriod = arg;
			}
		}*/
		
		/*boolean time = false;
		String markingPeriod = "MP2";
		String pd;
		
		Scanner x = new Scanner(System.in);
		
		pd = x.nextLine();*/
		
		//accessor = new GenesisAccessor("10015935", pd, false, time, markingPeriod);
		
		launch(args);
	}

	//private int i;
	private int x;
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		//StackPane layout = new StackPane();
		
		primaryStage.setTitle("Grades");
		
		VBox[] layouts = new VBox[accessor.getNumCourses()];
		
		Button[] buttons = new Button[accessor.getNumCourses()];
		Label[] labels = new Label[accessor.getNumCourses()];
		
		Scene[] courseScenes = new Scene[accessor.getNumCourses()];
		
		String[] courseIDs = accessor.getCourseIDs();
		
		System.out.println(accessor.getNumCourses() + " courses.\n");
		
		for (String id : courseIDs)
		{
			System.out.println(accessor.getCourse(id));
		}
		
		for (x = 0; x < accessor.getNumCourses(); x++)
		{
			//System.out.println("x = " + x);
			layouts[x] = new VBox(10);
			//labels[x] = new Label(accessor.getCourseNames()[x]);
			buttons[x] = new Button(accessor.getCourseNames()[((x < accessor.getNumCourses() - 1) ? x + 1 : 0)]);
			
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
			
			courseScenes[x] = new CourseScene(layouts[x], 1280, 720, accessor.getCourse(courseIDs[x]));
		}
		
		x = 0;
		
		//String[] courseIDs = accessor.getCourses().keySet().toArray();
		
		//System.out.println("\nCheckPoint 1\n");
		
		/*for (i = 0; i < courseScenes.length; i++)
		{
			//System.out.println("\nCheckPoint 2\n");
			
			//System.out.println(courseIDs[i]);
			
			/*buttons[i].setOnAction(e -> 
			{
				primaryStage.setScene(courseScenes[((i < courseScenes.length - 1) ? i + 1 : 0)]);
				//primaryStage.
			});* /
			
			//System.out.println("\nCheckPoint 3\n");
			
			layouts[i].getChildren().add(buttons[i]);
			layouts[i].getChildren().add(labels[i]);
			
			//System.out.println("\n" + accessor.getCourse(courseIDs[i]) + "\n");
			
			courseScenes[i] = new CourseScene(layouts[i], 1280, 720, accessor.getCourse(courseIDs[i]));
		}*/
		
		primaryStage.setScene(courseScenes[0]);
		primaryStage.show();
	}

	@Override
	public void handle(Event event)
	{
		if (event.getSource() == button)
		{
			System.out.println("button");
		}
	}
	
	
}