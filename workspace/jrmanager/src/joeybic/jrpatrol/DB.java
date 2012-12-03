package joeybic.jrpatrol;

import java.sql.*;

public class DB 
{
	/**
	 * The connection to the database.
	 */
	private Connection connection;
	
	/**
	 * Loads the mysql jdbc driver and creates the database connection.
	 */
	private DB()
	{
		loadDriver();
	}
	
	/**
	 * For holding the singleton instance. Uses block initialization pattern.
	 * @author Joey
	 *
	 */
	private static class Holder
	{
		public static final DB instance = new DB();
	}
	
	/**
	 * Gets the singleton instance of this class
	 * @return The singleton instance of the database
	 */
	public static DB getInstance()
	{
		return Holder.instance;
	}
	
	/**
	 * Load the mysql jdbc driver into the driver manager.
	 */
	private void loadDriver()
	{
		// Load the driver into the driver manager
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}
		catch (Exception e)
		{
			System.err.println("error loading mysql jdbc driver: " + e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Creates a connection to the junior patrol database on the localhost.
	 */
	private void createConnection()
	{
		connection = null;
		try
		{
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/JrPatrol",
					"joeybic",
					"");
		}
		catch (SQLException e)
		{
			System.err.println("error opening connection to jr patrol database: " +
					e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 *  Opens an sql conneciton
	 */
	public void openConnection()
	{
		System.out.println("Opening db connection...");
	    createConnection();
	    System.out.println("Success");
	}
	
	/**
	 * Closes an sql connection
	 */
	public void closeConnection()
	{
		System.out.println("Closing db connection...");
		if (connection == null)
		{
			System.out.println("Failed");
			System.err.println("error: cannot close unopened connection");
			System.exit(-1);
		}
		
		try
		{
			if (connection.isClosed())
			{
				System.out.println("Failed");
				System.err.println("error: cannot close unopened connection");
				System.exit(-1);
			}
			
			connection.close();
		}
		catch (SQLException e)
		{
			System.out.println("Failed");
			System.err.println("error closing db connection: " + e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
		
		connection = null;
		System.out.println("Success");
	}
	
	/**
	 * Gets the connection to the jr database
	 * @return the connection instance.
	 */
	public Connection getConnection()
	{	
		return connection;
	}
}

