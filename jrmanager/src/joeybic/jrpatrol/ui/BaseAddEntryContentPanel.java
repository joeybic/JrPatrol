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
	private Map<String, BaseFieldWrapper<? extends JComponent>> fields;
	
	/**
	 * A string for what the status message should be when processing
	 */
	private String statusMessage = DEFAULT_STATUS_MESSAGE;
	
	/**
	 * The default status message
	 */
	private static final String DEFAULT_STATUS_MESSAGE =
			"Adding entry...";
	
	/**
	 * Setup a content panel with the default status message
	 */
	public BaseAddEntryContentPanel()
	{
		this(DEFAULT_STATUS_MESSAGE);
	}
	
	/**
	 * Sets up a content panel with the specified default message
	 * @param statusMessage the status message to display at go time
	 */
	public BaseAddEntryContentPanel(String statusMessage)
	{
		// Super constructorifyyy!!!
		super();
		
		// Set the status message
		this.statusMessage = statusMessage;
		
		// Setup the fields map
		fields = new TreeMap<String, BaseFieldWrapper<? extends JComponent>>();
	}
	/**
	 * Checks the validity of the input fields
	 * @throws BadInputException to be thrown if user inputs are not valid
	 */
	public abstract void checkInputs() throws BadInputException;
	
	/**
	 * Get the status message to set at the start of processing
	 * @return the status message string
	 */
	public String getStatusMessage()
	{
		return statusMessage;
	}
	
	/**
	 * Set the status message
	 * @param statusMessage the new value for the status message
	 */
	public void setStatusMessage(String statusMessage)
	{
		this.statusMessage = statusMessage;
	}
	
	/**
	 * Get the number of fields registered to the panel
	 * @return The number of REGISTERED fields in the panel
	 */
	public int getFieldCount()
	{
		return fields.size();
	}
	
	/**
	 * Gets whether or not the specified field is registered
	 * @param 	fieldName	THe name of the field to lookup
	 * @return 	true if the field is registered. otherwise false.
	 */
	public boolean getHasField(String fieldName)
	{
		return fields.containsKey(fieldName);
	}
	
	/**
	 * Retrieves type of the a field
	 * @param  fieldName	The name of the field to lookup
	 * @return the type of the field with the given name
	 * @throws IllegalFieldException thrown if the field does not exist
	 */
	public EntryType getFieldType(String fieldName) 
			throws IllegalFieldException
	{
		if (! getHasField(fieldName))
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
		if (! getHasField(fieldName))
		{
			throw new IllegalFieldException("could not find referenced " +
					"field " + fieldName);
		}
		
		return fields.get(fieldName).extractValue();
	}
	
	/**
	 * Add info for a field to the field manager
	 * @param fieldName		The name to use as the lookup index
	 * @param fieldWrapper 	a wrapper of the info for the field
	 * @throws IllegalFieldException if an entry already exists for 
	 */
	public <G extends JComponent, T extends BaseFieldWrapper<G>> void registerField(String fieldName,
														   T fieldWrapper)
		throws IllegalFieldException
	{
		if (getHasField(fieldName))
		{
			throw new IllegalFieldException("stubbornly refusing to overwrite" +
					" existing field \"" + fieldName + "\"");
		}
		
		fields.put(fieldName, fieldWrapper);
	}
	
	/**
	 * Remove a registry
	 * @param fieldName
	 * @throws IllegalFieldException
	 */
	public void removeField(String fieldName)
	{
		if (! getHasField(fieldName))
		{
			throw new IllegalFieldException("field not found");
		}
		
		fields.remove(fieldName);
	}
		
}
