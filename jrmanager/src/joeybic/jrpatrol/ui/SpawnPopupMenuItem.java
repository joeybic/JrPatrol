package joeybic.jrpatrol.ui;

import javax.swing.*;
import java.awt.event.*;

import joeybic.jrpatrol.ui.*;
import joeybic.jrpatrol.*;

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
	 * Initialize a new menu item
	 */
	public SpawnPopupMenuItem(String text, BasePopupPanel popupContent)
	{
		// Invoke parent constructor and set the name of the button
		super(text);
		
		// Add itself as an action listener
		addActionListener(this);
		
		// Create the popup content panel
		this.popupContent = popupContent;
	}
}