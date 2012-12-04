package joeybic.jrpatrol.juniors;

import javax.swing.*;

import joeybic.jrpatrol.ui.BasePanel;

public class JuniorPanel extends BasePanel
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private JuniorTable table;
	
	public JuniorPanel()
	{
		super(new JuniorEditMenu());
		
		table = new JuniorTable();
		JScrollPane scroller = new JScrollPane(table);
		add(scroller);
		setVisible(true);
	}
	
	public void refresh()
	{
		table.parseDB();
	}
}
