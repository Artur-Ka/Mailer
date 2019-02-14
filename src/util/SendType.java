package util;

public enum SendType
{
	SMTP("smtp.gmail.com", "25"),
	SMTP_TLS("smtp.gmail.com", "587"),
	SMTP_SSL("smtp.gmail.com", "465");
	
	private String _host;
	private String _port;
	
	SendType(String host, String port)
	{
		_host = host;
		_port = port;
	}
	
	public String getDefaultHost()
	{
		return _host;
	}
	
	public String getDefaultPort()
	{
		return _port;
	}
}
