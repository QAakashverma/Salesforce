package akash.salesforce;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sforce.ws.ConnectionException;
import com.sforce.ws.shade.org.apache.commons.collections.map.HashedMap;

import akash.salesforce.Datatypes.Fields;

public class NewLeadTest
{
	static WebDriver driver;
	WebDriverWait wait;
	String url = "https://qainfotech4832-dev-ed.lightning.force.com";
	DataField datafield;
	//@BeforeClass
	public void tierUp()
	{
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+File.separator+"src"+File.separator+"resources"+File.separator+"chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver,30000);
		datafield = new DataField(driver, "Lead");
	}
	//@Test(priority=1)
	public void login()
	{
		driver.navigate().to(url);
		driver.findElement(By.cssSelector("input[id=\"username\"]")).sendKeys("akash.verma@qainfotech.com");
		driver.findElement(By.cssSelector("input[id=\"password\"]")).sendKeys("akashvermaqa68");
		driver.findElement(By.cssSelector("input[id=\"Login\"]")).click();
	}	
	//@Test(priority=2)
	public void app_launch()
	{
		click((By.cssSelector("nav button[class *= \"AppLauncher\"]")));
		click(By.xpath("//div[contains(@class,\"dragArea\")]//a[text()=\"Sales\"]"));
		hardwait(5);
	}
	//@Test(priority=3)
	public void create_new_lead()
	{	
		click_js("a[title='Leads']");
		click(By.cssSelector("a[title=\"New\"]"));
	}

	
	
	
	@Test(priority=4)
	public void fill_form1() throws ConnectionException
	{
		
		System.out.println(datafield.get_details("Lead Owner").get_byteLength());
		System.out.println(datafield.get_details("Phone").get_byteLength());
		
//		send_keys(datafield.input.get("Phone"),"7065586031");
//		send_keys(datafield.input.get("Fax"),"Fax");
//		send_keys(datafield.input.get("Mobile"),"051212345678");	
//		send_keys(datafield.input.get("Email"),"v.akash75@yahoo.in");
//		send_keys(datafield.input.get("Website"),"v.akash75@yahoo.in");
//		send_keys(datafield.input.get("No. of Employees"),"25");		
//		send_keys(datafield.input.get("Number of Locations"),"100");
//		send_keys(datafield.input.get("First Name"),"Akash");
//		send_keys(datafield.input.get("Last Name"),"Verma");
//		send_keys(datafield.input.get("Company"),"AV Labs");
//		send_keys(datafield.input.get("Title"),"Software Engg");
//		send_keys(datafield.input.get("Current Generator(s)"),"Nahi pata");
//		send_keys(datafield.input.get("SIC Code"),"Nahi pata");
//		send_keys(datafield.input.get("City"),"Kanpur");
//		send_keys(datafield.input.get("State/Province"),"UP");
//		send_keys(datafield.input.get("Zip/Postal Code"),"208004");
//		send_keys(datafield.input.get("Country"),"India");
//		send_keys(datafield.input.get("Annual Revenue"),"300000");
//		
//		datafield.select.get("Salutation", "Mr.");
//		datafield.select.get("Lead Source", "Web");
//		datafield.select.get("Industry", "Banking");
//		datafield.select.get("Lead Status", "Working - Contacted");
//		datafield.select.get("Rating", "Hot");
//		datafield.select.get("Product Interest", "GC1000 series");
//		datafield.select.get("Primary", "No");
//		
//		send_keys(datafield.textarea.get("Street"),"My street");
//		send_keys(datafield.textarea.get("Description"),"My description");
	}

	
	@AfterClass
	public void tierDown()
	{
//		driver.close();
//		driver.quit();
	}


	public void click(By locator)
	{
		WebElement element = driver.findElement(locator);
		wait.until(ExpectedConditions.visibilityOf(element));
		element.click();
	}
	
	public void click_js(String locator)
	{
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		String script = "document.querySelector(\"html\").querySelector(\""+locator+"\").click()";
		executor.executeScript(script);
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

	public void writeToFile(String s, String filename)
	{
		File file = new File(filename);
		FileWriter filewriter;
		try {
			filewriter = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(filewriter);
			writer.write(s);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void send_keys(WebElement e, String k)
	{
		if(e != null)
			e.sendKeys(k);
	}
	
	
}	//Public class









