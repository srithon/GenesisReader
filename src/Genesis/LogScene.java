package Genesis;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class LogScene extends Scene
{
	private Button returnButton;
	
	public LogScene(Parent root, double width, double height, Button returnButton)
	{
		super(root, width, height);
		
		this.returnButton = returnButton;
		returnButton.setDisable(true);
	}
	
	public void enableReturnButton()
	{
		returnButton.setDisable(false);
	}
}
