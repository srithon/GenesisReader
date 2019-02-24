package Genesis;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

enum MarkingPeriod
{
	MP1, MP2, MP3, MP4;
}

public class Assignment
{
	private SimpleObjectProperty<MarkingPeriod> mP;
	private SimpleObjectProperty<Date> date;
	private SimpleStringProperty assignmentName;
	private SimpleStringProperty assignmentCategory;
	private SimpleDoubleProperty receivedPoints;
	private SimpleDoubleProperty totalPoints;
	private SimpleDoubleProperty weightingModifier;
	
	public Assignment(MarkingPeriod mP, Date date, String assignmentName, String assignmentCategory, double receivedPoints, double totalPoints, double weightingModifier)
	{
		this.mP = new SimpleObjectProperty<MarkingPeriod>();
			this.mP.setValue(mP);
		this.date = new SimpleObjectProperty<Date>();
			this.date.setValue(date);
		this.assignmentName = new SimpleStringProperty(assignmentName);
		this.assignmentCategory = new SimpleStringProperty(assignmentCategory);
		this.receivedPoints = new SimpleDoubleProperty(receivedPoints);
		this.totalPoints = new SimpleDoubleProperty(totalPoints);
		this.weightingModifier = new SimpleDoubleProperty(weightingModifier);
	}
	
	public SimpleObjectProperty<Date> dateProperty()
	{
		return date;
	}
	
	public SimpleObjectProperty<MarkingPeriod> markingPeriodProperty()
	{
		return mP;
	}
	
	public SimpleStringProperty assignmentNameProperty()
	{
		return assignmentName;
	}
	
	public SimpleStringProperty assignmentCategoryProperty()
	{
		return assignmentCategory;
	}
	
	public SimpleDoubleProperty receivedPointsProperty()
	{
		if (receivedPoints.getValue() < 0)
			return new SimpleDoubleProperty(Double.NaN);
		
		return receivedPoints;
	}
	
	public SimpleDoubleProperty totalPointsProperty()
	{
		return totalPoints;
	}
	
	public SimpleDoubleProperty weightingModifierProperty()
	{
		return weightingModifier;
	}
	
	public SimpleStringProperty percentageProperty()
	{
		if (receivedPoints.getValue() == -1)
		{
			return new SimpleStringProperty("N/A");
		}
		else if (receivedPoints.getValue() == -2)
		{
			return new SimpleStringProperty("EX");
		}
		else
		{
			return new SimpleStringProperty(getPercentageString());
		}
	}
	
	public SimpleDoubleProperty weightedReceivedPointsProperty()
	{
		if (receivedPoints.getValue() < 0)
			return new SimpleDoubleProperty(Double.NaN);
		
		return new SimpleDoubleProperty(getWeightedReceivedPoints());
	}
	
	public SimpleDoubleProperty weightedTotalPointsProperty()
	{
		return new SimpleDoubleProperty(getWeightedTotalPoints());
	}
	
	public SimpleDoubleProperty unweightedReceivedPointsProperty()
	{
		if (receivedPoints.getValue() < 0)
			return new SimpleDoubleProperty(Double.NaN);
		
		return receivedPoints;
	}
	
	public SimpleDoubleProperty unweightedTotalPointsProperty()
	{
		return new SimpleDoubleProperty(getTotalPoints());
	}
	
	//need properties for marking period and date
	
	public String getName()
	{
		return assignmentName.getValueSafe();
	}
	
	public double getReceivedPoints()
	{
		return receivedPoints.getValue();
	}
	
	public double getTotalPoints()
	{
		return totalPoints.getValue();
	}
	
	public Date getDate()
	{
		return date.getValue();
	}
	
	public double getPercentage()
	{
		if (receivedPoints.getValue() < 0) //was == -1
		{
			return Double.NaN; //was -1
		}
		
		return Math.round(((receivedPoints.getValue() / totalPoints.getValue()) * 10000)) / 10000.0;
	}
	
	public String getPercentageString()
	{
		return getPercentage() * 100 + "%";
	}
	
	public double getWeightedReceivedPoints()
	{
		return receivedPoints.getValue() * weightingModifier.getValue();
	}
	
	public double getWeightedTotalPoints()
	{
		return totalPoints.getValue() * weightingModifier.getValue();
	}
	
	public double getWeighting()
	{
		return weightingModifier.getValue();
	}
	
	public String getAssignmentCategory()
	{
		return assignmentCategory.getValue();
	}
	
	public String getMarkingPeriod()
	{
		return mP.toString();
	}
	
	public String toString()
	{
		return assignmentName + " is of the category: " + assignmentCategory
				+ "\n" + "Received Points - " + ((receivedPoints.getValue() == Double.NaN) ? ("ungraded") : ("" + receivedPoints)) + " (x" + getWeighting() + ")"
				+ "\n" + "Total Points - " + totalPoints + " (x" + getWeighting() + ")"
				+ "\n" + ((receivedPoints.getValue() >= 0) ? getPercentageString() : "");
	}
}