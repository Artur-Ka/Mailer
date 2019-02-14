package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import engine.Engine;
import util.AuthType;
import util.FileManager;
import util.SendType;

@SuppressWarnings("serial")
public class SettingsFrame extends JFrame
{
	JTextField _hostField;
	JTextField _portField;
	JTextField _sleepField;
	JComboBox<SendType> _typeBox;
	JComboBox<AuthType> _authBox;
	
	
	public SettingsFrame()
	{
		setTitle("Налаштування");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(FileManager.getInstance().getIcon());
		setSize(290, 300);
	    setLocationRelativeTo(null);
	    
	    JPanel mainPanel = new JPanel();
	    mainPanel.setLayout(new GridLayout(6, 2, 10, 10));
	    
	    JLabel hostLabel = new JLabel("Сервер:");
	    _hostField = new JTextField(Engine.getInstance().getHost());
	    
	    JLabel portLabel = new JLabel("Порт:");
	    _portField = new JTextField(Engine.getInstance().getPort());
	    
	    JLabel sleepLabel = new JLabel("Хвилин чекати:");
	    _sleepField = new JTextField(String.valueOf(Engine.getInstance().getSleepTime() / 60000));
	    
	    JLabel typeLabel = new JLabel("Тип обл. запису:");
	    _typeBox = new JComboBox<SendType>(SendType.values());
	    _typeBox.setSelectedItem(Engine.getInstance().getSendType());
	    _typeBox.addActionListener(new ActionListener()
	    {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				SendType type = ((SendType)_typeBox.getSelectedItem());
				
				_hostField.setText(type.getDefaultHost());
				_portField.setText(type.getDefaultPort());
			}
	    });
	    
	    JLabel authLabel = new JLabel("Авторизація:");
	    _authBox = new JComboBox<AuthType>(AuthType.values());
	    _authBox.setSelectedItem(Engine.getInstance().getAuth());
	    
	    mainPanel.add(typeLabel);
	    mainPanel.add(_typeBox);
	    mainPanel.add(hostLabel);
	    mainPanel.add(_hostField);
	    mainPanel.add(portLabel);
	    mainPanel.add(_portField);
	    mainPanel.add(authLabel);
	    mainPanel.add(_authBox);
	    mainPanel.add(sleepLabel);
	    mainPanel.add(_sleepField);
	    
	    JPanel buttonPanel = new JPanel();
	    Dimension buttonSize = new Dimension(96, 26);
	    
	    JButton ok = new JButton("ОК");
	    ok.setPreferredSize(buttonSize);
	    ok.addActionListener(new ActionListener()
	    {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Engine.getInstance().setSendType((SendType)_typeBox.getSelectedItem());
				Engine.getInstance().setHost(_hostField.getText());
				Engine.getInstance().setPort(_portField.getText());
				Engine.getInstance().setAuth(((AuthType)_authBox.getSelectedItem()));
				Engine.getInstance().setSleepTime(Integer.parseInt(_sleepField.getText()) * 60000);
				exit();
			}	
	    });
	    
	    JButton cancel = new JButton("Відміна");
	    cancel.setPreferredSize(buttonSize);
	    cancel.addActionListener(new ActionListener()
	    {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
	    });
	    
	    buttonPanel.add(ok);
	    buttonPanel.add(cancel);
	    
	    add(mainPanel, BorderLayout.NORTH);
	    add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public void exit()
	{
		super.dispose();
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		
		_hostField.setText(Engine.getInstance().getHost());
		_portField.setText(Engine.getInstance().getPort());
		_sleepField.setText(String.valueOf(Engine.getInstance().getSleepTime() / 60000));
		_typeBox.setSelectedItem(Engine.getInstance().getSendType());
		_authBox.setSelectedItem(Engine.getInstance().getAuth());
	}
	
	public final static SettingsFrame getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final SettingsFrame _instance = new SettingsFrame();
	}
}
