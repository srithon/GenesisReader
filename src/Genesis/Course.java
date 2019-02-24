package Genesis;

import java.io.Serializable;
import java.util.ArrayList;

enum CLASS_TYPE
{
	AP, HONORS, REGULAR;
}

public class Course
{
	private String name;
	private String id;
	private boolean totalPoints;
	private ArrayList<Assignment> assignments;
	private ArrayList<String> categories;
	private ArrayList<Double> categoryWeightings;
	private CLASS_TYPE classtype;
	
	public Course(String name, String id)
	{
		this.name = name;
		this.id = id;
		assignments = new ArrayList<Assignment>();
	}
	
	public double getWeightedGPAContribution()
	{
		switch (classtype)
		{
		case HONORS:
			return getRawGPAContribution() + 0.5;
		case AP:
			return getRawGPAContribution() + 1.0;
		default:
			return getRawGPAContribution();
		}
	}
	
	public void setClassType(CLASS_TYPE classtype)
	{
		this.classtype = classtype;
	}
	
	public boolean hasGrades()
	{
		if (getAverage() != Double.NaN)
			return true;
		
		return false;
	}
	
	public double getRawGPAContribution()
	{
		//System.out.println("letter grade - " + getLetterGrade());
		
		switch (getLetterGrade())
		{
		case "A":
			return 4.0;
		case "A-":
			return 3.7;
		case "B+":
			return 3.3;
		case "B":
			return 3.0;
		case "B-":
			return 2.7;
		case "C+":
			return 2.3;
		case "C":
			return 2.0;
		case "C-":
			return 1.7;
		case "D+":
			return 1.3;
		case "D":
			return 1.0;
		case "NaN":
			return Double.NaN;
		default: //F
			return 0.0;
		}
	}
	
	public String getLetterGrade()
	{
		double perc = getAverage() * 100;
		
		//System.out.println("perc - " + perc);
		
		if (perc == Double.NaN)
		{
			return "NaN";
		}
		else if (perc >= 93)
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
	
	public void setTotalPoints(boolean totalPoints)
	{
		this.totalPoints = totalPoints;
	}
	
	public void sortByDate()
	{
		for (int i = 1; i < assignments.size(); i++)
		{
			if (assignments.get(i).getDate().compareTo(assignments.get(i - 1).getDate()) == -1)
			{
				assignments.add(i - 1, assignments.remove(i));
				i = 1;
			}
		}
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
		return assignments.size() == 0 ? null : assignments.get(assignments.size() - 1);
	}
	
	public Assignment getLastGradedAssignment()
	{
		for (Assignment x : assignments)
		{
			if (x.getReceivedPoints() != -1.0)
			{
				return x;
			}
		}
		
		return null;
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
				MarkingPeriodEnum.valueOf(arguments[0]), //Marking Period
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
	
	public double getCategoryMultiplier(String cat)
	{
		for (int ind = 0; ind < categories.size(); ind++)
		{
			if (categories.get(ind).equalsIgnoreCase(cat))
			{
				return categoryWeightings.get(ind);
			}
		}
		
		return Double.NEGATIVE_INFINITY;
	}
	
	public double getAverage()
	{
		double received = 0;
		double total = 0;
		double avr;
		
		if (totalPoints)
			for (Assignment x : assignments)
			{
				if (x.getReceivedPoints() >= 0)
				{
					received += (x.getWeightedReceivedPoints());
					total += (x.getWeightedTotalPoints());
				}
			}
		else
			for (Assignment x : assignments)
			{
				if (x.getReceivedPoints() >= 0)
				{
					double catMult = getCategoryMultiplier(x.getAssignmentCategory());
					received += (x.getWeightedReceivedPoints()) * catMult;
					total += (x.getWeightedTotalPoints()) * catMult;
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
