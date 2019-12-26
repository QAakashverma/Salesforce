package akash.salesforce;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NewLeadTest
{
	WebDriver driver;
	WebDriverWait wait;
	String url = "https://qainfotech4832-dev-ed.lightning.force.com";
	
	@BeforeClass
	public void tierUp()
	{
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+File.separator+"src"+File.separator+"resources"+File.separator+"chromedriver.exe");
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver,30000);
		
	}
	
	@Test(priority=1)
	public void login()
	{
		driver.navigate().to(url);
		driver.findElement(By.cssSelector("input[id=\"username\"]")).sendKeys("");
		driver.findElement(By.cssSelector("input[id=\"password\"]")).sendKeys("");
		driver.findElement(By.cssSelector("input[id=\"Login\"]")).click();
	}

	@Test(priority=2)
	public void app_launch()
	{
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("nav button[class *= \"AppLauncher\"]"))));
		driver.findElement(By.cssSelector("nav button[class *= \"AppLauncher\"]")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains(@class,\"dragArea\")]//a[text()=\"Sales\"]"))));
		driver.findElement(By.xpath("//div[contains(@class,\"dragArea\")]//a[text()=\"Sales\"]")).click();
	}
	
	@Test(priority=3)
	public void create_new_lead()
	{
		
		//driver.navigate().to("https://qainfotech4832-dev-ed.lightning.force.com/lightning/o/Lead/new?nooverride=1&useRecordTypeCheck=1&navigationLocation=MRU_LIST&backgroundContext=%2Flightning%2Fo%2FLead%2Flist%3FfilterName%3DRecent");
		
//		click_using_javascript(driver.findElement(By.cssSelector("nav a[title=\"Leads\"]")));
		hardwait(5);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("header a[title='Leads']"))));
		hardwait(5);
		driver.findElement(By.cssSelector("button[title='Cancel']")).click();hardwait(10);
		driver.findElement(By.cssSelector("a[title=\"New\"]")).click();hardwait(10);
	}
	
	@AfterClass
	public void tierDown()
	{
//		driver.close();
//		driver.quit();
	}
	
	
	public void hardwait(int sec)
	{
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Object click_using_javascript(WebElement element)
	{
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		return executor.executeScript("arguments[0].click();", element);
	}
	
}
