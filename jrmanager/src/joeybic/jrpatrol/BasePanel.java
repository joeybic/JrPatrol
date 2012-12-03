package joeybic.jrpatrol;

import javax.swing.JPanel;

public abstract class BasePanel extends JPanel
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public BasePanel()
	{
		super();
	}
	
	public abstract void refresh();
}
