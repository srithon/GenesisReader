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
		//Label[] labels = new Label[accessor.getNumCourses()];
		
		Scene[] courseScenes = new Scene[accessor.getNumCourses()];
		
		String[] courseIDs = accessor.getCourseIDs();
		
		if (debug)
			System.out.println(accessor.getNumCourses() + " courses.\n");
		
		if (debug)
		{
			for (String id : courseIDs)
			{
				System.out.println(accessor.getCourse(id));
			}
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
}