/**
 * 
 */
package joeybic.jrpatrol;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

import joeybic.jrpatrol.db.*;

/**
 * @author joeybic
 *
 */
public class MainMenuBar extends JMenuBar 
{
	//////////////////////////////////////
	/////////////  DB MENU  //////////////
	//////////////////////////////////////
	
	/**
	 * The DB Menu
	 * 
	 * Has items:
	 * - Connect/Disconnect
	 * - Exit
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
	 * The exit menu item. Nuff said.
	 */
	private JMenuItem dbMenuExit = null;
	
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
				int x = (MainFrame.WIDTH - ConnectPopup.WIDTH) / 2;
				int y = (MainFrame.HEIGHT - ConnectPopup.HEIGHT) / 2;
				
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
}
