package joeybic.jrpatrol.ui;

// System
import javax.swing.*;
import java.awt.event.*;

// Internal
import joeybic.jrpatrol.*;

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
	 * A control to disable when the popup gets shown
	 * If null, this behavior is disabled.
	 */
	private JComponent associatedControl;
	
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
			
			// Load all of our data out of the the content pane
			Object data[] = new Object[contentPanel.getFieldCount()];
			for (int i = 0; i < contentPanel.getFieldCount(); i++)
			{
				data[i] = contentPanel.getFieldValue(fieldNames[i]);
			}
			
			// Create the runner to use
			Runnable runner = new AddEntryRunner(this, data);
			
			// Create the running thread
			Thread thread = new Thread(runner);
			
			// Run the thread
			thread.run();
			
			// Get rid of the popup
			getPanel().hidePopup();
		}
	};			
}
