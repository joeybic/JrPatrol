package joeybic.jrpatrol.ui;

import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;

import joeybic.jrpatrol.Main;

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
	
	/**
	 * Show the popup
	 */
	public void showPopup()
	{
		// Disable the control
		setEnabled(false);
		
		// Calculate the location at which to add the popup
		int x = Main.getFrame().getX() + 
				(Main.getFrame().getWidth() - this.getWidth()) / 2;
		int y = Main.getFrame().getContentPane().getLocationOnScreen().y + 10;
		
		// Spawn the popup
		Popup popup = PopupFactory.getSharedInstance().getPopup(Main.getFrame(),
				this,
				x, y);
		
		// Give the panel a reference to the popup
		setPopup(popup);
		
		// Show the popup
		popup.show();
	}
	
	/**
	 * Hide the popup
	 */
	public void hidePopup()
	{
		popup.hide();
		popup = null;
	}
}
