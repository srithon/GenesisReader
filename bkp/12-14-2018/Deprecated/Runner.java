package Deprecated;

import java.util.ArrayList;
import java.util.Scanner;

public class Runner
{
	public static void main(String[] args)
	{
		String pd = PasswordField.readPassword("Enter pass- ");
		
		if (pd.charAt(0) == '*')
		{
			pd = pd.substring(1);
		}
		
		String markingPeriod = "MP2";
		boolean time = false;
		
		for (String arg : args)
		{
			if (arg.equals("-t"))
			{
				time = true;
			}
			else if (arg.contains("MP"))
			{
				markingPeriod = arg;
			}
		}
		
		//GUI g = new GUI("10015935", pd, false, time, markingPeriod);
		//GenesisAccessor m = new GenesisAccessor("10015935", pd, false, time, markingPeriod);
	}
}
