package joeybic.jrpatrol.sponsors;

import javax.swing.JMenu;

import joeybic.jrpatrol.ui.SpawnPopupMenuItem;

public class SponsorEditMenu extends JMenu
{
	/**
	 * idk
	 */
	private static final long serialVersionUID = 1L;

	public SponsorEditMenu()
	{
		super("Edit");
		add(new SpawnPopupMenuItem(this, "Add Sponsor", 
				new AddSponsorPanel(), true));	
		setEnabled(true);
		setVisible(true);
	}
}
