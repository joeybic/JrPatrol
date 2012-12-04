package joeybic.jrpatrol.juniors;

import javax.swing.*;

import joeybic.jrpatrol.ui.*;
import joeybic.jrpatrol.ui.DBDisplayTable.ColumnType;

public class JuniorPanel extends BaseDBDisplayPanel
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * The query to pose
	 */
	private static final String QUERY = 
		"SELECT jid, name FROM Juniors";
	
	private static final String COLUMN_LABELS[] =
	{
		"Junior ID",
		"Name"
	};
	
	private static final String COLUMN_NAMES[] =
	{
		"jid",
		"name"
	};
	
	private static final ColumnType COLUMN_TYPES[] =
	{
		ColumnType.INT,
		ColumnType.STRING
	};
	
	public JuniorPanel()
	{
		super(new JuniorEditMenu(),
			  QUERY,
			  COLUMN_LABELS,
			  COLUMN_NAMES,
			  COLUMN_TYPES);
	}
}
