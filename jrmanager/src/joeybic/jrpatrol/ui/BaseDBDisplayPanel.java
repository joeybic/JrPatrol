package joeybic.jrpatrol.ui;

import javax.swing.*;
import joeybic.jrpatrol.ui.*;

public class BaseDBDisplayPanel extends BasePanel
{
	/**
	 * The db display table
	 */
	private DBDisplayTable table;
	
	/**
	 * Create a display with default value of null for the preparedTypes
	 * @param editMenu		The edit menu
	 * @param query			Query to hand to the table
	 * @param columnLabels	The labels for the table columns
	 * @param columnNames	The names of the fields in the result fields
	 * @param columnTypes	The types of the fields
	 */
	public BaseDBDisplayPanel(JMenu editMenu,
							  String query,
							  String columnLabels[],
							  String columnNames[],
							  DBDisplayTable.ColumnType columnTypes[])
	{
		this(editMenu, query, columnLabels, columnNames, columnTypes, null);
	}
	
	/**
	 * Constructorify!
	 * @param editMenu		The edit menu
	 * @param query			the query to give the table
	 * @param columnLabels	the labels of the table's columns
	 * @param columnNames	the names of the fields returned by the query
	 * @param columnTypes	The types of the fields returned by the query
	 * @param preparedTypes	The types of the arguments to pass into the prepared statement
	 */
	public BaseDBDisplayPanel(JMenu editMenu,
							  String query,
							  String columnLabels[],
							  String columnNames[],
							  DBDisplayTable.ColumnType columnTypes[],
							  DBDisplayTable.ColumnType preparedTypes[])
	{
		super(editMenu);
		
		// Create the table
		table = new DBDisplayTable(query, columnLabels, columnNames,
				columnTypes, preparedTypes);
		
		// Create a scroller with our table in it
		JScrollPane scroller = new JScrollPane(table);
		
		// Add the table
		add(scroller);
	}
	
	/**
	 * Refresh the table
	 */
	@Override
	public void refresh()
	{
		table.clearContents();
		table.executeQuery();
	}
}
