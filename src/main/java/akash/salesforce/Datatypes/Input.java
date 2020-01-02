package akash.salesforce.Datatypes;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import akash.salesforce.SoapAPIExample;

/*
 
 	Fields Supported: Phone + Mobile + Fax
 
 
 */


public class Input 
{
	private static WebDriver driver;
	private static Map<String,String> map_datatype;
	
	public Input(WebDriver d, Map<String,String> map)
	{
		driver = d;
		map_datatype = map;
	}
	
	public  WebElement get(String label)
	{
		System.out.println("\n\n" +label + ":" +  map_datatype.get(label));
		
		String data_field = map_datatype.get(label) + "";
		
		if(!data_field.equals(""))
			return get(data_field, label);
		
		return null;
	}
	
	private  WebElement get(String field,String label)
	{	
		
		List<WebElement> divs = driver.findElements(By.cssSelector("div[class *='"+ field +"']"));
		int size = divs.size();
		for(int i=0; i<size; i++)
		{
			divs = driver.findElements(By.cssSelector("div[class *='"+ field +"']"));
			WebElement div = divs.get(i);
			String spanText = div.findElement(By.cssSelector("span")).getText();
			if(spanText.equalsIgnoreCase(label))
			{
				return div.findElement(By.cssSelector("input"));
			}
		}
		return null;
	}
	
	
	
}
