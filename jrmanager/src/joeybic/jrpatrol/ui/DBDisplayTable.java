package joeybic.jrpatrol.ui;

import javax.swing.*;
import javax.swing.table.*;

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
	 * @param columnLabels 	What to label each column in the table
	 * @param columnNames	The names of each column in the result set
	 * @param columnTypes	The type to extract for each column in the result set
	 * @param preparedTypes	The type of any parameters given to a prepared statment
	 */
	public DBDisplayTable(String columnLabels[],
						  String columnNames[],
						  ColumnType columnTypes[],
						  ColumnType preparedTypes[])
		throws IllegalArgumentException
	{
		// Invoke the super constructor
		super();
	
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
		if (preparedTypes != null || preparedTypes.length > 0)
		{
			if (args == null)
			{
				throw new IllegalArgumentException("prepared statement arguments do not match types");
			}
			
			if (args.length != preparedTypes.length)
			{
				
		}
		
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
