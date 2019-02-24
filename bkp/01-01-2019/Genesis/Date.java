package Genesis;

public class Date implements Comparable<Object>
{
	private int month;
	private int day;
	
	public Date(int month, int day)
	{
		this.month = month;
		this.day = day;
	}
	
	public Date(Date date)
	{
		
	}
	
	public String getMonthName()
	{
		return monthToString(month);
	}
	
	public int getMonthNum()
	{
		return month;
	}
	
	public int getDayNum()
	{
		return day;
	}
	
	public String toString()
	{
		return monthToString(month) + " " + day;
	}
	
	public String monthToString(int month)
	{
		switch (month)
		{
			case 1:	return "January";
			case 2:	return "February";
			case 3: return "March";
			case 4:	return "April";
			case 5:	return "May";
			case 6:	return "June";
			case 7:	return "July";
			case 8:	return "August";
			case 9:	return "September";
			case 10: return "October";
			case 11: return "November";
			case 12: return "December";
		}
		
		return "error";
	}
	
	public int monthStringToInt(String month)
	{
		month = month.toLowerCase();
		
		switch (month)
		{
			case "january":		return 1;
			case "february":	return 2;
			case "march": 		return 3;
			case "april":		return 4;
			case "may":			return 5;
			case "june":		return 6;
			case "july":		return 7;
			case "august":		return 8;
			case "september":	return 9;
			case "october": 	return 10;
			case "november": 	return 11;
			case "december": 	return 12;
		}
		
		return 0;
	}

	@Override
	public int compareTo(Object otherDate)
	{
		Date other;
		
		try
		{
			other = new Date(((Date) otherDate).getMonthNum(), ((Date) otherDate).getDayNum());
		}
		catch (Exception e)
		{
			return -2;
		}
		
		if (other.getMonthNum() < 7)
		{
			if (month < 7)
			{
				if (month > other.getMonthNum())
				{
					return 1;
				}
				else if (month < other.getMonthNum())
				{
					return -1;
				}
			}
			else
			{
				return -1;
			}
		}
		else
		{
			if (month < 7)
			{
				return 1;
			}
			else
			{
				if (month > other.getMonthNum())
				{
					return 1;
				}
				else if (month < other.getMonthNum())
				{
					return -1;
				}
			}
		}
		
		if (other.getDayNum() > getDayNum())
		{
			return -1;
		}
		else if (other.getDayNum() < getDayNum())
		{
			return 1;
		}
		else
		{
			return 0;
		}
		
		/*
		 * JAN - NEWYEAR 1
		 * FEB - NEWYEAR 2
		 * MAR - NEWYEAR 3
		 * APR - NEWYEAR 4
		 * MAY - NEWYEAR 5
		 * JUN - NEWYEAR 6
		 * JUL - SUMMER 7
		 * AUG - SUMMER 8
		 * SEP - OLD 9
		 * OCT - OLD 10
		 * NOV - OLD 11
		 * DEC - OLD 12
		 */
	}
}
