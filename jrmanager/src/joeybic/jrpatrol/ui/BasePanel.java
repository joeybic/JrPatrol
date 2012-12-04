package joeybic.jrpatrol.ui;

import javax.swing.JPanel;
import javax.swing.JMenu;

public abstract class BasePanel extends JPanel
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Every tab panel has an associated edit menu
	 */
	private JMenu editMenu = null;
	
	public BasePanel(JMenu editMenu)
	{
		super();
		this.editMenu = editMenu;
	}
	
	/**
	 * Get the edit menu associated with this panel
	 * @return the edit menu associated with a given panel.
	 */
	public JMenu getEditMenu()
	{
		return editMenu;
	}
	
	/**
	 * Set the edit menu associated with this panel
	 * @param editMenu the new value for the edit menu
	 */
	public void setEditMenu(JMenu editMenu)
	{
		this.editMenu = editMenu;
	}
	
	/**
	 * Useful for actionlisteners that need to refer to this by a name besides this.
	 * @return this
	 */
	public BasePanel getPanel()
	{
		return this;
	}
	
	/**
	 * Invoked by the parent container when a tab is swapped into.
	 */
	public abstract void refresh();
}
