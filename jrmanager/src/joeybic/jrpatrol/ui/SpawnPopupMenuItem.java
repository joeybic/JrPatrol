package joeybic.jrpatrol.ui;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.*;

import joeybic.jrpatrol.db.DB;


/**
 * Spawn a popup with the given panel on click
 */
public class SpawnPopupMenuItem extends JMenuItem implements ActionListener
{
	/**
	 * IDK what this is for.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The parent menu
	 */
	private JMenu parent;
	
	/**
	 * The add junior popup panel
	 */
	private BasePopupPanel popupContent = null;
	
	/**
	 * The action listener implementation
	 */
	@Override
	public void actionPerformed(ActionEvent evt)
	{
		popupContent.showPopup();
	}
	
	/**
	 * Menu listener for disabling us if we don't have a db connection
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
			if (DB.getInstance().getIsConnected())
			{
				setEnabled(true);
			}
			else
			{
				setEnabled(false);
			}			
		}
	};

	public SpawnPopupMenuItem(JMenu parent,
							  String text,
							  BasePopupPanel popupContent)
	{
		this(parent, text, popupContent, false);
	}
							
	/**
	 * Initialize a new menu item
	 */
	public SpawnPopupMenuItem(JMenu parent, 
							  String text, 
							  BasePopupPanel popupContent,
							  boolean disableIfNoDBConnection)
	{
		// Invoke parent constructor and set the name of the button
		super(text);
		
		// Add itself as an action listener
		addActionListener(this);
		
		// Create the popup content panel
		this.popupContent = popupContent;
		
		this.parent = parent;
		setDisableIfNoDBConnection(disableIfNoDBConnection);
	}
	
	public void setDisableIfNoDBConnection(boolean disableIfNoDBConnection)
	{
		parent.removeMenuListener(menuListener);
		if (disableIfNoDBConnection)
		{
			parent.addMenuListener(menuListener);
		}
	}
}