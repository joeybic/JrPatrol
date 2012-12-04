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
	
	private static final String SELECT_JUNIORS =
			"SELECT * FROM Juniors ORDER BY jid";
	
	private DefaultTableModel model = new DefaultTableModel(COLUMN_NAMES, 1)
	{
		/**
		 * IDK what this is.
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Make all the cells not editable
		 */
		@Override
		public boolean isCellEditable(int r, int c)
		{
			return false;
		}
	};
	
	public JuniorTable()
	{
		super();
		setModel(model);
		
		// Make it big!
		setFillsViewportHeight(true);
		
		// Draw the grid lines
		setShowGrid(true);
		
		// Allow row selection
		setRowSelectionAllowed(true);
		
		// Populate with information from the database
		parseDB();
		
		// Show!
		setVisible(true);
	}
	
	public void parseDB()
	{
		clear();
		
		DB db = DB.getInstance();
		
		if (! db.getIsConnected())
		{
			return;
		}
		
		try
		{
			PreparedStatement stmt = db.getConnection().prepareStatement(
					SELECT_JUNIORS);
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
