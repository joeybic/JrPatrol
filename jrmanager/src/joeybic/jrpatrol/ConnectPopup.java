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
public class ConnectPopup extends JPanel implements ActionListener
{
	/**
	 * IDK what this is. Added by eclipse. 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The width of the panel
	 */
	public static final int WIDTH = MainFrame.WIDTH / 2;
	
	/**
	 * The height of the panel
	 */
	public static final int HEIGHT = MainFrame.HEIGHT / 2;
	
	/**
	 * The parent popup of the content
	 */
	private Popup popup = null;
	
	/**
	 * Label for the host field
	 */
	private JLabel hostLabel = null;
	
	/**
	 * Input field for host
	 */
	private JTextField hostField = null;
	
	/**
	 * Label for the database name
	 */
	private JLabel dbNameLabel = null;
	
	/**
	 * Input field for database name
	 */
	private JTextField dbNameField = null;
	
	/**
	 * Label for user name
	 */
	private JLabel userNameLabel = null;
	
	/**
	 * Field for user name
	 */
	private JTextField userNameField = null;
	
	/**
	 * Label for password field
	 */
	private JLabel passwordLabel = null;
	
	/**
	 * Password entry field
	 */
	private JPasswordField passwordField = null;
	
	/**
	 * The connect thread
	 */
	private ConnectionRunner connect = null;
	
	/**
	 * Action listener for connection action
	 */
	@Override
	public void actionPerformed(ActionEvent evt)
	{
		// Get the host string
		connect.host = hostField.getText();
		
		// Get the db name
		connect.dbName = dbNameField.getText();
		
		// Get the user name
		connect.userName = userNameField.getText();
		
		// Make sure that the user name has a value
		if (connect.userName == "")
		{
			JOptionPane.showMessageDialog(this, "Please enter a user name",
					"Error",
					JOptionPane.ERROR_MESSAGE);
			connect.reset();
			return;
		}
		
		// Get the password
		connect.password = passwordField.getPassword();
		
		// Run the runner
		Thread connectThread = new Thread(connect);
		connectThread.run();
		
		// Reset our fields
		reset();
		
		// Hide the parent popup
		popup.hide();
	}
	
	/**
	 * Creates a new connection popup with default values
	 */
	public ConnectPopup()
	{
		// TODO
	}
	
	/**
	 * Resets our fields to default values
	 */
	public void reset()
	{
		// TODO
	}
	
	/**
	 * Sets the parent popup of the popup panel
	 */
	public void setPopup(Popup popup)
	{
		this.popup = popup;
	}				
}

class ConnectionRunner implements Runnable
{
	public String host = "";
	public String dbName = "";
	public String userName = "";
	public char password[] = { };
	
	public void reset()
	{
		host = "";
		dbName = "";
		userName = "";
		for (int i = 0; i < password.length; i++)
		{
			password[i] = '\0';
		}
	}
	
	@Override
	public void run()
	{
		// Set the status
		Main.getFrame().setStatus("Connecting...");
		
		// Connect
		try
		{
			DB.getInstance().openConnection(host, dbName, userName, 
											new String(password));
		}
		catch (ConnectionException e)
		{
			Main.getFrame().setStatus("Failed.");
			System.err.println("error connecting to database: " +
					e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			// Re-enable the connection control on the menu
			Main.getFrame().getMenuBar().dbMenuConnect.setEnabled(true);
			
			// Update the button's text
			Main.getFrame().getMenuBar().dbMenuListener.menuSelected(null);
			
			// Reset our fields
			reset();
		}
	}
}
