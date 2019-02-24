package Genesis;

import java.util.ArrayList;
import java.util.Arrays;

public class Test
{
	private static ArrayList<Assignment> assignments;
	
	public static void main(String[] args)
	{
		/*assignments = new ArrayList<Assignment>(Arrays.asList(
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
		}*/
		
		String courseAssignment = "MP2~12/19~Participation~Fit -3~1.0~7~7";
		String courseAssignmentB = "MP2~11/19~Oral~Assessments la lettre de Victor/ Stephanie~1.0~-1~25";
		
		double startTime = System.nanoTime();
		benchmarkConvertToAssignment(courseAssignment, courseAssignmentB);
		System.out.println("total - " + ((System.nanoTime() - startTime) / 1000000000));
	}
	
	private static void benchmarkNormalizeAssignmentText()
	{
		//idk how
	}
	
	private static void benchmarkConvertToAssignment(String a, String b)
	{
		double avr = 0;
		
		for (int x = 0; x < 10; x++)
		{
			double start = System.nanoTime();
			
			for (int i = 0; i < 100; i++)
			{
				Assignment assignment1 = Course.convertToAssignment(a);
				Assignment assignment2 = Course.convertToAssignment(b);
			}
			
			avr += (System.nanoTime() - start);
		}
		
		avr /= 10;
		
		System.out.println("Average duration for 200 method calls on one thread - " + ((avr) / 1000000000.0));
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