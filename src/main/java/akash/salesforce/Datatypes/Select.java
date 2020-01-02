package akash.salesforce.Datatypes;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import akash.salesforce.SoapAPIExample;

public class Select 
{
	private static Map<String,String> map_datatype;
	private static WebDriver driver;
	
	public Select(WebDriver d, Map<String,String> map)
	{
		driver = d;
		map_datatype = map;
	}

	
	public  void get(String label, String value)
	{
		System.out.println("\n\n" +label + ":" +  map_datatype.get(label));
		
		String data_field = map_datatype.get(label) + "";
		
		if(!data_field.equals(""))
			get(data_field, label, value);
	}
	
	
	private  void get(String field,String label, String value)
	{	
		List<WebElement> divs = driver.findElements(By.cssSelector("div[class *='"+ field +"']"));
		for(WebElement div : divs)
		{
			String spanText = div.findElement(By.cssSelector("span")).getText().replace('*',' ').trim();
			if(spanText.equalsIgnoreCase(label))
			{
				System.out.println("Expand fields");
				div.findElement(By.cssSelector("a")).click();		hardwait(1);
				break;
			}
		}
		
		List<WebElement> options = driver.findElements(By.cssSelector("div[class*='select-options'][class*='visible'] ul li a"));
		int size = options.size();
		for(int i=0; i<size; i++)
		{
			options = driver.findElements(By.cssSelector("div[class*='select-options'][class*='visible'] ul li a"));
			WebElement option = options.get(i);
			if(option.getText().equalsIgnoreCase(value))
			{
				option.click();					hardwait(1);
				break;
			}
		}
	}
	
	private void hardwait(int sec)
	{
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
