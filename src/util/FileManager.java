package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import frame.Frame;


public class FileManager
{
	private BufferedImage _icon;
	private Map<Integer, String> _mailList;
		
	public FileManager()
	{
		try
		{
			_icon = ImageIO.read(getClass().getResource("/data/image/icon.jpg"));
		} 
		catch (IOException e)
		{
			System.out.println(e);
		}
		
		_mailList = new HashMap<Integer, String>();
	}
	
	public BufferedImage getIcon()
	{
		return _icon;
	}
	
	public Map<Integer, String> parseMailList(File mailList)
	{
		Scanner sc = null;
		
		try
		{
			sc = new Scanner(mailList);
		}
		catch (Exception e)
		{
			
		}
				
		int i = 0;
		while (sc.hasNextLine())
		{
			_mailList.put(i, sc.nextLine());
			i++;
		}
		
		sc.close();
		
		Frame.logLine("Завантажено адрес:                         " + _mailList.size());
		
		return _mailList;
	}
	
	public final static FileManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final FileManager _instance = new FileManager();
	}
}
