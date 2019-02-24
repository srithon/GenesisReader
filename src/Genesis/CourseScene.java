package Genesis;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CourseScene extends Scene
{
	/*@SuppressWarnings("unused")
	private Course course;*/
	
	private ArrayList<Assignment> assignments;
	//Button[] assignmentButtons;
	
	private TableView<Assignment> table = new TableView<Assignment>(); //type safety?
	
	@SuppressWarnings("unchecked")
	public CourseScene(VBox layout, int d1, int d2, Course course)
	{
		super(layout, d1, d2);
		
		//this.course = course;
		assignments = course.getAssignments();
		//assignmentButtons = new Button[assignments.size()];
		
		Button getAverage = new Button("Get Average");
		getAverage.setOnAction(e -> AlertBox.display("Average", ("Your average in " + course.getName() + " is " + course.getAverageString())));
		
		Button getTotalReceivedPoints = new Button("Get Total Received Points");
		getTotalReceivedPoints.setOnAction(e -> AlertBox.display("Total Received Points", "You have received a total of " + course.getTotalReceivedPoints() + " points."));
		
		Button getTotalTotalPoints = new Button("Get Total Points");
		getTotalTotalPoints.setOnAction(e -> AlertBox.display("Total Points", "All of the assignments in this course add up to a total of " + course.getTotalTotalPoints() + " points."));
		
		HBox functions = new HBox(5);
		
		functions.getChildren().add(getAverage);
		functions.getChildren().add(getTotalReceivedPoints);
		functions.getChildren().add(getTotalTotalPoints);
		
		functions.setAlignment(Pos.CENTER);
		
		//other functions
		
		layout.getChildren().add(functions);
		
		/*for (Assignment i : assignments)
		{
			layout.getChildren().add(new Button(i.toString()));
		}*/
		
		//new code
		
		HBox titleBox = new HBox();
		
		final Label tableTitle = new Label(course.getName());
		tableTitle.setFont(new Font("Arial", 20));
		
		tableTitle.setAlignment(Pos.CENTER);
		
		titleBox.getChildren().add(tableTitle);
		
		table.setEditable(true);
		
		ObservableList<Assignment> tableData = FXCollections.observableArrayList(assignments);
		
		TableColumn<Assignment, String> assignmentCategoryCol = new TableColumn<Assignment, String>("Category");
		assignmentCategoryCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, String>("assignmentCategory"));
		
		TableColumn<Assignment, String> assignmentNameCol = new TableColumn<Assignment, String>("Name");
		assignmentNameCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, String>("assignmentName"));
		
		TableColumn<Assignment, String> assignmentDueDateCol = new TableColumn<Assignment, String>("Date");
		assignmentDueDateCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, String>("date"));
		//new
		TableColumn<Assignment, String> assignmentGradeLetterCol = new TableColumn<Assignment, String>("Grade");
		assignmentGradeLetterCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, String>("assignmentGradeLetter"));
		
		TableColumn<Assignment, String> assignmentMarkingPeriodCol = new TableColumn<Assignment, String>("Marking Period");
		assignmentMarkingPeriodCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, String>("markingPeriod"));
		
		TableColumn<Assignment, Double> assignmentUnweightedPointsCol = new TableColumn<Assignment, Double>("Unweighted Points"); //type safety?
		TableColumn<Assignment, Double> assignmentWeightedPointsCol = new TableColumn<Assignment, Double>("Weighted Points"); //type safety?
		
		TableColumn<Assignment, Double> assignmentUnweightedReceivedPointsCol = new TableColumn<Assignment, Double>("Received Points");
		assignmentUnweightedReceivedPointsCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, Double>("unweightedReceivedPoints"));
		
		TableColumn<Assignment, Double> assignmentUnweightedTotalPointsCol = new TableColumn<Assignment, Double>("Total Points");
		assignmentUnweightedTotalPointsCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, Double>("unweightedTotalPoints"));
		
		TableColumn<Assignment, Double> assignmentWeightedReceivedPointsCol = new TableColumn<Assignment, Double>("Received Points");
		assignmentWeightedReceivedPointsCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, Double>("weightedReceivedPoints"));
		
		TableColumn<Assignment, Double> assignmentWeightedTotalPointsCol = new TableColumn<Assignment, Double>("Total Points");
		assignmentWeightedTotalPointsCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, Double>("weightedTotalPoints"));
		
		TableColumn<Assignment, Double> assignmentWeightingCol = new TableColumn<Assignment, Double>("Weighting");
		assignmentWeightingCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, Double>("weightingModifier"));
		
		TableColumn<Assignment, String> assignmentPercentageCol = new TableColumn<Assignment, String>("Percentage");
		assignmentPercentageCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, String>("percentage"));
		
		//Functionality to toggle weighted points
		
		assignmentUnweightedPointsCol.getColumns().addAll(assignmentUnweightedReceivedPointsCol, assignmentUnweightedTotalPointsCol);
		assignmentWeightedPointsCol.getColumns().addAll(assignmentWeightedReceivedPointsCol, assignmentWeightedTotalPointsCol);
		
		table.setItems(tableData);
		
		table.getColumns().addAll(assignmentCategoryCol, assignmentNameCol, assignmentGradeLetterCol, assignmentDueDateCol, assignmentMarkingPeriodCol, assignmentWeightingCol, assignmentUnweightedPointsCol, assignmentWeightedPointsCol, assignmentPercentageCol);
		
		table.setMinHeight(super.getHeight() / 1.25);
		
		VBox newBox = new VBox(5);
		newBox.getChildren().addAll(tableTitle, table);
		
		layout.getChildren().add(newBox);
	}
}