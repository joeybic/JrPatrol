package joeybic.jrpatrol.db;

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
	 * Creates a connection to the database at the given host.
	 * 
	 * @param host The host to connect to
	 * @param dbName The name of the database to connect to
	 */
	private void createConnection(String host, 
								  String dbName, 
								  String userName,
								  String password)
		throws SQLException, ConnectionException
	{
		if (connection != null || ! connection.isClosed())
		{
			throw new ConnectionException("connection already exists");
		}
		
		connection = DriverManager.getConnection(
				"jdbc:mysql://" + host + "/" + dbName,
				userName, password);
	}
	
	private void destroyConnection()
		throws ConnectionException, SQLException
	{
		if (connection == null || connection.isClosed())
		{
			throw new ConnectionException("no connection");
		}
		
		connection.close();
		connection = null;
	}
	
	/**
	 * Creates a connection to the database using the default location.
	 * 
	 * Default location is jdbc:mysql://localhost/JrPatrol
	 * 
	 * @param 
	 */
	public void openConnection(String userName, String password)
		throws ConnectionException
	{
		openConnection("localhost", "JrPatrol", userName, password);
	}
	
	/**
	 *  Opens an sql conneciton
	 */
	public void openConnection(String host,
							   String dbName,
							   String userName,
							   String password)
		throws ConnectionException
	{
		System.out.println("Opening connection to " + host + "/" + dbName + 
						   "...");
		try
		{
			createConnection(host, dbName, userName, password);
		}
		catch (SQLException e)
		{
			throw new ConnectionException(e.getMessage(), e);
		}
		
	    System.out.println("Success");
	}
	
	/**
	 * Closes an sql connection
	 */
	public void closeConnection() throws ConnectionException
	{	
		System.out.println("Closing db connection...");
		try
		{
			destroyConnection();
		}
		catch (SQLException e)
		{
			throw new ConnectionException(e.getMessage(), e);
		}
		finally
		{
			connection = null;
		}
		System.out.println("Success");
	}
	
	/**
	 * Returns true if the connection is open
	 * @return true if the connection is open. false if not.
	 */
	public boolean getIsConnected()
	{
		if (connection == null)
		{
			return false;
		}
		else
		{
			try
			{
				if (connection.isClosed())
				{
					return false;
				}
			}
			catch (SQLException e)
			{
				return false;
			}
		}
		
		return true;
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

