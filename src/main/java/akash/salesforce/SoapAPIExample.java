package akash.salesforce;
import java.util.Iterator;
import java.util.Map;

import com.sforce.soap.enterprise.DescribeLayout;
import com.sforce.soap.enterprise.DescribeLayoutComponent;
import com.sforce.soap.enterprise.DescribeLayoutItem;
import com.sforce.soap.enterprise.DescribeLayoutResult;
import com.sforce.soap.enterprise.DescribeLayoutRow;
import com.sforce.soap.enterprise.DescribeLayoutSection;
import com.sforce.soap.enterprise.DescribeSObjectResult;
import com.sforce.soap.enterprise.DescribeTab;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.Field;
import com.sforce.soap.enterprise.FieldLayoutComponent;
import com.sforce.soap.enterprise.GetUserInfoResult;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.shade.org.apache.commons.collections.map.HashedMap;
import akash.salesforce.Datatypes.Fields;

public class SoapAPIExample 
{
	
	private static Map<String, DLField> map_labels = new HashedMap();
	private static Map<String, String> labels = new HashedMap();
	private static Map<String, Details_Of_Field> details_map = new HashedMap();
	private static ConnectorConfig config;
	private static EnterpriseConnection connection;
	
	
	
	public SoapAPIExample(String username,String pass,String object)
	{
        login(username,pass);
        
        try {
        	describe_layout(object);
            describe_sobj(object);
            logout();
		} catch (ConnectionException e) {
			e.printStackTrace();
		}        
	}
	
	public  Map<String, String> get_map()
	{
        return labels;
    }
	
	public Map<String,Details_Of_Field> get_details()
	{
		return details_map;
	}
	
	
    private  void describe_sobj(String object) throws ConnectionException
    {
    	Map<String, String> map = Fields.fields();
    	DescribeSObjectResult describeSObjectResult = connection.describeSObject(object);
    	Field[] fields = describeSObjectResult.getFields();
    	for(int i=0; i<fields.length; i++)
    	{
//    		System.out.println(fields[i].getControllerName() + " : " + fields[i].getCompoundFieldName() + " : " + fields[i].getName() + " : " + fields[i].getLabel() + " : " + fields[i].getType().name());

    		DLField dlfield = map_labels.get(fields[i].getName());
    		if(dlfield != null)
    		{
    			String check_nill = dlfield.dataType + "";
        		if( check_nill.equals("nill") )
        		{
        			if(dlfield.label != null) {
        				labels.put(dlfield.label, map.get(fields[i].getType().name()));
        				details_map.put(dlfield.label, new Details_Of_Field(fields[i]));
        				}
        			else {
        				labels.put(fields[i].getLabel(), map.get(fields[i].getType().name()));
        				details_map.put(fields[i].getLabel(),new Details_Of_Field(fields[i]));
        			}
        		}
    		}
    	}
    }
    
    private  void login(String user_name, String pass)
    {
    	String username = user_name;
    	String password = pass;
    	String authEndPoint = "https://login.salesforce.com/services/Soap/c/47.0";
    	
    	
    	try {
    	config = new ConnectorConfig();
		config.setUsername(username);
		config.setPassword(password);
		System.out.println("AuthEndPoint: " + authEndPoint);
		config.setAuthEndpoint(authEndPoint);
		connection = new EnterpriseConnection(config);
		
    	}
    	catch(Exception e) {e.printStackTrace();}
    }
    private  void logout() throws ConnectionException
    {
    	connection.logout();
    }
    private  void config_info() throws ConnectionException
    {
    	GetUserInfoResult userInfo = connection.getUserInfo();
		System.out.println("UserID: " + userInfo.getUserId());
		System.out.println("User Full Name: " + userInfo.getUserFullName());
		System.out.println("User Email: " + userInfo.getUserEmail());
		System.out.println("SessionID: " + config.getSessionId());
		System.out.println("Auth End Point: " + config.getAuthEndpoint());
		System.out.println("Service End Point: " + config.getServiceEndpoint());
    }
    private  void describe_all_tabs() throws ConnectionException
    {
    	DescribeTab[] dsrArray = connection.describeAllTabs();
		
		for(DescribeTab tab : dsrArray)
		{
			System.out.print(tab.getLabel() + ",");
		}
    }
    
    private  void describe_layout(String object) throws ConnectionException
    {
    	DescribeLayoutResult dlr = connection.describeLayout(object, null, null);
//		System.out.println(dlr.getLayouts().length);
		for(int i=0; i<dlr.getLayouts().length; i++)
		{
			DescribeLayout layout = dlr.getLayouts()[i];
			DescribeLayoutSection[] editLayoutSectionList = layout.getEditLayoutSections();
			
			for(int j=0; j<editLayoutSectionList.length;j++)
			{
//				System.out.println(editLayoutSectionList[j].getHeading());
				DescribeLayoutRow []layoutRow= editLayoutSectionList[j].getLayoutRows();
				for(int k=0; k<editLayoutSectionList[j].getRows(); k++)
				{
					 DescribeLayoutItem []layoutItems = layoutRow[k].getLayoutItems();
					for(int l=0; l<layoutRow[k].getNumItems(); l++)
					{
//						System.out.println("\t" + layoutItems[l].getLabel());
						DLField sf = new DLField();
						sf.label = layoutItems[l].getLabel();	sf.dataType = "nill";
						
						DescribeLayoutComponent []layoutComponents = layoutItems[l].getLayoutComponents();
						for(int m=0; m<layoutComponents.length;m++)
						{
							if(layoutComponents[m].getClass().getSimpleName().equals("FieldLayoutComponent"))
							{
								FieldLayoutComponent fieldLayout = (FieldLayoutComponent)layoutComponents[m];
								DescribeLayoutComponent []fieldLayoutComponent = fieldLayout.getComponents();
								
								DLField sf_i = new DLField();
								sf_i.dataType = "nill";
								
								for(int n=0; n<fieldLayoutComponent.length; n++)
								{
//									System.out.println("\t\t" + fieldLayoutComponent[n].getValue());
									sf_i.object_name = fieldLayoutComponent[n].getValue();
									sf_i.label = null;
									map_labels.put(fieldLayoutComponent[n].getValue(), sf_i);
								}
							}
							else {
								sf.object_name = layoutComponents[m].getValue();
								map_labels.put(layoutComponents[m].getValue(), sf);
							}
						}
					}
				}
			}
		}
    }
    
    private  void print_map(Map map)
    {
    	Iterator<Map.Entry<String, String>> itr = map.entrySet().iterator();
    	
    	System.out.print("\n\n");
    	
    	while(itr.hasNext())
    	{
    		Map.Entry<String, String> entry = itr.next();
    		System.out.println(entry.getKey() + " : " + entry.getValue());
    	}
    	
    	System.out.print("\n\n");
    	
    } 
    
    private class DLField
    {
    	public String label;
    	public String object_name;
    	public String dataType;
    }
}

