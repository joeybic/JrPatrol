package joeybic.jrpatrol.juniors;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.awt.event.*;

import joeybic.jrpatrol.*;
import joeybic.jrpatrol.db.*;

/**
 * Edit menu for the junior tab
 */
public class JuniorEditMenu extends JMenu 
{
	/**
	 * IDK what this is. Added automatically by eclipse. 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The menu item for adding a junior
	 */
	private AddJuniorMenuItem addJunior = null;
	
	/**
	 * A listener for when the menu is opened
	 */
	private MenuListener menuListener = new MenuListener()
	{

		@Override
		public void menuCanceled(MenuEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void menuDeselected(MenuEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void menuSelected(MenuEvent e)
		{
			// Is the database connected
			if (DB.getInstance().getIsConnected())
			{
				addJunior.setEnabled(true);
			}
			else
			{
				addJunior.setEnabled(false);
			}
			
		}
	
	};
	
	/**
	 * Instantiate the menu
	 */
	public JuniorEditMenu()
	{
		// Superconstructorify!!!
		super("Edit");
		
		// Register our menu listener
		addMenuListener(menuListener);
		
		// Create and add the add junior button
		addJunior = new AddJuniorMenuItem();
		add(addJunior);
	}
	
	/**
	 * Get the addjunior item
	 * @return teh add junior button
	 */
	public AddJuniorMenuItem getAddJuniorItem()
	{
		return addJunior;
	}
}

/**
 * For adding a junior
 */
class AddJuniorMenuItem extends JMenuItem implements ActionListener
{
	/**
	 * IDK what this is for.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The text to display on this menu item
	 */
	public static final String TEXT = "Add Junior";
	
	/**
	 * The add junior popup panel
	 */
	private AddJuniorPanel popupContent = null;
	
	/**
	 * The action listener implementation
	 */
	@Override
	public void actionPerformed(ActionEvent evt)
	{
		// Disable the control
		setEnabled(false);
		
		// Calculate the location at which to add the popup
		int x = Main.getFrame().getX() + 
				(Main.getFrame().getWidth() - popupContent.getWidth()) / 2;
		int y = Main.getFrame().getContentPane().getLocationOnScreen().y + 10;
		
		// Spawn the popup
		Popup popup = PopupFactory.getSharedInstance().getPopup(Main.getFrame(),
				popupContent,
				x, y);
		
		// Give the panel a reference to the popup
		popupContent.setPopup(popup);
		
		// Show the popup
		popup.show();
	}
	
	/**
	 * Initialize a new menu item
	 */
	public AddJuniorMenuItem()
	{
		// Invoke parent constructor and set the name of the button
		super(AddJuniorMenuItem.TEXT);
		
		// Add itself as an action listener
		addActionListener(this);
		
		// Create the popup content panel
		popupContent = new AddJuniorPanel();
	}
}