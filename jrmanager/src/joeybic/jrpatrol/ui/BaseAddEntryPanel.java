package joeybic.jrpatrol.ui;

// System
import javax.swing.*;
import java.awt.event.*;
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
			Runnable runner = new AddEntryRunner(this);
			
			// Create the running thread
			Thread thread = new Thread(runner);
			
			// Run the thread
			thread.run();
			
			// Get rid of the popup
			getPanel().hidePopup();
		}
	};	
	
	public String getUpdateString()
	{
		return updateString;
	}
	
	public String [] getFieldNames()
	{
		return fieldNames;
	}
	
	/**
	 * Get the associated content panel
	 * @return ref to the content panel
	 */
	public BaseAddEntryContentPanel getContentPanel()
	{
		return contentPanel;
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
	
	
	