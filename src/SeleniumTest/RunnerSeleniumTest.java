package SeleniumTest;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class RunnerSeleniumTest
{
	static WebDriver webDriver;
	    /**
	     * @param args
	     * @throws InterruptedException
	     */
	    public static void main(final String[] args) throws InterruptedException {
	    	System.setProperty("webdriver.gecko.driver", "D:\\Data\\Dev\\ThirdParty\\Selenium\\Firefox\\geckodriver.exe");
	    	
	    	WebDriver driver;
	    	driver = new FirefoxDriver();
	    	
	    	driver.get("https://www.wikipedia.org");
	    	
	    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    	
	    	WebElement link;
	    	
	    	link = driver.findElement(By.id("js-link-box-en"));
	    	//link = driver.findElement(By.linkText("English"));
	    	
	    	link.click();
	    	
	    	Thread.sleep(2500);
	    	
	    	WebElement searchBox;
	    	
	    	searchBox = driver.findElement(By.id("searchInput"));
	    	searchBox.sendKeys("Software");
	    	
	    	searchBox.submit();
	    	
	    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    	
	    	Thread.sleep(4000);
	    	
	    	driver.quit();
	    	
	    	/*String username = "Firelord652";
	    	String password = "x";//m o m o
	    	
	        // Telling the system where to find the chrome driver
	        System.setProperty("webdriver.ie.driver", "D:\\Data\\Dev\\Eclipse2018-2019\\Projects\\src\\BruteForce\\IEDriverServer.exe");

	        // Open the Chrome browser
	        webDriver = new InternetExplorerDriver();

	        webDriver.get("https://twitter.com/login");
	        
	        // Waiting a bit before closing
	        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	        
	        //System.out.println(webDriver.getTitle());
	        
	        WebElement about;
	        about = webDriver.findElement(By.linkText("About"));
	        
	        //webDriver.findElement(By.name("session[username_or_email]")).sendKeys(username);
	        //webDriver.findElement(By.name("session[password]")).sendKeys(password);

	        about.click();
	        
	        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	        
	        // Closing the browser and WebDriver
	        webDriver.close();
	        webDriver.quit();*/
	    }
}
