package joeybic.jrpatrol.ui;

import javax.swing.*;

import joeybic.jrpatrol.Main;

public class BasePopupPanel extends JPanel
{
	/**
	 * Whatever
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The parent popup
	 */
	protected Popup popup;
	
	/**
	 * A control to disable when the popup gets shown
	 * If null, this behavior is disabled.
	 */
	private JComponent associatedControl;
	
	/**
	 * CONSTRUCTOR
	 */
	public BasePopupPanel()
	{
		this(null);
	}
	
	/**
	 * CONSTRUCTOR
	 * @param associatedControl	From whence we spawned
	 */
	public BasePopupPanel(JComponent associatedControl)
	{
		super();
		setAssociatedControl(associatedControl);
		setPopup(null);
	}
	
	/**
	 * Get the associated control
	 * @return the control from whence we spawned
	 */
	public JComponent getAssociatedControl()
	{
		return associatedControl;
	}
	
	/**
	 * Set the associated control
	 * @param associatedControl	The control from whence we spawned (or null if disabled)
	 */
	public void setAssociatedControl(JComponent associatedControl)
	{
		this.associatedControl = associatedControl;
	}
	
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
		if (associatedControl != null)
		{
			associatedControl.setEnabled(false);
		}
		
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
		if (associatedControl != null)
		{
			associatedControl.setEnabled(true);
		}
		
		reset(); 
		popup.hide();
		popup = null;
	}
}
