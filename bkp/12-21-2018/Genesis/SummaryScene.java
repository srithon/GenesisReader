package Genesis;

import java.util.HashMap;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SummaryScene extends Scene
{
	public SummaryScene(VBox layout, int d1, int d2, HashMap<String, Course> courses)
	{
		super(layout, d1, d2);
		
		HBox title = new HBox();
		
		Label titleLabel = new Label("Summary");
		titleLabel.setAlignment(Pos.CENTER);
		titleLabel.setFont(new Font("Arial", 36.0));
		
		title.getChildren().add(titleLabel);
		
		layout.getChildren().add(title);
		
		HBox[] courseBoxes = new HBox[courses.size()];
		
		String[] courseIDs = courses.keySet().toArray(new String[courses.size()]);
		String[] courseNames = new String[courseIDs.length];
		
		for (int i = 0; i < courseBoxes.length; i++)
		{
			courseNames[i] = courses.get(courseIDs[i]).getName();
			
			courseBoxes[i] = new HBox(5);
			
			courseBoxes[i].getChildren().add(new Label(courseNames[i]));
			courseBoxes[i].getChildren().add(new Label(courses.get(courseIDs[i]).getAverageString()));
			
			Assignment lastAssignment = courses.get(courseIDs[i]).getLastGradedAssignment();
			
			if (lastAssignment != null)
			{
				courseBoxes[i].getChildren().add(new Label(lastAssignment.toString()));
			}
			
			layout.getChildren().add(courseBoxes[i]);
		}
	}
}