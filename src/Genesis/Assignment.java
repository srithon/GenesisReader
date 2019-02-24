package Genesis;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

enum MarkingPeriodEnum
{
	MP1, MP2, MP3, MP4;
}

public class Assignment implements Serializable
{
	private SimpleObjectProperty<MarkingPeriodEnum> mP;
	private SimpleObjectProperty<Date> date;
	private SimpleStringProperty assignmentName;
	private SimpleStringProperty assignmentCategory;
	private SimpleDoubleProperty receivedPoints;
	private SimpleDoubleProperty totalPoints;
	private SimpleDoubleProperty weightingModifier;
	
	public Assignment(MarkingPeriodEnum mP, Date date, String assignmentName, String assignmentCategory, double receivedPoints, double totalPoints, double weightingModifier)
	{
		this.mP = new SimpleObjectProperty<MarkingPeriodEnum>();
			this.mP.setValue(mP);
		this.date = new SimpleObjectProperty<Date>();
			this.date.setValue(date);
		this.assignmentName = new SimpleStringProperty(assignmentName);
		this.assignmentCategory = new SimpleStringProperty(assignmentCategory);
		this.receivedPoints = new SimpleDoubleProperty(receivedPoints);
		this.totalPoints = new SimpleDoubleProperty(totalPoints);
		this.weightingModifier = new SimpleDoubleProperty(weightingModifier);
	}
	
	/*public Assignment(SimpleObjectProperty<MarkingPeriod> mP, SimpleObjectProperty<Date> date, SimpleStringProperty assignmentName,
			SimpleStringProperty assignmentCategory, SimpleDoubleProperty receivedPoints, SimpleDoubleProperty totalPoints, SimpleDoubleProperty weightingModifier)
	{
		this.mP = mP;
		this.date = date;
		this.assignmentName = assignmentName;
		this.assignmentCategory = assignmentCategory;
	}*/
	
	public SimpleObjectProperty<Date> dateProperty()
	{
		return date;
	}
	
	public SimpleObjectProperty<MarkingPeriodEnum> markingPeriodProperty()
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
	//new
	public SimpleStringProperty assignmentGradeLetterProperty()
	{
		return new SimpleStringProperty(getLetterGrade());
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
		return assignmentName.getValue() + " is of the category: " + assignmentCategory.getValue()
				+ "\n" + "Received Points - " + ((receivedPoints.getValue() == -1.0) ? ("Ungraded") : ("" + receivedPoints.getValue())) + " (x" + getWeighting() + ")"
				+ "\n" + "Total Points - " + totalPoints.getValue() + " (x" + getWeighting() + ")"
				+ "\n" + ((receivedPoints.getValue() >= 0) ? getPercentageString() : "");
	}
	
	public String getLetterGrade()
	{
		double perc = getPercentage() * 100;
		
		if (perc >= 93)
		{
			return "A";
		}
		else if (perc >= 90)
		{
			return "A-";
		}
		else if (perc >= 87)
		{
			return "B+";
		}
		else if (perc >= 83)
		{
			return "B";
		}
		else if (perc >= 80)
		{
			return "B-";
		}
		else if (perc >= 77)
		{
			return "C+";
		}
		else if (perc >= 73)
		{
			return "C";
		}
		else if (perc >= 70)
		{
			return "C-";
		}
		else if (perc >= 67)
		{
			return "D+";
		}
		else if (perc >= 63)
		{
			return "D";
		}
		else if (perc >= 60)
		{
			return "D-";
		}
		
		return "F";
	}
	
	/**
	    * Serialize this instance.
	    * 
	    * @param out Target to which this instance is written.
	    * @throws IOException Thrown if exception occurs during serialization.
	    */
	/*private void writeObject(final ObjectOutputStream out) throws IOException
	{
		out.writeUTF(mP.getValue().toString());
	    out.writeUTF(date.getValue().toString());
	    out.writeUTF(assignmentName.getValueSafe());
	    out.writeUTF(assignmentCategory.getValueSafe());
	    out.writeUTF(receivedPoints.getValue().toString());
	    out.writeUTF(totalPoints.getValue().toString());
	    out.writeUTF(weightingModifier.getValue().toString());
	}
	 
	/**
	 * Deserialize this instance from input stream.
	 * 
	 * @param in Input Stream from which this instance is to be deserialized.
	 * @throws IOException Thrown if error occurs in deserialization.
	 * @throws ClassNotFoundException Thrown if expected class is not found.
	 
	private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		Assignment x = new Assignment(MarkingPeriod.valueOf(in.readUTF())
	}

	private void readObjectNoData() throws ObjectStreamException
	{
		throw new InvalidObjectException("Stream data required");
	}*/
}