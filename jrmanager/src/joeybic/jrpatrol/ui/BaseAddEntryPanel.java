package joeybic.jrpatrol.ui;

// System
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

// Internal
import joeybic.jrpatrol.*;
import joeybic.jrpatrol.db.*;

/**
 * A popup dialog for adding new entries on a given page
 * @author Joey Bickerstaff <joeybic@umich.edu>
 */
public class BaseAddEntryPanel extends BasePopupPanel 
{
	/**
	 *  IDK what this is
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The default label for the add button
	 */
	private static final String DEFAULT_ADD_TEXT = "Add Entry";
	
	/**
	 * The label for the cancel button
	 */
	private static final String CANCEL_TEXT = "Cancel";

	/**
	 * The string passed to the db at update time
	 */
	private String updateString;
	
	/**
	 * The names of the fields we should get from the content panel
	 */
	private String fieldNames[];
	
	/**
	 * The content panel to show
	 * (also where we get our data from)
	 */
	private BaseAddEntryContentPanel contentPanel;
	
	/**
	 * The "add" button
	 */
	private JButton addButton;
	
	/**
	 * An action listener for the action button
	 */
	private ActionListener actionListener = new ActionListener()
	{
		/**
		 * Handle when the action button is pressed
		 * @param evt	event info (unused)
		 */
		@Override
		public void actionPerformed(ActionEvent evt)
		{
			// Let the content panel verify it's input
			try
			{
				contentPanel.checkInputs();
			}
			catch (BadInputException e)
			{
				// If we get an error because they sent us
				// bad data, show the message to the user
				// in a message dialog
				JOptionPane.showMessageDialog(getPanel(), e.getMessage(),
						"Input Error", JOptionPane.ERROR_MESSAGE);
				
				// Then act like nothing happened
				return;
			}
			
			// Use the content panel to set the status message
			Main.getFrame().setStatus(contentPanel.getStatusMessage());
			
			// Create the runner to use
			Runnable runner = new AddEntryRunner((BaseAddEntryPanel)getPanel());
			
			// Create the running thread
			Thread thread = new Thread(runner);
			
			// Run the thread
			thread.run();
			
			// Get rid of the popup
			getPanel().hidePopup();
		}
	};
	
	private ActionListener cancelListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent evt)
		{
			// Hide the popup
			hidePopup();
		}
	};
	
	/**
	 * (default-ish) constructor
	 * 
	 * Uses default value of NULL for control
	 * Uses default value of DEFAULT_ADD_TEXT for addButtonLabel
	 * (DEFAULT_ADD_TEXT = "Add Entry")
	 * 
	 * @param updateString	The query posed to the database
	 * @param fieldNames	The names of the fields in the panel's field map
	 * @param contentPanel	The panel to display
	 */
	public BaseAddEntryPanel(String updateString,
							 String[] fieldNames,
							 BaseAddEntryContentPanel contentPanel)
	{
		this(updateString, fieldNames, contentPanel, DEFAULT_ADD_TEXT);
	}
	
	/**
	 * (half) parameterized constructor
	 * 
	 * Uses default value of NULL for control
	 * 
	 * @param updateString	The query posed to the database
	 * @param fieldNames	The names of the fields in the panel's field map
	 * @param contentPanel	The panel to display,
	 * @param addButtonLabel	The text to display on the "add" button
	 */
	public BaseAddEntryPanel(String updateString,
							 String[] fieldNames,
							 BaseAddEntryContentPanel contentPanel,
							 String addButtonLabel)
	{
		this(updateString, fieldNames, contentPanel, addButtonLabel, null);
	}
	
	/**
	 * Fully parameterized constructor
	 * @param updateString	The query posed to the database
	 * @param fieldNames	The names of the fields in the panel's field map
	 * @param contentPanel	The panel to display,
	 * @param addButtonLabel	The text to display on the "add" button
	 * @param control		The control that was activated to spawn this popup
	 */
	public BaseAddEntryPanel(String updateString,
							 String[] fieldNames,
							 BaseAddEntryContentPanel contentPanel, 
							 String addButtonLabel,
							 JComponent control)
	{
		super(control);
		setLayout(new BorderLayout());
		setUpdateString(updateString);
		setFieldNames(fieldNames);
		setContentPanel(contentPanel);
		
		// Create our control button panel
		FlowLayout flow = new FlowLayout(FlowLayout.CENTER, 10, 10);
		JPanel buttonPanel = new JPanel(flow);
		
		// Create the add button
		addButton = new JButton(addButtonLabel);
		addButton.addActionListener(actionListener);
		buttonPanel.add(addButton);
		
		// Create the cancel button
		JButton cancelButton  = new JButton(CANCEL_TEXT);
		cancelButton.addActionListener(cancelListener);
		buttonPanel.add(cancelButton);
		
		// Add the button panel to the bottom of the popup
		add(buttonPanel, BorderLayout.SOUTH);
	}
		
	/**
	 * Get the string that is used to query the database
	 * @return the string handed to the database server
	 */
	public String getUpdateString()
	{
		return updateString;
	}
	
	/**
	 * Set the query
	 * @param the query to pose to the database when update is pressed
	 */
	public void setUpdateString(String updateString)
	{
		this.updateString = updateString;
	}
	
	/**
	 * Get an array of the field names
	 * @return an array of strings popuplated with field names
	 */
	public String [] getFieldNames()
	{
		return fieldNames;
	}
	
	/**
	 * Set the field names
	 * @param fieldNames  the new field names to use
	 */
	public void setFieldNames(String... fieldNames)
	{
		this.fieldNames = fieldNames;
	}
	
	/**
	 * Get the associated content panel
	 * @return ref to the content panel
	 */
	public BaseAddEntryContentPanel getContentPanel()
	{
		return contentPanel;
	}
	
	/**
	 * Set the content panel
	 * @param contentPanel the new content panel to display
	 */
	public void setContentPanel(BaseAddEntryContentPanel contentPanel)
	{
		// Remove the current content panel
		if (this.contentPanel != null)
		{
			remove(this.contentPanel);
		}
		
		// Store the new panel
		this.contentPanel = contentPanel;
		
		// Add the new panel to the center
		add(this.contentPanel, BorderLayout.CENTER);
		
		// Validate the panel
		validate();
	}
	
	/**
	 * Get the add button label
	 * @return the label text for the add button
	 */
	public String getAddButtonLabel()
	{
		return addButton.getText();
	}
	
	/**
	 * Set the add button label
	 * @param addButtonLabel the new label for the add button
	 */
	public void setAddButtonLabel(String addButtonLabel)
	{
		addButton.setText(addButtonLabel);
	}
	
	/**
	 * Reset as normal, and reset the content panel as well.
	 */
	@Override
	public void reset()
	{
		super.reset();
		contentPanel.reset();
	}
}

class AddEntryRunner implements Runnable
{
	private BaseAddEntryPanel panel;
	
	public AddEntryRunner(BaseAddEntryPanel panel)
	{
		this.panel = panel;
	}
	
	@Override
	public void run()
	{
		DB db = DB.getInstance();
		if (! db.getIsConnected())
		{
			Main.getFrame().setStatus("Failed. No Connection.");
		}
		
		// Execute
		try
		{
			PreparedStatement stmt = db.getConnection().prepareStatement(
					panel.getUpdateString());
			
			BaseAddEntryContentPanel contentPanel = panel.getContentPanel();
			Object data;
			EntryType type;
			String fieldNames[] = panel.getFieldNames();
			for (int i = 0; i < contentPanel.getFieldCount(); i++)
			{
				type = contentPanel.getFieldType(fieldNames[i]);
				data = contentPanel.getFieldValue(fieldNames[i]);
				
				// Add the data to the query
				switch (type)
				{
					case INTEGER:
						stmt.setInt(i + 1, (Integer)data);
						break;
				
					case DOUBLE:
						stmt.setDouble(i + 1, (Double)data);
						break;
					
					case STRING:
						stmt.setString(i + 1, (String)data);
						break;
				}
			}
			
			stmt.executeUpdate();
			stmt.close();
			
			Main.getFrame().setStatus("Success.");
		}
		catch (SQLException e)
		{
			Main.getFrame().setStatus("Failed. SQL Error.");
			System.err.println("error adding entry to db: " + e.getMessage());
		}
		
		panel.hidePopup();
	}
}
	
	
	