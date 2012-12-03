package joeybic.jrpatrol;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

import joeybic.jrpatrol.db.*;
import joeybic.jrpatrol.juniors.JuniorPanel;

public class MainFrame extends JFrame
{
	/**
	 * The width of the main window
	 */
	public static final int WIDTH = 800;
	
	/**
	 * The height of the main window
	 */
	public static final int HEIGHT = 600;
	
	/**
	 * IDK what this is for. 
	 */
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * The listener for tab change.
	 * 
	 * Retrieves the source object (as a JTabbedPane), gets the current index
	 * from the pane (as a BasePanel) and invokes the refresh() method.
	 */
	private ChangeListener changeListener = new ChangeListener()
	{
		public void stateChanged(ChangeEvent evt)
		{
			JTabbedPane pane = (JTabbedPane)evt.getSource();
			BasePanel panel = (BasePanel)pane.getSelectedComponent();
			
			if (panel != null)
			{
				panel.refresh();
			}
		}
	};
	
	/**
	 * Automatically closes the database on exit
	 */
	private WindowAdapter closeListener = new WindowAdapter()
	{
		@Override
		public void windowClosed(WindowEvent evt)
		{
			// Shut down the database connection
			if (DB.getInstance().getIsConnected())
			{
				try
				{
					DB.getInstance().closeConnection();
				}
				catch (ConnectionException e)
				{
					System.err.println("warning: failed to close connection " +
							"correctly: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	};
	
	/**
	 * The main menu bar for the window
	 */
	private MainMenuBar menuBar;
	
	/**
	 * The status bar at the bottom of the screen
	 */
	private JLabel statusBar = null;
	
	public MainFrame()
	{
		super();
		
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Junior Manager");
		
		addWindowListener(closeListener);
		
		JTabbedPane pane = new JTabbedPane();
		pane.addChangeListener(changeListener);
		
		// Add Tabs
		pane.addTab("Junior Roster", new JuniorPanel());
		
		// Add tab panel
		add(pane);
		
		// Create the status bar
		statusBar = new JLabel("Status: ", JLabel.LEFT);
		
		// Add the status bar
		add(statusBar);
		
		// Make me visible!
		setVisible(true);
	}
	
	/**
	 * Set the status bar text to the given value
	 * @param status the new text to display in the status bar
	 */
	void setStatus(String status)
	{
		statusBar.setText("Status: " + status);
	}
	
	/**
	 * Get the main menu bar for the frame
	 * @return a reference to the main menu bar
	 */
	public MainMenuBar getMenuBar()
	{
		return menuBar;
	}
}
