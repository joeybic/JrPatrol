/**
 * 
 */
package joeybic.jrpatrol.ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import joeybic.jrpatrol.Main;
import joeybic.jrpatrol.db.*;

/**
 * @author joeybic
 *
 */
public class ConnectPopup extends BasePopupPanel implements ActionListener
{
	/**
	 * IDK what this is. Added by eclipse. 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The width of all the fields
	 */
	private static final int FIELD_WIDTH = 30;

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
	 * The default text for the host field
	 */
	private static final String DEFAULT_HOST_TEXT = "localhost";
	
	/**
	 * Label for the database name
	 */
	private JLabel dbNameLabel = null;
	
	private static final String DEFAULT_DB_NAME = "JrPatrol";
	
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
		if (connect.userName == null || connect.userName == "" || connect.userName.length() == 0)
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
		
		popup.hide();
	}
	
	/**
	 * Creates a new connection popup with default values
	 */
	public ConnectPopup()
	{
		// Invoke base constructor
		super();
		
		// create the connect runner
		connect = new ConnectionRunner();
		connect.parent = this;
		
		// set the layout
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(layout);
		
		// Set our size
		//setPreferredSize(new Dimension(ConnectPopup.WIDTH, ConnectPopup.HEIGHT));
		//setSize(ConnectPopup.WIDTH, ConnectPopup.HEIGHT);
		setMinimumSize(new Dimension(ConnectPopup.WIDTH, ConnectPopup.HEIGHT));
		
		// Host
		hostLabel = new JLabel("Host: ");
		hostField = new JTextField(ConnectPopup.FIELD_WIDTH);
		hostField.addActionListener(this);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(hostLabel, c);
		c.gridx = 1;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(hostField, c);
	
		// DB Name
		dbNameLabel = new JLabel("Database Name: ");
		dbNameField = new JTextField(ConnectPopup.FIELD_WIDTH);
		dbNameField.addActionListener(this);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		add(dbNameLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridwidth = 3;
		add(dbNameField, c);
		
		// User Name
		userNameLabel = new JLabel("User Name: ");
		userNameField = new JTextField(ConnectPopup.FIELD_WIDTH);
		userNameField.addActionListener(this);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		add(userNameLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridwidth = 3;
		add(userNameField, c);
		
		// Password
		passwordLabel = new JLabel("Password: ");
		passwordField = new JPasswordField();
		passwordField.addActionListener(this);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		add(passwordLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridwidth = 3;
		add(passwordField, c);
		
		// Button for connecting
		JButton connectButton = new JButton("Connect");
		connectButton.addActionListener(this);
		//c.fill = GridBagConstraints.NONE;
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		add(connectButton, c);
		
		// Button for canceling
		ActionListener listener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				// Re-enable the button
				Main.getFrame().getMainMenuBar().dbMenuConnect.setEnabled(true);
				
				// Destroy the popup.
				popup.hide();
			}
		};
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(listener);
		c.gridx = 2;
		c.gridwidth = 1;
		add(cancelButton, c);
		
	
		// Set our fields to default values
		reset();
		
		// Show us!
		setVisible(true);
		
		// Set the width
		setSize(WIDTH, getHeight());
	}
	
	/**
	 * Resets our fields to default values
	 */
	@Override
	public void reset()
	{
		super.reset();
		
		// Clear all of our fields
		hostField.setText(ConnectPopup.DEFAULT_HOST_TEXT);
		hostField.setEnabled(true);
		hostField.setEditable(true);
		dbNameField.setText(ConnectPopup.DEFAULT_DB_NAME);
		dbNameField.setEnabled(true);
		dbNameField.setEditable(true);
		userNameField.setText("");
		userNameField.setEnabled(true);
		userNameField.setEditable(true);
		passwordField.setText("");
		passwordField.setEnabled(true);
		passwordField.setEditable(true);
	}
	
	/**
	 * Sets the parent popup of the popup panel
	 */
	public void setPopup(Popup popup)
	{
		this.popup = popup;
	}	
	
	/**
	 * Gets teh parent popup of the panel
	 */
	public Popup getPopup()
	{
		return popup;
	}
}

class ConnectionRunner implements Runnable
{
	public ConnectPopup parent = null;
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
			Main.getFrame().setStatus("Success.");
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
			// Get the main menu bar
			MainMenuBar menuBar = Main.getFrame().getMainMenuBar();
			
			// Re-enable the connection control on the menu
			menuBar.dbMenuConnect.setEnabled(true);
			
			// Update the button's text
			menuBar.dbMenuListener.menuSelected(null);
			
			// Reset our fields
			reset();
			
			// Refresh the currently displayed tab
			Main.getFrame().refreshCurrentTab();			
		}
	}
}
