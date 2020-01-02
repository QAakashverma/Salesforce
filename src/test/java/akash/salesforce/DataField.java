package akash.salesforce;

import java.util.Map;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import akash.salesforce.Datatypes.Input;
import akash.salesforce.Datatypes.Select;
import akash.salesforce.Datatypes.TextArea;

public class DataField 
{
	private WebDriver driver;
	private Map<String,String> map_datatype;
	private Map<String,Details_Of_Field> map_details;
	private String object;
	private SoapAPIExample soap;
	
	
	public Input input;
	public Select select;
	public TextArea textarea;
	
	
	public DataField(WebDriver d,String username,String pass,String object)
	{
		SoapAPIExample soap = new SoapAPIExample(username,pass,object);
		map_datatype = soap.get_map();
		map_details = soap.get_details();
		this.object = object;
		driver = d;
		input = new Input(driver, map_datatype);
		select = new Select(driver,map_datatype);
		textarea = new TextArea(driver,map_datatype);
	}
	
	public Details_Of_Field get_details(String label)
	{
		return map_details.get(label);
	}
	
	private void compute_as_object()	//Will do later
	{
		JSONObject main = new JSONObject();
		main.put(object,map_datatype);
	}
	
}
