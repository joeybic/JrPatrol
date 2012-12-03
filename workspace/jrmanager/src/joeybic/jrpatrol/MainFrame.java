package joeybic.jrpatrol;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

import joeybic.jrpatrol.juniors.JuniorPanel;

public class MainFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
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
	
	private WindowAdapter closeListener = new WindowAdapter()
	{
		@Override
		public void windowClosed(WindowEvent evt)
		{
			// Shut down the database connection
			DB.getInstance().closeConnection();
		}
	};
	
	public MainFrame()
	{
		super();
		
		// Open a connection to then database
		DB.getInstance().openConnection();
		
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
		
		// Make me visible!
		setVisible(true);
	}
}
