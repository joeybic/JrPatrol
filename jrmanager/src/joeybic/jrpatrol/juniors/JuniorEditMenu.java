package joeybic.jrpatrol.juniors;

import javax.swing.*;

import joeybic.jrpatrol.ui.*;
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
	 * Instantiate the menu
	 */
	public JuniorEditMenu()
	{
		// Superconstructorify!!!
		super("Edit");
		
		// Create and add the add junior button
		addJunior = new SpawnPopupMenuItem(this, "Add Junior", 
				new AddJuniorPanel(), false);
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