package util;

public enum AuthType
{
	Так("true"),
	Ні("false");
	
	String _value;
	
	AuthType(String value)
	{
		_value = value;
	}
	
	public String getValue()
	{
		return _value;
	}
}
