package Genesis;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CourseScene extends Scene
{
	private Course course;
	private ArrayList<Assignment> assignments;
	//Button[] assignmentButtons;
	
	private TableView table = new TableView();
	
	public CourseScene(VBox layout, int d1, int d2, Course course)
	{
		super(layout, d1, d2);
		
		this.course = course;
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
		
		TableColumn assignmentNameCol = new TableColumn("Name");
		assignmentNameCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, String>("name"));
		
		TableColumn assignmentDueDateCol = new TableColumn("Date");
		assignmentDueDateCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, String>("date"));
		
		TableColumn assignmentMarkingPeriodCol = new TableColumn("Marking Period");
		assignmentMarkingPeriodCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, String>("markingPeriod"));
		
		TableColumn assignmentUnweightedPointsCol = new TableColumn("Unweighted Points");
		TableColumn assignmentWeightedPointsCol = new TableColumn("Weighted Points");
		
		TableColumn assignmentUnweightedReceivedPointsCol = new TableColumn("Received Points");
		assignmentUnweightedReceivedPointsCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, Double>("unweightedReceivedPoints"));
		
		TableColumn assignmentUnweightedTotalPointsCol = new TableColumn("Total Points");
		assignmentUnweightedTotalPointsCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, Double>("unweightedTotalPoints"));
		
		TableColumn assignmentWeightedReceivedPointsCol = new TableColumn("Received Points");
		assignmentWeightedReceivedPointsCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, Double>("weightedReceivedPoints"));
		
		TableColumn assignmentWeightedTotalPointsCol = new TableColumn("Total Points");
		assignmentWeightedTotalPointsCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, Double>("weightedTotalPoints"));
		
		TableColumn assignmentWeightingCol = new TableColumn("Weighting");
		assignmentWeightingCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, Double>("weightingModifier"));
		
		TableColumn assignmentPercentageCol = new TableColumn("Percentage");
		assignmentPercentageCol.setCellValueFactory(
				new PropertyValueFactory<Assignment, String>("percentage"));
		
		//Functionality to toggle weighted points
		
		assignmentUnweightedPointsCol.getColumns().addAll(assignmentUnweightedReceivedPointsCol, assignmentUnweightedTotalPointsCol);
		assignmentWeightedPointsCol.getColumns().addAll(assignmentWeightedReceivedPointsCol, assignmentWeightedTotalPointsCol);
		
		table.setItems(tableData);
		table.getColumns().addAll(assignmentNameCol, assignmentDueDateCol, assignmentMarkingPeriodCol, assignmentWeightingCol, assignmentUnweightedPointsCol, assignmentWeightedPointsCol, assignmentPercentageCol);
		
		VBox newBox = new VBox(5);
		newBox.getChildren().addAll(tableTitle, table);
		
		layout.getChildren().add(newBox);
	}
}