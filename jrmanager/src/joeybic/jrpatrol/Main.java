package joeybic.jrpatrol;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import joeybic.jrpatrol.ui.MainFrame;

public class Main 
{
	/**
	 * The instance of the main frame created at startup
	 */
	private static MainFrame frame;
	
	/**
	 * Create and store the main frame
	 * @params args cmd line arguments. we don't take any. ignored.
	 */
	public static void main(String args[])
	{
		Main.setLookAndFeel();
		frame = new MainFrame();
	}
	
	/**
	 * Sets the look and feel to use the system look and feel
	 */
	public static void setLookAndFeel()
	{
		// Set the look and feel to the system L&F
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (UnsupportedLookAndFeelException e)
		{
			Main.handleLookAndFeelError(e);
		}
		catch (ClassNotFoundException e)
		{
			Main.handleLookAndFeelError(e);
		}
		catch (InstantiationException e)
		{
			Main.handleLookAndFeelError(e);
		}
		catch (IllegalAccessException e)
		{
			Main.handleLookAndFeelError(e);
		}	
	}
		
	/**
	 * Print the error message and exits with code -1
	 */
	private static <T extends Exception> void handleLookAndFeelError(T e)
	{
		System.err.println("error setting look and feel: " +
				e.getMessage());
		e.printStackTrace();
		System.exit(-1);
	}
	
	/**
	 * Return the main application frame
	 * @return the instance of the main frame
	 */
	public static MainFrame getFrame()
	{
		return Main.frame;
	}
}
