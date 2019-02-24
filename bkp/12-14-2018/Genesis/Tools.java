package Genesis;

public class Tools
{
	public static void main(String[] args)
	{
		System.out.println(getPerc(0.945434));
	}
	
	private static double getPerc(double percentage)
	{
		return Math.round((percentage * 10000)) / 100.0;
	}
	
	/*private static double getAverage(double received, double total)
	{
		double avr;
		
		avr = received / total;
		
		avr *= 1000;
		
		if (avr % 100 >= 50)
		{
			avr++;
		}
		
		avr = (int) avr;
		avr /= 1000;
		
		/*
		 * 99.35 * 10
		 * 993.5
		 * (int) 993.5
		 * 993
		 * 99.3
		 * 
		 * if (99.35 % 0.1 >= 5)
		 * 		99.35 = 993.5 + 1
		 * 		994.5 -> 994
		 * 		99.4
		 * 
		 * else
		 * { //99.32
		 * 
		 * }
		 * /
		
		return (Double.isNaN(avr)) ? -1 : avr;
	}*/
}
