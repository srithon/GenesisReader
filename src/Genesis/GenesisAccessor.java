package Genesis;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;  

public class GenesisAccessor
{
	//private HashMap<String, Course>[] courses;
	//POST
	private static MarkingPeriod[] markingPeriods;
	
	private String[] courseIDs;
	
	private String usr;
	private boolean fast;
	private boolean debug;
	
	private WebDriver explorer;
	private WebDriverWait wait;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
    Date date;
    
    double startTime;
    
    private String markingPeriod;
    private int markingPeriodNum;
    
	public GenesisAccessor(String usr, String pass, boolean fast, boolean time, String markingPeriod, boolean debug)
	{
		if (time)
		{
			startTime = System.nanoTime();
		}
		
		System.setProperty("webdriver.gecko.driver", pathUp(System.getProperty("user.dir") + "geckodriver.exe"));
		
		this.usr = usr;
		//this.pass = pass;
		this.fast = fast;
		this.markingPeriod = markingPeriod;
		//markingPeriodNum = Integer.parseInt("" + markingPeriod.charAt(2));
		markingPeriodNum = 1;
		this.debug = debug;
		markingPeriods = new MarkingPeriod[4];
		
		for (int i = 0; i < markingPeriods.length; i++)
		{
			markingPeriods[i] = new MarkingPeriod();
		}
		
		/*ProfilesIni profile = new ProfilesIni();
		
		FirefoxProfile myProfile = profile.getProfile("Selenium");*/
		
		/*File profileDir = new File("C:\\Users\\sttlm\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\5rd7q59h.Selenium");
		
		FirefoxProfile myProfile = new FirefoxProfile(profileDir);
		
		FirefoxOptions options = new FirefoxOptions().setProfile(myProfile);*/
		
		//OutputStream original = System.out;
		/*PrintStream dummyStream = new PrintStream(new OutputStream() {
			public void write(int b)
			{
				// NO-OP
			}
		});
		
		System.setOut(dummyStream);*/
		
		FirefoxOptions options = new FirefoxOptions();
		
		if (!debug)
			options.addArguments("--headless");
		
		explorer = new FirefoxDriver(options);
		//explorer = new HtmlUnitDriver();
		
		explorer.get("https://students.sbschools.org");
		
		explorer.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		
		wait = new WebDriverWait(explorer, 30); //v0.45 WAS 10
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("j_username")));
		
		//sleep(1500);
		
		login(pass);
		
		explorer.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS); //v0.45 WAS 10
		
		//courses = new HashMap[4];
		
		/*for (int index = 0; index < 4; index++)
		{
			courses[index] = new HashMap<String, Course>();
		}*/
		
		sleep(1000);
		
		for (markingPeriodNum = 1; markingPeriodNum < 3; markingPeriodNum++)
		{
			setup(markingPeriodNum);
		}
		
		markingPeriodNum--;
		
		if (time)
		{
			double endTime = System.nanoTime();
			
			System.out.println("\n" + (endTime - startTime) / 1000000000 + " seconds elapsed.");
		}
	}
	
	private static String pathUp(String path)
	{
		String[] split = path.split(File.separator);
		path = "";
		
		for (int i = 0; i < split.length - 1; i++)
		{
			path += split[i] + File.separator;
		}
		
		return path;
	}
	
	private void sleep(int millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch(InterruptedException e)
		{
			
		}
	}
	
	public MarkingPeriod[] getMarkingPeriods()
	{
		return markingPeriods;
	}
	
	public static double getWeightedGPA()
	{
		double gpa = 0;
		int count = 0;
		
		for (MarkingPeriod m : markingPeriods)
		{
			if (m.hasGrades())
			{
				gpa += m.getWeightedGPA();
				
				count++;
			}
		}
		
		return gpa / count;
	}
	
	public static double getWeightedGPA(int markingPeriod)
	{
		double gpa = 0;
		
		if (markingPeriods[markingPeriod].hasGrades())
		{
			return markingPeriods[markingPeriod].getWeightedGPA();
		}
		else
		{
			return Double.NaN;
		}
	}
	
	public static double getUnweightedGPA()
	{
		double gpa = 0;
		int count = 0;
		
		for (MarkingPeriod m : markingPeriods)
		{
			if (m.hasGrades())
			{
				//System.out.println("has grades");
				
				gpa += m.getUnweightedGPA();
				
				//System.out.println(gpa);
				
				count++;
			}
		}
		
		//System.out.println(gpa);
		//System.out.println(count);
		
		return gpa / count;
	}
	
	public static double getUnweightedGPA(int markingPeriod)
	{
		double gpa = 0;
		
		if (markingPeriods[markingPeriod].hasGrades())
		{
			return markingPeriods[markingPeriod].getUnweightedGPA();
		}
		else
		{
			return Double.NaN;
		}
	}
	
	private void login(String pass)
	{
		explorer.findElement(By.id("j_username")).sendKeys(usr + "@sbstudents.org");
		
		sleep(500);
		
		explorer.findElement(By.id("j_password")).sendKeys(pass);
		
		explorer.findElement(By.id("j_password")).submit();
	}
	
	private void setup(int numMarkingPeriod)
	{
		/*
		 * Go to each course, convert it to
		 * a course object and put it in the
		 * HashMap with its course name
		 * 
		 * If
		 * 	boolean fast == true
		 * 	Do not prefill the HashMap and
		 * wait until the course is directly called
		 */
		
		//first things first
		
		//sleep(2500);
		
		explorer.get("https://students.sbschools.org/genesis/parents?"
				+ "tab1=studentdata&tab2=gradebook"
				+ "&tab3=coursesummary&action=form&studentid="
				+ this.usr);
		
		sleep(250);
		
		WebElement courseDropDownMenu = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"fldCourse\"]")));
		
		Select dropDown = new Select(courseDropDownMenu);
		
		java.util.List<WebElement> courseElements = new LinkedList<WebElement>();
		courseElements = dropDown.getOptions();
		
		courseIDs = new String[courseElements.size()];
		
		//TODO-----Optimize
		for (int i = 0; i < courseIDs.length; i++)
			courseIDs[i] = courseElements.get(i).getAttribute("value");
		
		for (WebElement i : courseElements)
		{
			markingPeriods[numMarkingPeriod].addToHashMap(i.getAttribute("value"), new Course(i.getText(), i.getAttribute("value")));
		}
		
		//System.setOut(System.out);
		
		System.out.println("\n\n");
		
		for (String courseID : courseIDs)
		{
			setupCourse(courseID, numMarkingPeriod);
			markingPeriods[numMarkingPeriod].retrieveFromHashMap(courseID).sortByDate();
		}
	}
	
	private void setupCourse(String courseID, int numMarkingPeriod)
	{
		explorer.get("https://students.sbschools.org/genesis/parents?"
				+ "tab1=studentdata&tab2=gradebook&tab3=listassignments&"
				+ "studentid=" + usr + "&action=form&date=" + getDate() + "&dateRange="
				+ markingPeriod + "&courseAndSection="
				+ courseID + "&status=");
		
		explorer.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS); //v0.45 WAS 10
		
		/*try
		{
			Thread.sleep(5000);
		}
		catch(Exception e) {}*/
		
		//ASSIGNMENTS
		WebElement gradesTable = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/table[3]/tbody/tr[2]/td/table/tbody")));
		
		List<WebElement> tableRows = gradesTable.findElements(By.tagName("tr"));

		if (debug)
		{
			for (int i = 0; i < tableRows.size(); i++)
			{
				System.out.println(i + ")");
				System.out.println(tableRows.get(i).getText());
				System.out.println();
			}
		}
		
		boolean totalPoints = true;
		
		try
		{
			WebElement categoryWeightingTableBody = explorer.findElement(By.xpath("/html/body/table[2]/tbody/tr[2]/td/div/table/tbody/tr[16]/td/div/table/tbody"));
			List<WebElement> categoryWeightingTableBodyRows = categoryWeightingTableBody.findElements(By.tagName("tr"));
			
			List<WebElement>[] categoryWeightingTableBodyRowsCellLists = new List[categoryWeightingTableBodyRows.size()];
			
			for (int row = 0; row < categoryWeightingTableBodyRowsCellLists.length; row++)
			{
				categoryWeightingTableBodyRowsCellLists[row] = categoryWeightingTableBodyRows.get(row).findElements(By.tagName("td"));
			}
			
			System.out.println("\n");
			
			for (int sdf = 0; sdf < categoryWeightingTableBodyRowsCellLists.length; sdf++)
			{
				System.out.println(sdf + ")\n");
				
				for (WebElement cell : categoryWeightingTableBodyRowsCellLists[sdf])
				{
					System.out.println(cell.getText());
				}
			}
			
			totalPoints = false;
		}
		catch (Exception e)
		{
			
		}
		
		
		
		// /html/body/table[2]/tbody/tr[2]/td/div/table/tbody/tr[7]/td/div/table/tbody
		// ^ Category Weighting Table
		
		//String[] courseAssignments = normalizeAssignmentText(tableRows);
		ArrayList<String> courseAssignments = normalizeAssignmentText(tableRows);
		
		for (String i : courseAssignments)
		{
			markingPeriods[numMarkingPeriod].addAssignmentToCourse(courseID, Course.convertToAssignment(i));
			//System.out.println(i);
		}
		
		markingPeriods[numMarkingPeriod].retrieveFromHashMap(courseID).setTotalPoints(totalPoints);
		
		///html/body/table[3]/tbody/tr[2]/td/table/tbody
		
	}
	
	public String getDate()
	{
		date = new Date();
		return dateFormat.format(date);
	}
	
	//TODO -- Optimize --- remove repeated code
	public /*String[]*/ArrayList<String> normalizeAssignmentText(List<WebElement> tableRows)
	{ //input each individual assignment -- each row is an assignment
		if (tableRows.get(1).getText().contains("No assignments were found"))
		{
			//full line -- "No assignments were found using the criteria above."
			//return new String[] {}; //return empty string array if no assignments
			return new ArrayList<String>();
		}
		
		//TODO -- hardcode common categories, check aggainst list instead of splitting
		
		String x;
		String output;
		String[] tableRowLines;
		String[] assignmentName;
		
		ArrayList<String> tempAssignmentStrings = new ArrayList<String>();
			
		/*
		 * Format -----
		 * <Marking Period>~<Date>~<Category>~<AssignmentName>~<Weighting>~<Points Gotten>~<Total Points>
		 * if <Points Gotten> is -1, not graded
		 */
		
		for (int i = 1; i < tableRows.size(); i += 7) //was ++
		{
			output = "";
			
			x = tableRows.get(i).getText();
			x = x.trim();
			
			if (x.isEmpty())
			{ //if it's all whitespace, go to the next row
				continue;
			}
			
			//System.out.println(x);
			
			//System.out.println(tableRows.get(i + 1).getText());
			//System.out.println(tableRows.size());
			
			tableRowLines = x.split("\n");
			
			output += tableRowLines[0]; //Marking Period
			output += "~";
			output += tableRowLines[2]; //Date (Month/Day)
			output += "~";
			
			if (debug)
			{
				System.out.println(output);
			}
			
			handleAssignmentName:
			{
				//starting format --- i.e "Projects Independent Reading Project"
				
				//output += assignmentName[0]; //category
				assignmentName = splitAssignmentName(tableRowLines[5].split(" "));//splitAssignmentName(tableRowLines[5].split(" "));
				
				output += assignmentName[0];
				output += "~";
				output += assignmentName[1];
				
				/*for (int index = 1; i < assignmentName.length; i++)
				{
					output += assignmentName[index];
					
					if (index + 1 < assignmentName.length)
					{
						output += " ";
					}
				}*/
			}
			
			output += "~";
			if (debug)
			{
				System.out.println(output);
			}
			
			handleWeighting:
			{
				if (tableRowLines[6].charAt(0) == 'x')
				{
					//e.x "x0.0"
					output += tableRowLines[6].substring(1);
					//this would just add "0.0"
				}
				else
				{
					output += "1.0";
				}
				
				/*for (int line = 6; line < tableRowLines.length; line++)
				{
					weighting, if it is there, will always occur right after the name
					I don't know if a for loop has any use here
				}*/
			}
			
			output += "~";
			if (debug)
			{
				System.out.println(output);
			}
			
			handleGrading:
			{
				if (x.contains("Not Graded"))
				{
					//TODO-- Add case for when it is ungraded but weighted
					output += "-1"; //points gotten
					output += "~";
					output += tableRowLines[tableRowLines.length - 1].split(":")[1].trim(); //points total
				}
				else if (x.contains("EX"))
				{
					output += "-2";
					output += "~";
					output += "0";
				}
				else
				{ //if it IS graded
					//grade will (always?) be second to last line
					String[] gradePoints = tableRowLines[tableRowLines.length - 2].split("/");
					//second to last line e.x "14 / 15" (Points attained / Points total)
					//						gradePoints array would be {"14 ", " 15"}
					output += gradePoints[0].trim(); //element would be "14 ", trimmed "14"
					output += "~";
					output += gradePoints[1].trim(); //element would be " 15", trimmed "15"
				}
			}
			
			if (debug)
			{
				System.out.println(output);
			}
			
			tempAssignmentStrings.add(output);
		}
		
		//return returnedOutput;
		return tempAssignmentStrings;
	}
	
	public String[] splitAssignmentName(String[] splitArray)
	{
		String[] commonCategories = {"participation", "quizzes", "tests", "homework", "classwork",
				"pre-test", "post-test", "pre test", "post test", "socratic seminar", "summer assignment",
				"performance assessments", "research and inquiry"};
		int splitIndex = 1;
		
		String category = "";
		String assignment = "";
		
		/*
		 * Examples ---- Post Test		Modern Life
		 * 				Pre Test		Daily Meals
		 * 				Participation	P1
		 * 				{Post, Test, Modern, Life}
		 * 				{Pre, Test, Daily, Meals}
		 * 				{Participation, P1}
		 * 				
		 */
		
		if (splitArray.length == 2)
		{
			/*
			 * If there are only two elements, 
			 * then one must be the category and
			 * the other must be the assignment name
			 */
			
			return splitArray;
		}
		
		outer:
		{
			for (int i = 0; i < commonCategories.length; i++)
			{
				for (int j = 0; j < splitArray.length; j++)
				{
					if (j < commonCategories[i].split(" ").length && splitArray[j].equalsIgnoreCase(commonCategories[i].split(" ")[j]))
					{
						continue;
					}
					else
					{
						if (j != 0)
						{
							splitIndex = j;
							break outer;
						}
						else
						{
							break;
						}
					}
				}
			}
		}
		
		for (int i = 0; i < splitIndex; i++)
		{
			category += splitArray[i];
			
			if (i + 1 < splitIndex)
			{
				category += " ";
			}
		}
		
		for (int i = splitIndex; i < splitArray.length; i++)
		{
			assignment += splitArray[i];
			
			if (i + 1 < splitArray.length && splitArray[i + 1] != "-")
			{
				assignment += " ";
			}
		}
		
		if (assignment.contains("- "))
		{
			try
			{
				assignment = assignment.substring(0, assignment.indexOf("- ") + 1) + assignment.substring(assignment.indexOf("- ") + 2);
			}
			catch (IndexOutOfBoundsException e)
			{
				assignment = assignment.substring(0, assignment.indexOf("- ")) + "-";
			}
		}
		
		String[] returnedArray = {category, assignment};
		
		return returnedArray;
	}
	
	public int getNumCourses()
	{
		return markingPeriods[markingPeriodNum].getNumCourses();
	}
	
	public Course getCourse(String courseName)
	{
		return markingPeriods[markingPeriodNum].retrieveFromHashMap(courseName);
	}
	
	public HashMap<String, Course> getCourses()
	{
		return markingPeriods[markingPeriodNum].getHashMap();
	}
	
	public String[] getCourseNames()
	{
		String[] ret = new String[markingPeriods[markingPeriodNum].getNumCourses()];
		
		for (int f = 0; f < markingPeriods[markingPeriodNum].getNumCourses(); f++)
		{
			ret[f] = markingPeriods[markingPeriodNum].retrieveFromHashMap(courseIDs[f]).getName();
		}
		
		return ret;
	}
	
	public String[] getCourseIDs()
	{
		return courseIDs;
	}
	
	public int[] getGrades()
	{
		/*
		 * Returns weekly summary
		 */
		
		return new int[] {};
	}
	
	public int[] getGrades(String course)
	{
		/*
		 * Returns grades for a course
		 */
		
		return new int[] {};
	}
	
	public void printGrades()
	{
		/*
		 * Print weekly summary
		 */
	}
	
	public void printGrades(String course)
	{
		/*
		 * Print grades for a course
		 */
	}
	
	public double getTotalPoints()
	{
		/*
		 * Returns total points for a course
		 */
		
		return -1;
	}
	
	public double getPointsEarned()
	{
		/*
		 * Returns total points earned for a course
		 */
		
		return -1;
	}
	
	public void printNewAssignments()
	{
		/*
		 * Prints any new assignments
		 */
	}
}
