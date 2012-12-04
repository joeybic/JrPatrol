package joeybic.jrpatrol;

import javax.swing.JPanel;
import javax.swing.Popup;

public class BasePopupPanel extends JPanel
{
	/**
	 * The parent popup
	 */
	protected Popup popup;
	
	/**
	 * Get the parent popup
	 * @return the parent popup
	 */
	public Popup getPopup()
	{
		return popup;
	}
	
	/**
	 * Set the parent popup
	 * @param popup the new parent popup
	 */
	public void setPopup(Popup popup)
	{
		this.popup = popup;
	}
	
	/**
	 * Useful. Trust me.
	 */
	public BasePopupPanel getPanel()
	{
		return this;
	}
	
	/**
	 * Resets the fields for a new popup
	 */
	public void reset()
	{
		// Reset the parent popup to null.
		this.popup = null;
	}
}
