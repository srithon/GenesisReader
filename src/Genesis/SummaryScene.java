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
	public SummaryScene(VBox layout, int d1, int d2, MarkingPeriod[] markingPeriods)
	{
		super(layout, d1, d2);
		
		int lastMarkingPeriod = -1;
		
		for (int i = 0; i < markingPeriods.length; i++)
		{
			if (!markingPeriods[i].hasGrades())
			{
				lastMarkingPeriod = i;
				break;
			}
		}
		
		HBox title = new HBox();
		
		Label titleLabel = new Label("Summary");
		titleLabel.setAlignment(Pos.CENTER);
		titleLabel.setFont(new Font("Arial", 36.0));
		
		Label unweightedGPA = new Label("Unweighted GPA - " + GenesisAccessor.getUnweightedGPA());
		//Label weightedGPA = new Label("Weighted GPA - " + GenesisAccessor.getWeightedGPA());
		
		title.getChildren().add(titleLabel);
		
		title.getChildren().addAll(unweightedGPA/*, weightedGPA*/);
		
		layout.getChildren().add(title);
		
		HBox[] courseBoxes = new HBox[markingPeriods[lastMarkingPeriod].getHashMap().size()];
		
		String[] courseIDs = markingPeriods[lastMarkingPeriod].getHashMap().keySet().toArray(new String[markingPeriods[lastMarkingPeriod].getHashMap().size()]);
		String[] courseNames = new String[courseIDs.length];
		
		for (int i = 0; i < courseBoxes.length; i++)
		{
			courseNames[i] = markingPeriods[lastMarkingPeriod].retrieveFromHashMap(courseIDs[i]).getName();
			
			courseBoxes[i] = new HBox(5);
			
			courseBoxes[i].getChildren().add(new Label(courseNames[i]));
			courseBoxes[i].getChildren().add(new Label(markingPeriods[lastMarkingPeriod].retrieveFromHashMap(courseIDs[i]).getAverageString()));
			
			Assignment lastAssignment = markingPeriods[lastMarkingPeriod].retrieveFromHashMap(courseIDs[i]).getLastGradedAssignment();
			
			if (lastAssignment != null)
			{
				courseBoxes[i].getChildren().add(new Label(lastAssignment.toString()));
			}
			
			layout.getChildren().add(courseBoxes[i]);
		}
	}
}