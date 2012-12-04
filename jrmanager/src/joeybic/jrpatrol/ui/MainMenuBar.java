/**
 * 
 */
package joeybic.jrpatrol.ui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

import joeybic.jrpatrol.Main;
import joeybic.jrpatrol.db.*;

/**
 * @author joeybic
 *
 */
public class MainMenuBar extends JMenuBar 
{
	/**
	 * IDK what this is.  Added automatically by eclipse.
	 */
	private static final long serialVersionUID = 1L;
	
	//////////////////////////////////////
	/////////////  DB MENU  //////////////
	//////////////////////////////////////

	/**
	 * The DB Menu
	 * 
	 * Has items:
	 * - Connect/Disconnect
	 */
	private JMenu dbMenu = null;
	
	/**
	 * Create a connection to the database using this item.
	 * 
	 * Becomes disconnect once connection is established.
	 */
	protected JMenuItem dbMenuConnect = null;

	/**
	 * The label text for the connect menu item when the db is disconnected.
	 */
	private static final String CONNECTLABEL_CONNECT = "Connect";
	
	/**
	 * The label text for the connect menu item when the db is connected.
	 */
	private static final String CONNECTLABEL_DISCONNECT = "Disconnect";
	
	/**
	 * Listener for the db menu for updating the status of the connect button
	 */
	protected MenuListener dbMenuListener = new MenuListener()
	{
		
		@Override
		public void menuSelected(MenuEvent e)
		{
			// Check the status of the connection
			if (DB.getInstance().getIsConnected())
			{
				// Yes, then set the string to disconnect
				dbMenuConnect.setText(MainMenuBar.CONNECTLABEL_DISCONNECT);
			}
			else
			{
				// No, then set the string to connect
				dbMenuConnect.setText(MainMenuBar.CONNECTLABEL_CONNECT);
			}
		}
		
		@Override
		public void menuDeselected(MenuEvent e)
		{
			// Nothing
		}
		
		@Override
		public void menuCanceled(MenuEvent e)
		{
			// Nothing
		}
	};
	
	/**
	 * The contents for the connect popup.
	 */
	private ConnectPopup connectPopup = null;
	
	/**
	 * Listener for the connect menu item
	 */
	private ActionListener dbMenuConnectListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// Case 1: Connect
			if (dbMenuConnect.getText() == MainMenuBar.CONNECTLABEL_CONNECT)
			{
				// Disable the control
				dbMenuConnect.setEnabled(false);
				
				// Calculate the position at which the popup should appear
				int x = Main.getFrame().getX() + 
						(Main.getFrame().getContentPane().getWidth() - 
								connectPopup.getWidth()) / 2;
				int y = Main.getFrame().getContentPane().getLocationOnScreen().y + 10;
				
				// Reset the popup content
				connectPopup.reset();
				
				// Spawn the popup control
				Popup pop = PopupFactory.getSharedInstance().getPopup(
						Main.getFrame(),
						connectPopup,
						x, y);
				
				// Give the popup panel a reference to the popup itself
				connectPopup.setPopup(pop);
				
				// Show the connect popup
				pop.show();
			}
			else if (dbMenuConnect.getText() == MainMenuBar.CONNECTLABEL_DISCONNECT)
			{
				dbMenuConnect.setEnabled(false);
				runDisconnect();
			}
		}
	};
	
	/**
	 * Runs the disconnect function in a new thread
	 */
	public void runDisconnect()
	{
		// Create the runnable to use
		Runnable runner = new Runnable()
		{
			@Override
			public void run()
			{
				Main.getFrame().setStatus("Disconnecting...");
				try
				{
					DB.getInstance().closeConnection();
					Main.getFrame().setStatus("Success.");
				}
				catch (ConnectionException e)
				{
					Main.getFrame().setStatus("Failed.");
					System.err.println("error closing database connection: " +
							e.getMessage());
					e.printStackTrace();
				}
				finally
				{
					// Renable the button
					dbMenuConnect.setEnabled(true);
					
					// Update the button text
					dbMenuListener.menuSelected(null);
					
					// Update the current pane
					Main.getFrame().refreshCurrentTab();
				}	
			}
		};
		
		// Run the thread
		Thread thread = new Thread(runner);
		thread.start();
	}
	
	/**
	 * Gets the db menu
	 * @return a reference to the db menu instance
	 */
	public JMenu getDBMenu()
	{
		return dbMenu;
	}
	
	/**
	 * Build the menu bar with default values
	 */
	public MainMenuBar()
	{
		// Parent constructor
		super();
		
		// Create the db menu
		dbMenu = new JMenu("Database");
		dbMenu.addMenuListener(dbMenuListener);
		
		// Add the connect button to the menu bar
		dbMenuConnect = new JMenuItem(MainMenuBar.CONNECTLABEL_CONNECT);
		dbMenuConnect.addActionListener(dbMenuConnectListener);
		dbMenu.add(dbMenuConnect);
		
		// Add the db menu
		add(dbMenu, 0);
		
		// Add the current edit menu
		//add(Main.getFrame().getEditMenu(), 1);
		
		// Create the popup panel
		connectPopup  = new ConnectPopup();
		
		// Show up!
		setVisible(true);
	}
}