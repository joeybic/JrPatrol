package joeybic.jrpatrol.juniors;

import javax.swing.*;

import java.sql.*;

import java.awt.event.*;
import java.awt.*;
import java.sql.Connection;

import joeybic.jrpatrol.*;
import joeybic.jrpatrol.db.DB;

public class AddJuniorPanel extends BasePopupPanel
{
	/**
	 * The width of the name field
	 */
	private static final int FIELD_WIDTH = 30;
	
	/**
	 * Our prepared statement
	 */
	private static final String ADD_JUNIOR =
			"INSERT INTO Juniors(name) VALUES(?)";
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
	
	/**
	 * An action listener for the "Add" button
	 */
	private ActionListener addButtonListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent evt)
		{
			// Extract the name
			final String juniorName = juniorNameField.getText();
			
			if (juniorName == null || (juniorName.length() == 0))
			{
				JOptionPane.showMessageDialog(getPanel(), "Please enter a name", 
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// Make a runner for the add
			Runnable runner = new Runnable()
			{
				@Override
				public void run()
				{
					// Set the status in the status bar
					Main.getFrame().setStatus("Adding junior...");
					
					// Do the SQL Transaction
					try
					{
						// Get our connection
						Connection conn = DB.getInstance().getConnection();
						
						// Load a statement with our query
						PreparedStatement stmt = conn.prepareStatement(AddJuniorPanel.ADD_JUNIOR);
						
						// Load the string into the query
						stmt.setString(1, juniorName);
						
						// Run the query and close the result
						stmt.executeUpdate();
						
						// Close the statement
						stmt.close();
						
						// Update the status message
						Main.getFrame().setStatus("Success.");
					}
					catch (SQLException e)
					{
						Main.getFrame().setStatus("Failed.");
						System.err.println("error adding new junior record: " +
								e.getMessage());
					}
					finally
					{
						// Re-enable the control
						JuniorEditMenu menu = (JuniorEditMenu)Main.getFrame().getEditMenu();
						menu.getAddJuniorItem().setEnabled(true);
						
						// Refresh the tab
						Main.getFrame().refreshCurrentTab();
						
						// Reset ourself
						reset();
					}
				}
			};
			
			// Run the thread
			Thread thread = new Thread(runner);
			thread.start();
			
			// Hide the panel
			popup.hide();
		}
	};
	
	/**
	 * Action listener for the close action
	 */
	private ActionListener cancelListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent evt)
		{
			// Re-enable control
			JuniorEditMenu menu = (JuniorEditMenu)Main.getFrame().getEditMenu();
			menu.getAddJuniorItem().setEnabled(true);
			
			// Hide the popup
			popup.hide();
		}
	};
		
	/**
	 * Reset
	 */
	@Override
	public void reset()
	{
		// Reset the name field
		juniorNameField.setText("");
	}
	
	/**
	 * Constructor
	 */
	public AddJuniorPanel()
	{
		super();
		
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
		juniorNameField = new JTextField(AddJuniorPanel.FIELD_WIDTH);
		c.gridx = 1;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(juniorNameField, c);
		
		// Create the add and cancel buttons
		JButton add = new JButton("Add");
		JButton cancel = new JButton("Cancel");
		add.addActionListener(addButtonListener);
		cancel.addActionListener(cancelListener);
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		add(add, c);
		c.gridx = 2;
		add(cancel, c);
		
		setSize(WIDTH, getHeight());
	}
}
