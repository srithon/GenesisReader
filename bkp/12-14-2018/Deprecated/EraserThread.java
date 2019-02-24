package Deprecated;

//https://stackoverflow.com/questions/22545603/how-to-display-asterisk-for-input-in-java

public class EraserThread implements Runnable
{
	private boolean stop;

	public EraserThread(String prompt)
	{
		System.out.print(prompt);
	}

	public void run ()
	{
		while (!stop)
		{
			System.out.print("\010*");
			try
			{
				Thread.currentThread().sleep(1);
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}
	}

	public void stopMasking()
	{
		this.stop = true;
	}
}