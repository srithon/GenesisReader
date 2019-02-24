package Genesis;

public class Test
{
	public static void main(String[] args)
	{
		System.out.println(getAverage(2, 4, 3, 1, 5, 6));
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