package akash.salesforce.Datatypes;

import java.util.Map;

import com.sforce.ws.shade.org.apache.commons.collections.map.HashedMap;

public class Fields 
{
	public static final String PHONE = "uiInputPhone";	//phone
	public static final String EMAIL = "uiInputEmail";	//email
	public static final String URL = "uiInputURL";		//url
	public static final String NUMBER = "uiInputNumber"; //_int , _double
	public static final String TEXT = "uiInputText";	//string
	public static final String CURRENCY = "uiInput";	//currency
	public static final String SELECT = "uiInputSelect";	//picklist
	public static final String TEXTAREA = "uiInputTextArea"; //textarea
	
	public static Map<String,String> fields()
	{
		Map<String,String> map = new HashedMap();
		
		map.put("phone",PHONE);
		map.put("email",EMAIL);
		map.put("url",URL);
		map.put("_int",NUMBER);
		map.put("string",TEXT);
		map.put("currency",CURRENCY);
		map.put("picklist",SELECT);
		map.put("textarea",TEXTAREA);
		map.put("_double", NUMBER);
		
		return map;
	}
	
	
}
