package joeybic.jrpatrol;

import java.sql.*;

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
		frame = new MainFrame();
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
