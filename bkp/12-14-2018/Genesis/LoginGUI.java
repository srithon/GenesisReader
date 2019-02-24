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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Scanner;

public class LoginGUI extends Application
{
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
		
		VBox layout = new VBox(10);
		
		final Label message = new Label("");
		
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER_LEFT);
		
		Label label = new Label("Password ");
		
		final javafx.scene.control.PasswordField passField = new javafx.scene.control.PasswordField();
		
		HBox hb2 = new HBox();
		Label user = new Label("Username ");
		TextField usernameField = new TextField();
		
		HBox toggleButtons = new HBox();
		
		ToggleButton fastYN = new ToggleButton("Fast mode?");
		ToggleButton timeYN = new ToggleButton("Time program?");
		TextField markingPeriodField = new TextField();
		markingPeriodField.setPromptText("Marking Period");
		
		toggleButtons.getChildren().addAll(fastYN, timeYN, markingPeriodField);
		
		VBox submit = new VBox(5);
		
		Button submitButton = new Button("Submit");
		
		hb2.setAlignment(Pos.CENTER);
		hb.setAlignment(Pos.CENTER);
		submit.setAlignment(Pos.BOTTOM_CENTER);
		
		submitButton.setOnAction(e ->
		{
			CourseGUI gui = new CourseGUI(usernameField.getText(), passField.getText(), fastYN.isSelected(), timeYN.isSelected(), markingPeriodField.getText(), primaryStage);
			passField.clear();
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
		
		
		primaryStage.setScene(new Scene(layout, 330, 200));
		
		primaryStage.show();
	}
}
