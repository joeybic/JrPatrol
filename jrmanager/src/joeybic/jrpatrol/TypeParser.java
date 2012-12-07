package joeybic.jrpatrol;

public class TypeParser
{
	public static Object parseString(EntryType type, String data)
	{
		switch (type)
		{
			case INTEGER:
				return Integer.parseInt(data);
			
			case DOUBLE:
				return Double.parseDouble(data);
				
			case STRING:
				return data;
		}
		
		return null;
	}
}
