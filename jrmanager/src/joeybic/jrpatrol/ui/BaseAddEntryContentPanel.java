package joeybic.jrpatrol.ui;

// System
import javax.swing.*;
import java.util.Map;
import java.util.TreeMap;

// Internal
import joeybic.jrpatrol.*;

public abstract class BaseAddEntryContentPanel extends JPanel
{
	/**
	 * IDK 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A map, indexed by name, of field wrappers
	 */
	private Map<String, BaseFieldWrapper> fields;
	
	/**
	 * A string for what the status message should be when processing
	 */
	private String statusMessage;
	
	/**
	 * The default status message
	 */
	private static final String DEFAULT_STATUS_MESSAGE =
			"Adding entry...";
	
	/**
	 * Checks the validity of the input fields
	 * @throws BadInputException to be thrown if user inputs are not valid
	 */
	public abstract void checkInputs() throws BadInputException;
	
	/**
	 * Get the number of 
	/**
	 * Retrieves type of the a field
	 * @param  fieldName	The name of the field to lookup
	 * @return the type of the field with the given name
	 * @throws IllegalFieldException thrown if the field does not exist
	 */
	public EntryType getFieldType(String fieldName) 
			throws IllegalFieldException
	{
		if (! fields.containsKey(fieldName))
		{
			throw new IllegalFieldException("could not find referenced field " +
					fieldName);
		}
		
		return fields.get(fieldName).getFieldType();
	}
	
	/**
	 * Retrieves the value stored in the named field
	 * @param 	fieldName	The name of the field to lookup
	 * @return	the value stored in the specified field
	 * @throws	IllegalFieldException if the field does not exist
	 */
	public Object getFieldValue(String fieldName)
		throws IllegalFieldException
	{
		if (! fields.containsKey(fieldName))
		{
			throw new IllegalFieldException("could not find referenced " +
					"field " + fieldName);
		}
		
		return fields.get(fieldName).extractValue();
	}
	
}
