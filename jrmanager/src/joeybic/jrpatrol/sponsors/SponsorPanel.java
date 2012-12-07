package joeybic.jrpatrol.sponsors;

import joeybic.jrpatrol.ui.*;
import joeybic.jrpatrol.*;

public class SponsorPanel extends BaseDBDisplayPanel
{
	/**
	 * idk
	 */
	private static final long serialVersionUID = 1L;

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
	
	private static final EntryType COLUMN_TYPES[] =
	{
		EntryType.INTEGER,
		EntryType.STRING
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
