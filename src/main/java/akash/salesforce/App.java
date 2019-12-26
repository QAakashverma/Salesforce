package akash.salesforce;
import com.sforce.soap.enterprise.DescribeLayout;
import com.sforce.soap.enterprise.DescribeLayoutComponent;
import com.sforce.soap.enterprise.DescribeLayoutItem;
import com.sforce.soap.enterprise.DescribeLayoutResult;
import com.sforce.soap.enterprise.DescribeLayoutRow;
import com.sforce.soap.enterprise.DescribeLayoutSection;
import com.sforce.soap.enterprise.DescribeSObjectResult;
import com.sforce.soap.enterprise.DescribeTab;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.FieldLayoutComponent;
import com.sforce.soap.enterprise.GetUserInfoResult;
import com.sforce.soap.enterprise.LayoutComponentType;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
public class App 
{
    public static void main( String[] args )
    {
        login();
    }
    
    public static void login()
    {
    	String username = "";
    	String password = "";
    	String authEndPoint = "https://login.salesforce.com/services/Soap/c/47.0";
    	EnterpriseConnection connection;
    	
    	try {
    	ConnectorConfig config = new ConnectorConfig();
		config.setUsername(username);
		config.setPassword(password);
		System.out.println("AuthEndPoint: " + authEndPoint);
		config.setAuthEndpoint(authEndPoint);
		connection = new EnterpriseConnection(config);
		
		
/*		GetUserInfoResult userInfo = connection.getUserInfo();
		System.out.println("UserID: " + userInfo.getUserId());
		System.out.println("User Full Name: " + userInfo.getUserFullName());
		System.out.println("User Email: " + userInfo.getUserEmail());
		System.out.println("SessionID: " + config.getSessionId());
		System.out.println("Auth End Point: " + config.getAuthEndpoint());
		System.out.println("Service End Point: " + config.getServiceEndpoint());
*/		
		
/*		DescribeTab[] dsrArray = connection.describeAllTabs();
		
		for(DescribeTab tab : dsrArray)
		{
			System.out.print(tab.getLabel() + ",");
		}
*/
		
		DescribeLayoutResult dlr = connection.describeLayout("Account", null, null);
		System.out.println(dlr.getLayouts().length);
		for(int i=0; i<dlr.getLayouts().length; i++)
		{
			DescribeLayout layout = dlr.getLayouts()[i];
			DescribeLayoutSection[] editLayoutSectionList = layout.getEditLayoutSections();
			
			for(int j=0; j<editLayoutSectionList.length;j++)
			{
				System.out.println(editLayoutSectionList[j].getHeading());
				DescribeLayoutRow []layoutRow= editLayoutSectionList[j].getLayoutRows();
				for(int k=0; k<editLayoutSectionList[j].getRows(); k++)
				{
					 DescribeLayoutItem []layoutItems = layoutRow[k].getLayoutItems();
					for(int l=0; l<layoutRow[k].getNumItems(); l++)
					{
						System.out.println("\t" + layoutItems[l].getLabel());
						DescribeLayoutComponent []layoutComponents = layoutItems[l].getLayoutComponents();
						for(int m=0; m<layoutComponents.length;m++)
						{	
							if(layoutComponents[m].getClass().getSimpleName().equals("FieldLayoutComponent"))
							{
								FieldLayoutComponent fieldLayout = (FieldLayoutComponent)layoutComponents[m];
								DescribeLayoutComponent []fieldLayoutComponent = fieldLayout.getComponents();
								for(int n=0; n<fieldLayoutComponent.length; n++)
								{
									System.out.println("\t\t" + fieldLayoutComponent[n].getValue() + "::" + fieldLayoutComponent[n].getType());
								}
							}
							
						}
					}
				}
			}
		}
		
		connection.logout();
    	}
    	catch(Exception e) {e.printStackTrace();}
    	
    }
}
