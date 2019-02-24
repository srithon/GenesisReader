package Genesis;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Scanner;

public class LoginGUI extends Application
{
	/*private class clearPassField extends javafx.scene.control.PasswordField
	{
		public String clear()
		{
			super.clear();
		}
	}*/
	
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
		
		String username = "";
		String markingPeriod = "";
		boolean fast = false;
		boolean time = false;
		
		final javafx.scene.control.PasswordField passField = new javafx.scene.control.PasswordField();
		
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
		
		mP1Button.setToggleGroup(markingPeriodButtons);
		mP2Button.setToggleGroup(markingPeriodButtons);
		mP3Button.setToggleGroup(markingPeriodButtons);
		mP4Button.setToggleGroup(markingPeriodButtons);
		
		mP1Button.setUserData("MP1");
		mP2Button.setUserData("MP2");
		mP3Button.setUserData("MP3");
		mP4Button.setUserData("MP4");
		
		toggleButtons.getChildren().addAll(fastYN, timeYN, debugYN, mP1Button, mP2Button, mP3Button, mP4Button);
		
		VBox submit = new VBox(5);
		
		Button submitButton = new Button("Submit");
		
		hb2.setAlignment(Pos.CENTER);
		hb.setAlignment(Pos.CENTER);
		submit.setAlignment(Pos.BOTTOM_CENTER);
		
		submitButton.setOnAction(e ->
		{
			CourseGUI gui = new CourseGUI(usernameField.getText(), passField.getText(), fastYN.isSelected(), timeYN.isSelected(), (String) (markingPeriodButtons.getSelectedToggle().getUserData()), debugYN.isSelected(), primaryStage);
			//gui.start(primaryStage);
		});
		
		submit.getChildren().add(submitButton);
		
		hb2.getChildren().addAll(user, usernameField);
		
		hb.getChildren().addAll(label, passField);
		
		layout.getChildren().addAll(toggleButtons);
		layout.getChildren().addAll(hb2, message);
		layout.getChildren().addAll(hb);
		layout.getChildren().add(new HBox(5));
		layout.getChildren().add(submit);
		
		primaryStage.setScene(new Scene(layout, 415, 200));
		
		primaryStage.show();
	}
}
