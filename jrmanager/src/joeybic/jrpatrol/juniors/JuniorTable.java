package joeybic.jrpatrol.juniors;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import joeybic.jrpatrol.db.DB;

public class JuniorTable extends JTable
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private static final String COLUMN_NAMES[] = { "Junior ID", "Name" };
	
	private DefaultTableModel model;
	
	public JuniorTable()
	{
		super(new DefaultTableModel(JuniorTable.COLUMN_NAMES, 1));
		
		model = (DefaultTableModel)getModel();
		
		setFillsViewportHeight(true);
		setShowGrid(true);
		setEnabled(false);
		setVisible(true);
		// Populate with information from the database
		parseDB();
	}
	
	public void parseDB()
	{
		clear();
		
		DB db = DB.getInstance();
		try
		{
			PreparedStatement stmt = db.getConnection().prepareStatement(
					"SELECT * FROM Juniors");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next())
			{
				Object data[] = new Object[2];
				data[0] = new Integer(rs.getInt("jid"));
				data[1] = rs.getString("name");
				model.addRow(data);
			}
			
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			System.err.println("error during db transaction: " + e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void clear()
	{
		// Until there are no rows left:
		while (model.getRowCount() > 0)
		{
			// Remove the first row
			model.removeRow(0);
		}
	}
}
