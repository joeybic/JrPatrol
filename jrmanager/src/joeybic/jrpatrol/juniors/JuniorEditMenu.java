package joeybic.jrpatrol.juniors;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.awt.event.*;

import joeybic.jrpatrol.ui.*;
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
	private SpawnPopupMenuItem addJunior = null;
	
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
		addJunior = new SpawnPopupMenuItem("Add Junior", new AddJuniorPanel());
		add(addJunior);
	}
	
	/**
	 * Get the addjunior item
	 * @return teh add junior button
	 */
	public SpawnPopupMenuItem getAddJuniorItem()
	{
		return addJunior;
	}
}