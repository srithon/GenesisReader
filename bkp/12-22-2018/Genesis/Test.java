package Genesis;

import java.util.ArrayList;
import java.util.Arrays;

public class Test
{
	private static ArrayList<Assignment> assignments;
	
	public static void main(String[] args)
	{
		assignments = new ArrayList<Assignment>(Arrays.asList(
				new Assignment(null, new Date(10, 30), null, null, 0, 0, 0),
				new Assignment(null, new Date(5, 15), null, null, 0, 0, 0),
				new Assignment(null, new Date(1, 23), null, null, 0, 0, 0),
				new Assignment(null, new Date(4, 26), null, null, 0, 0, 0)));
		
		for (Assignment i : assignments)
		{
			System.out.println(i.getDate());
		}
		
		System.out.println("\n");
		
		sortByDate();
		
		for (Assignment i : assignments)
		{
			System.out.println(i.getDate());
		}
	}
	
	private static void sortByDate()
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
	
	private static double getAverage(double ... doubles)
	{
		double average = 0;
		
		for (double value : doubles)
		{
			average += value;
		}
		
		return average / doubles.length;
	}
}