package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import util.AuthType;
import util.SendType;
import frame.Frame;


public class Engine
{
	private String _mail;
	private String _pass;
	private String _from;
	private String _subject;
	private String _text;
	
	private List<String> _firstList;
	private List<String> _secondList;
	private List<String> _thirdList;
	
	private Properties _property;
	private Thread _first;
	private Thread _second;
	private Thread _third;
	private boolean _allowRunning = false;
	
	private SendType _type = SendType.SMTP;
	private String _host = _type.getDefaultHost();
	private String _port = _type.getDefaultPort();
	private AuthType _auth = AuthType.Так;
	private long _sleepTime = 300000L; // в миллисекундах
	
	
	public SendType getSendType()
	{
		return _type;
	}
	
	public String getHost()
	{
		return _host;
	}
	
	public String getPort()
	{
		return _port;
	}
	
	public AuthType getAuth()
	{
		return _auth;
	}
	
	public long getSleepTime()
	{
		return _sleepTime;
	}
	
	public void setSendType(SendType type)
	{
		_type = type;
	}
	
	public void setHost(String host)
	{
		_host = host;
	}
	
	public void setPort(String port)
	{
		_port = port;
	}
	
	public void setAuth(AuthType auth)
	{
		_auth = auth;
	}
	
	public void setSleepTime(long sleepTime)
	{
		_sleepTime = sleepTime;
	}
	
	public void startSending(String mail, String pass, Map<Integer, String> mailList, String from, String subject, String text)
	{
		_mail = mail;
		_pass = pass;
		_from = from;
		_subject = subject;
		_text = text;
		
		_firstList = new ArrayList<String>();
		_secondList = new ArrayList<String>();
		_thirdList = new ArrayList<String>();
		
		for (int i = 0; i < mailList.size(); i++)
		{
			if (i % 3 == 2)
				_thirdList.add(mailList.get(i));
			else if (i % 3 == 1)
				_secondList.add(mailList.get(i));
			else
				_firstList.add(mailList.get(i));
		}
		
		_first = new Thread(new FirstThread());
		_second = new Thread(new SecondThread());
		_third = new Thread(new ThirdThread());
		
		Frame.logLine("Спроба підключення...");
		
		_property = new Properties();
		
		_property.setProperty("mail.smtp.host", getHost());
		_property.setProperty("mail.smtp.port", getPort());
		_property.setProperty("mail.smtp.auth", getAuth().getValue());
		
		switch (getSendType())
		{
			case SMTP:
				break;
			case SMTP_TLS:
				_property.setProperty("mail.smtp.starttls.enable", "true");
				break;
			case SMTP_SSL:
				_property.setProperty("mail.smtp.socketFactory.port", getPort());
				_property.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				break;
		}
		
		_allowRunning = true;
		
		_first.start();
		_second.start();
		_third.start();
	}
	
	public void stopSending()
	{
		_allowRunning = false;
	}
	
	private Message getMessage()
	{
		Session session = null;
		Message message = null;
		
		try
		{
			session = Session.getInstance(_property, new javax.mail.Authenticator()
			{
				protected PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(_mail, _pass);
				}
			});
			
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(_from));
			message.setSubject(_subject);
			message.setText(_text);
		}
		catch (Exception e)
		{
			Frame.logLine("Помилка при підключенні: " + e);
		}
		
		return message;
	}
	
	private boolean sendMessage(String recipient, Message message)
	{
		try
		{
			message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
			Transport.send(message);
		}
		catch (Exception e)
		{
			Frame.logLine("Помилка при відправленні листа: " + e);
			return false;
		}
		
		return true;
	}
	
	public final static Engine getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final Engine _instance = new Engine();
	}
	
	private class FirstThread implements Runnable
	{
		@Override
		public void run()
		{
			Message message = getMessage();
			
			int i = 0;
			for (String m : _firstList)
			{
				if (!_allowRunning)
					break;
				
				if (!sendMessage(m, message))
				{
					try
					{
						Thread.sleep(getSleepTime());
						Frame.logLine("Потік 1: Заснув на " + (getSleepTime() / 60000) + " хвилин...");
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					i++;
					Frame.setProgress1(i * 100 / _firstList.size());
				}
			}
			
			Frame.logLine("Потік 1: Завершив роботу!");
		}
	}
	
	private class SecondThread implements Runnable
	{
		@Override
		public void run()
		{
			Message message = getMessage();
			
			int i = 0;
			for (String m : _secondList)
			{	
				if (!_allowRunning)
					break;
				
				if (!sendMessage(m, message))
				{
					try
					{
						Thread.sleep(getSleepTime());
						Frame.logLine("Потік 2: Заснув на " + (getSleepTime() / 60000) + " хвилин...");
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					i++;
					Frame.setProgress2(i * 100 / _firstList.size());
				}
			}
			
			Frame.logLine("Потік 2: Завершив роботу!");
		}
	}
	
	private class ThirdThread implements Runnable
	{
		@Override
		public void run()
		{
			Message message = getMessage();
			
			int i = 0;
			for (String m : _thirdList)
			{
				if (!_allowRunning)
					break;
				
				if (!sendMessage(m, message))
				{
					try
					{
						Thread.sleep(getSleepTime());
						Frame.logLine("Потік 3: Заснув на " + (getSleepTime() / 60000) + " хвилин...");
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					i++;
					Frame.setProgress3(i * 100 / _firstList.size());
				}
			}
			
			Frame.logLine("Потік 3: Завершив роботу!");
		}
	}
}
