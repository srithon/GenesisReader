package Deprecated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PasswordField {

	public static String readPassword (String prompt)
	{
		EraserThread et = new EraserThread(prompt);
		Thread mask = new Thread(et);
		mask.start();

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String password = "";

		try
		{
			password = in.readLine();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}

		et.stopMasking();
		return password;
	}
}   