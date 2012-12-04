package joeybic.jrpatrol.ui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

import joeybic.jrpatrol.db.*;
import joeybic.jrpatrol.juniors.*;
import joeybic.jrpatrol.sponsors.*;

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
	 * The tabbed pane!
	 */
	private JTabbedPane pane = null;
	
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
			
			if (menuBar.getComponentCount() >= 2)
			{
				// Remove the current edit menu and add the new one in it's place.
				menuBar.remove(1);
			}
			
			//System.out.println("Adding...");
			menuBar.add(panel.getEditMenu(), 1);
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
		
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Junior Manager");
		

		
		
		addWindowListener(closeListener);
		
		// Create the tab pane
		pane = new JTabbedPane();
		
		// Create and add the menu bar
		menuBar = new MainMenuBar();
		setJMenuBar(menuBar);
		
		// Create the status bar
		statusBar = new JLabel("Status: ", JLabel.LEFT);
		
		// Add the status bar
		add(statusBar, BorderLayout.SOUTH);
	
		// Add Tabs
		JuniorPanel jp = new JuniorPanel();
		pane.addTab("Junior Roster", jp);
		
		SponsorPanel sp = new SponsorPanel();
		pane.addTab("Sponsors", sp);
		
		// Add the change listener
		pane.addChangeListener(changeListener);
		
		// Add tab panel
		add(pane);
		
		
		menuBar.add(jp.getEditMenu());
		// Make me visible!
		setVisible(true);
	}
	
	/**
	 * Set the status bar text to the given value
	 * @param status the new text to display in the status bar
	 */
	public void setStatus(String status)
	{
		statusBar.setText("Status: " + status);
	}
	
	/**
	 * Get the main menu bar (the built in method is insufficient).
	 * @return the MAIN menu bar
	 */
	public MainMenuBar getMainMenuBar()
	{
		return menuBar;
	}
	
	/**
	 * Refresh the current pane in the tab pane
	 */
	public void refreshCurrentTab()
	{
		BasePanel current = (BasePanel)pane.getSelectedComponent();
		current.refresh();
	}
	
	public JMenu getEditMenu()
	{
		BasePanel panel = (BasePanel)pane.getSelectedComponent();
		return panel.getEditMenu();
	}
}
