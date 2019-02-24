package Genesis;

import java.io.PrintStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginGUI extends Application
{
	private static javafx.scene.control.PasswordField passField;
	private static Thread accessorThread;
	
	private CourseGUI gui;
	
	public static PrintStream stream;
	
	public static TextArea log;
	
	public static LogScene logs;
	
	private Scene loginScreen;
	
	private Scene[] courseScenes;
	
	public LoginGUI()
	{
		
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("Login");
		
		VBox logLayout = new VBox(5);
		
		Button goBackToLoginScreen = new Button("Return To Login Screen");
		
		log = new TextArea();
		log.setPrefSize(1000, 1000);
		
		Button returnButton = new Button("Return To Grades");
		returnButton.setOnAction(e -> 
		{
			primaryStage.setScene(courseScenes[0]);
		});
		
		logLayout.getChildren().add(returnButton);
		logLayout.getChildren().add(goBackToLoginScreen);
		logLayout.getChildren().add(log);
		logs = new LogScene(logLayout, 1000, 1000, returnButton);
		
		String username = "";
		String markingPeriod = "";
		boolean fast = false;
		boolean time = false;
		
		passField = new javafx.scene.control.PasswordField();
		
		VBox layout = new VBox(10);
		
		final Label message = new Label("");
		
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER_LEFT);
		
		Label label = new Label("Password ");
		
		HBox hb2 = new HBox();
		Label user = new Label("Username ");
		TextField usernameField = new TextField();
		
		HBox toggleButtons = new HBox();
		
		ToggleButton fastYN = new ToggleButton("Fast mode?");
		ToggleButton timeYN = new ToggleButton("Time program?");
		ToggleButton debugYN = new ToggleButton("Debugging?");
		
		/*TextField markingPeriodField = new TextField();
		markingPeriodField.setPromptText("Marking Period");*/
		
		ToggleGroup markingPeriodButtons = new ToggleGroup();
		
		ToggleButton mP1Button = new ToggleButton("MP1");
		ToggleButton mP2Button = new ToggleButton("MP2");
		ToggleButton mP3Button = new ToggleButton("MP3");
		ToggleButton mP4Button = new ToggleButton("MP4");
		
		Button goToLogButton = new Button("View Logs");
		
		goToLogButton.setOnAction(e ->
		{
			primaryStage.setScene(logs);
		});
		
		mP1Button.setToggleGroup(markingPeriodButtons);
		mP2Button.setToggleGroup(markingPeriodButtons);
		mP3Button.setToggleGroup(markingPeriodButtons);
		mP4Button.setToggleGroup(markingPeriodButtons);
		
		mP1Button.setUserData("MP1");
		mP2Button.setUserData("MP2");
		mP3Button.setUserData("MP3");
		mP4Button.setUserData("MP4");
		
		toggleButtons.getChildren().addAll(fastYN, timeYN, debugYN, mP1Button, mP2Button, mP3Button, mP4Button, goToLogButton);
		
		VBox submit = new VBox(5);
		
		Button submitButton = new Button("Submit");
		
		hb2.setAlignment(Pos.CENTER);
		hb.setAlignment(Pos.CENTER);
		submit.setAlignment(Pos.BOTTOM_CENTER);
		
		submitButton.setOnAction(e ->
		{
			primaryStage.setScene(logs);
			
			accessorThread = new Thread()
					{
						@Override
						public void run()
						{
							double startTime = System.nanoTime();
							
							gui = new CourseGUI(usernameField.getText(), passField.getText(), fastYN.isSelected(), timeYN.isSelected(), (String) (markingPeriodButtons.getSelectedToggle().getUserData()), debugYN.isSelected(), primaryStage);
						
							Platform.runLater(new Runnable()
									{
										@Override
										public void run()
										{
											courseScenes = gui.getCourseScenes();
											
											primaryStage.setScene(courseScenes[0]);
											
											System.out.println("Total duration : " + (System.nanoTime() - startTime) / 1000000000);
										
											logs.enableReturnButton();
										}
									});
						}
					};
					
			accessorThread.start();
		});
		
		submit.getChildren().add(submitButton);
		
		hb2.getChildren().addAll(user, usernameField);
		
		hb.getChildren().addAll(label, passField);
		
		layout.getChildren().addAll(toggleButtons);
		layout.getChildren().addAll(hb2, message);
		layout.getChildren().addAll(hb);
		layout.getChildren().add(new HBox(5));
		layout.getChildren().add(submit);
		
		loginScreen = new Scene(layout, 484, 200);
		
		goBackToLoginScreen.setOnAction(e ->
		{
			primaryStage.setScene(loginScreen);
		});
		
		primaryStage.setScene(loginScreen);
		
		primaryStage.show();
		
		//configure printstream
		
		stream = new PrintStream(new Console(log), true);
		
		System.setOut(stream);
		System.setErr(stream);
	}
	
	public static void clearPassField()
	{
		passField.clear();
	}
}
