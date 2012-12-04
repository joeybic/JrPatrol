package joeybic.jrpatrol.ui;

import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;

import joeybic.jrpatrol.Main;
import joeybic.jrpatrol.db.*;

public class DBDisplayTable extends JTable
{
	/**
	 * Column Types
	 */
	public enum ColumnType
	{
		INT,
		DOUBLE,
		STRING
	};
	
	/**
	 * IDK what this is. 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The query used to popuplate the table
	 */
	private String query;
	
	/**
	 * The names of the table columns
	 */
	private String columnLabels[];
	
	/**
	 * The names of the columns in the result set
	 */
	private String columnNames[];
	
	/**
	 * The type of each column
	 */
	private ColumnType columnTypes[];
	
	/**
	 * The types of any arguments to a prepared statment the query might use.
	 */
	private ColumnType preparedTypes[];
	
	/**
	 * The data model used
	 */
	private DBDisplayTableModel model;
	
	/**
	 * Create the table
	 * <p>uses null as the default value for preparedTypes</p>
	 * @param query			The query to pose
	 * @param columnLabels	what to label each column in the table
	 * @param columnNames	the name of each column in the result set
	 * @param columnTypes	the type of each column in the result set
	 */
	public DBDisplayTable(String query,
						  String columnLabels[],
						  String columnNames[],
						  ColumnType columnTypes[])
	{
		this(query, columnLabels, columnNames, columnTypes, null);
	}
	
	/**
	 * Create the table
	 * @param query			The query to pose
	 * @param columnLabels 	What to label each column in the table
	 * @param columnNames	The names of each column in the result set
	 * @param columnTypes	The type to extract for each column in the result set
	 * @param preparedTypes	The type of any parameters given to a prepared statment
	 */
	public DBDisplayTable(String query,
						  String columnLabels[],
						  String columnNames[],
						  ColumnType columnTypes[],
						  ColumnType preparedTypes[])
		throws IllegalArgumentException
	{
		// Invoke the super constructor
		super();
		
		this.query = query;
		setColumnInfo(columnLabels, columnNames, columnTypes);
		
		this.preparedTypes = preparedTypes;
		// Create and set the data model
		model = new DBDisplayTableModel(columnLabels);
		setModel(model);
		
		// Take up the entire given space
		setFillsViewportHeight(true);
		
		// Show the grid lines
		setShowGrid(true);
		
		// Rows are selectable, columns are not, cells are not
		setRowSelectionAllowed(true);
		setColumnSelectionAllowed(false);
		setCellSelectionEnabled(false);
	}
	
	/**
	 * Clear the table
	 */
	public void clearContents()
	{
		while (model.getRowCount() > 0)
		{
			model.removeRow(0);
		}
	}
	
	/**
	 * Perform the query
	 * @param args	The arguments to hand to the prepared statement
	 */
	public void executeQuery(Object... args) throws IllegalArgumentException
	{
		// Get a reference to teh db instance
		DB db = DB.getInstance();
		
		// See if the db is connected
		if (! db.getIsConnected())
		{
			return;
		}
		
		// Make sure our args are valid inore null
		if (preparedTypes == null)
		{
			if (args != null)
			{
				if (args.length > 0)
				{
					System.err.println("warning: extra prepared statement " +
							"arguments ignored");
				}
			}
		}
		else if (preparedTypes != null || preparedTypes.length > 0)
		{
			if (args == null)
			{
				throw new IllegalArgumentException("prepared statement " +
						"arguments do not match types");
			}
			
			if (args.length < preparedTypes.length)
			{
				throw new IllegalArgumentException("too few arguments" +
						" provided");
			}
			
			if (args.length > preparedTypes.length)
			{
				System.err.println("warning: extra prepared statement " +
						"arguments ignored");
			}
		}
		
		// Set our status
		Main.getFrame().setStatus("Refreshing db display table...");
		
		// Run the query
		try
		{
			// Create the prepared statement
			PreparedStatement stmt = db.getConnection().
				prepareStatement(query);
			
			// Load in the parameters
			for (int i = 0; i < args.length; i++)
			{
				switch (preparedTypes[i])
				{
					case INT:
						Integer ivalue = (Integer)args[i];
						stmt.setInt(i + 1, ivalue);
						break;
						
					case DOUBLE:
						Double dvalue = (Double)args[i];
						stmt.setDouble(i + 1, dvalue);
						break;
						
					case STRING:
						String svalue = (String)args[i];
						stmt.setString(i + 1, svalue);
						break;
				}
			}
			
			// Execute
			ResultSet rs = stmt.executeQuery();
			
			// Iterate through the result set
			Object data[] = null;
			while (rs.next())
			{
				data = new Object[columnNames.length];
				for (int i = 0; i < columnNames.length; i++)
				{
					switch (columnTypes[i])
					{
						case INT:
							data[i] = rs.getInt(columnNames[i]);
							break;
							
						case DOUBLE:
							data[i] = rs.getDouble(columnNames[i]);
							break;
						
						case STRING:
							data[i] = rs.getString(columnNames[i]);
							break;
					}
				}
				
				// Add the row to the table
				model.addRow(data);
			}
			
			// Close the result set and the statement
			rs.close();
			stmt.close();
			
			// Update our status
			Main.getFrame().setStatus("Success.");
		}
		catch (SQLException e)
		{
			Main.getFrame().setStatus("Failed.");
			System.err.println("error refreshing db display table: " +
					e.getMessage());
		}
	}
	
	/**
	 * Get the query
	 * @return the query string
	 */
	public String getQuery()
	{
		return query;
	}
	
	/**
	 * Set the query
	 * @param query the new query to use.
	 */
	public void setQuery(String query)
	{
		this.query = query;
	}
	
	/**
	 * Set table data
	 * @param columnLabels	The labels for each column
	 * @param columnNames	The names of the columns to search for
	 * @param columnTypes	The types of the columns to search for
	 */
	public void setColumnInfo(String columnLabels[], String columnNames[],
			ColumnType columnTypes[])
	{
		// Make sure our arguments are valid
		if (! (columnLabels.length == columnNames.length && 
				columnNames.length == columnTypes.length))
		{
			throw new IllegalArgumentException(
					"column labels, names, and types cannot differ in length");
		}
		
		// Store our stuff
		this.columnLabels = columnLabels;
		this.columnNames = columnNames;
		this.columnTypes = columnTypes;
	}
	
	/**
	 * Get teh column labels
	 * @return an array of column label strings
	 */
	public String[] getColumnLabels()
	{
		return columnLabels;
	}
	
	/**
	 * Get the column names
	 * @return an array of column name string
	 */
	public String[] getColumnNames()
	{
		return columnNames;
	}
	
	/**
	 * Get the column types
	 * @return an array of column types
	 */
	public ColumnType[] getColumnTypes()
	{
		return columnTypes;
	}
	
	/**
	 * Set the prepared statement types
	 * @param preparedTypes the new array of prepared statement argument types
	 */
	public void setPreparedTypes(ColumnType preparedTypes[])
	{
		this.preparedTypes = preparedTypes;
	}
	
	/**
	 * Get the prepared statement types
	 * @return an array of the prepared statement types
	 */
	public ColumnType[] getPreparedTypes()
	{
		return preparedTypes;
	}
}

/**
 * A data model for the table that percludes editing
 */
class DBDisplayTableModel extends DefaultTableModel
{
	/**
	 * IDK what this is
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Build a table with the given columns
	 * @param columnLabels 	What to call each column
	 */
	public DBDisplayTableModel(String columnLabels[])
	{
		super(columnLabels, 0);
	}
	
	/**
	 * Makes it so we cannot edit any cells
	 * @return false
	 */
	@Override
	public boolean isCellEditable(int r, int w)
	{
		return false;
	}
}
