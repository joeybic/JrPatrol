package joeybic.jrpatrol.ui;

import javax.swing.*;

import joeybic.jrpatrol.EntryType;
import joeybic.jrpatrol.TypeParser;

public class TextFieldWrapper extends BaseFieldWrapper<JTextField>
{

	public TextFieldWrapper(JTextField field, EntryType type)
	{
		super(field, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object extractValue()
	{
		// TODO Auto-generated method stub
		return TypeParser.parseString(getFieldType(), field.getText());
	}

	@Override
	public void reset()
	{
		// TODO Auto-generated method stub
		field.setText((String)getDefaultValue());
	}

}
