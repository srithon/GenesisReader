package Genesis;

import java.util.ArrayList;

public class Course
{
	private String name;
	private String id;
	private ArrayList<Assignment> assignments;
	
	public Course(String name, String id)
	{
		this.name = name;
		this.id = id;
		assignments = new ArrayList<Assignment>();
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getID()
	{
		return id;
	}

	public void addAssignment(Assignment assignment)
	{
		assignments.add(assignment);
	}
	
	public Assignment getLastAssignment()
	{
		return assignments.get(assignments.size() - 1);
	}
	
	public ArrayList<Assignment> getAssignments()
	{
		return assignments;
	}
	
	public static Assignment convertToAssignment(String rowText)
	{
		String[] arguments = rowText.split("~");
		
		/*
		 * Format -----
		 * <Marking Period>~<Date>~<Category>~<AssignmentName>~<Weighting>~<Points Gotten>~<Total Points>
		 * if <Points Gotten> is -1, not graded
		 * 
		 * MarkingPeriod mP, Date date, String assignmentName, String assignmentCategory, int receivedPoints, int totalPoints, double weightingModifier
		 */
		
		return new Assignment(
				MarkingPeriod.valueOf(arguments[0]), //Marking Period
				new Date(Integer.parseInt(arguments[1].split("/")[0]), Integer.parseInt(arguments[1].split("/")[1])), //Date
				arguments[3], //Assignment Name
				arguments[2], //Assignment Category
				Double.parseDouble(arguments[5]), //Points gotten
				Double.parseDouble(arguments[6]), //Points total
				Double.parseDouble(arguments[4])); //Weighting
	}
	
	public String getAverageString()
	{
		return getAverage() * 100 + "%";
	}
	
	public String getAverageString(String cat)
	{
		return getAverage(cat) * 100 + "%";
	}
	
	public double getAverage()
	{
		double received = 0;
		double total = 0;
		double avr;
		
		for (Assignment x : assignments)
		{
			if (x.getReceivedPoints() >= 0)
			{
				received += x.getWeightedReceivedPoints();
				total += x.getWeightedTotalPoints();
			}
		}
		
		avr = received / total;
		
		return (Double.isNaN(avr)) ? Double.NaN : Math.round((avr * 10000)) / 10000.0;
	}
	
	public double getAverage(String category)
	{
		double received = 0;
		double total = 0;
		double avr;
		
		for (Assignment x : assignments)
		{
			if (x.getAssignmentCategory().equalsIgnoreCase(category))
			{
				if (x.getReceivedPoints() >= 0)
				{
					received += x.getWeightedReceivedPoints();
					total += x.getWeightedTotalPoints();
				}
			}
		}
		
		avr = received / total;
		
		return (Double.isNaN(avr)) ? Double.NaN : Math.round((avr * 10000)) / 10000.0;
	}
	
	public double getTotalReceivedPoints()
	{
		double received = 0;
		
		for (Assignment x : assignments)
		{
			if (x.getWeightedReceivedPoints() >= 0)
				received += x.getWeightedReceivedPoints();
		}
		
		return received;
	}
	
	public double getTotalTotalPoints()
	{
		double total = 0;
		
		for (Assignment x : assignments)
		{
			total += x.getWeightedTotalPoints();
		}
		
		return total;
	}
	
	public String toString()
	{
		return "You currently have a " + getAverage() + " in " + name;
	}
}
