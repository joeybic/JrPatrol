package joeybic.jrpatrol.ui;

// System
import javax.swing.*;

// Internal
import joeybic.jrpatrol.*;

/**
 * Wraps a field so we can easily extract info from it
 */
public abstract class BaseFieldWrapper<T extends JComponent>
{
	/**
	 * The field to wrap
	 */
	protected T field;
	
	/**
	 * The type of value stored in the field
	 */
	protected EntryType type;
	
	/**
	 * Get the field
	 * @return the field we are using
	 */
	public T getField()
	{
		return field;
	}
	
	/**
	 * Set the field
	 * @param field	The new field to use
	 * @throws IllegalArgumentException if field is null
	 */
	public void setField(T field) throws IllegalArgumentException
	{
		// Check arg validity
		if (field == null)
		{
			throw new IllegalArgumentException("field cannot be null");
		}
		
		this.field = field;
	}
	
	/**
	 * Get the field type
	 * @return the field type
	 */
	public EntryType getFieldType()
	{
		return type;
	}
	
	/**
	 * Set the field type
	 * @param type the new type to extract
	 */
	public void setFieldType(EntryType type)
	{
		this.type = type;
	}
	
	/**
	 * Instantiate the field wrapper
	 * @param field	The initial value for field (cannot be null)
	 * @param type	The type of value to extract
	 * @throws IllegalArgumentException if field is null
	 */
	public BaseFieldWrapper(T field, EntryType type) 
			throws IllegalArgumentException
	{		
		setField(field);
	}
	
	/**
	 * Get the value stored by the field
	 * @return the value (boxed in an object) stored in the field
	 */
	public abstract Object extractValue();
}
