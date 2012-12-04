package joeybic.jrpatrol.sponsors;

import joeybic.jrpatrol.ui.*;
import joeybic.jrpatrol.ui.DBDisplayTable.ColumnType;

public class SponsorPanel extends BaseDBDisplayPanel
{
	private static final String QUERY =
		"SELECT sid, name FROM Sponsors ORDER BY sid";
	
	private static final String COLUMN_LABELS[] =
	{
		"Sponsor ID",
		"Name"
	};
	
	private static final String COLUMN_NAMES[] =
	{
		"sid",
		"name"
	};
	
	private static final ColumnType COLUMN_TYPES[] =
	{
		ColumnType.INT,
		ColumnType.STRING
	};
	
	public SponsorPanel()
	{
		super(new SponsorEditMenu(),
			  QUERY,
			  COLUMN_LABELS,
			  COLUMN_NAMES,
			  COLUMN_TYPES);
	}
}
