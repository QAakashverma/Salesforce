package akash.salesforce;

import com.sforce.soap.enterprise.Field;

public class Details_Of_Field 
{
	
	private Field fields;
	
	public Details_Of_Field(Field f)
	{
		fields = f;
	}
	
	public boolean get_aiPredictionField()
	{
		return fields.getAiPredictionField();
	}
	public int get_byteLength()
	{
		return fields.getByteLength();
	}
}
