package joeybic.jrpatrol.juniors;

import javax.swing.*;
import java.awt.*;
import joeybic.jrpatrol.*;
import joeybic.jrpatrol.ui.*;

public class AddJuniorPanel extends BaseAddEntryPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Our prepared statement
	 */
	private static final String ADD_JUNIOR =
			"INSERT INTO Juniors(name) VALUES(?)";
	
	private static final String FIELD_NAMES[] =
	{
		"name"
	};
	
	private static final String BUTTON_LABEL = "Add Junior";
	
	public AddJuniorPanel()
	{
		super(ADD_JUNIOR,
			  FIELD_NAMES,
			  new AddJuniorContentPanel(), 
			  BUTTON_LABEL);
	}
}
			  
class AddJuniorContentPanel extends BaseAddEntryContentPanel
{
	/**
	 * The width of the name field
	 */
	private static final int FIELD_WIDTH = 30;

	/**
	 * IDK what this is for
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The desired width of the popup
	 */
	public static final int WIDTH = MainFrame.WIDTH / 2;
	
	/**
	 * The name field
	 */
	private JTextField juniorNameField = null;

	@Override
	public void checkInputs() throws BadInputException
	{
		String name = juniorNameField.getText();
		if (name.length() == 0)
		{
			throw new BadInputException("Please enter a name");
		}
	}
	
	/**
	 * Constructor
	 */
	public AddJuniorContentPanel()
	{
		super("Adding new junior");
		
		// Set the layout to gridbag
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Create a label
		JLabel jrNameLabel = new JLabel("Junior Name: ");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(jrNameLabel, c);
		
		// Create the text field
		juniorNameField = new JTextField(FIELD_WIDTH);
		c.gridx = 1;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(juniorNameField, c);
		
		setSize(WIDTH, getHeight());
		
		registerField("name", new TextFieldWrapper(juniorNameField, 
				EntryType.STRING));
	}
}
