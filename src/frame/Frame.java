package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import engine.Engine;
import util.FileManager;


public class Frame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5503738786153724100L;
	
	private JTextField _mailPath;
	private JTextField _mail;
	private JTextField _pass;
	private JTextField _from;
	private JTextField _subject;
	private JTextArea _text;
	private static JTextArea _logArea;
	private static JProgressBar _progressBar1;
	private static JProgressBar _progressBar2;
	private static JProgressBar _progressBar3;

	public void createGUI()
	{
		setTitle("Messanger beta (by Dallar)");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(FileManager.getInstance().getIcon());
		setSize(420, 640);
	    setLocationRelativeTo(null);
	    createMainMunu();
	    createTopPanel();
	    createMiddlePanel();
	    createLowPanel();
	    setVisible(true);
	}
	
	private void createMainMunu()
	{
		Font font = new Font("Verdana", Font.PLAIN, 11);
        
        JMenuBar menuBar = new JMenuBar();
        
        // ==================== Настройки ====================
        JMenu settingsMenu = new JMenu("Файл");
        
        JMenuItem age = new JMenuItem("Налаштування");
        age.setFont(font);
        age.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				SettingsFrame.getInstance().setVisible(true);;
			}
        });
        
        JMenuItem exit = new JMenuItem("Вихід");
        exit.setFont(font);
        exit.addActionListener(new ActionListener() 
        {           
            public void actionPerformed(ActionEvent e) 
            {
                System.exit(0);
            }           
        });
        
        settingsMenu.add(age);
        settingsMenu.addSeparator();
        settingsMenu.add(exit);
        
        // ==================== Настройки ====================
        JMenu helpMenu = new JMenu("Справка");
        
        JMenu reference = new JMenu("Програма");
        
        JMenu update = new JMenu("Оновлення");
        
        helpMenu.add(reference);
        helpMenu.add(update);
         
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);
                 
        setJMenuBar(menuBar);
	}
	
	public void createTopPanel()
	{
		JPanel topPanel = new JPanel();
		
		topPanel.setPreferredSize(new Dimension(getWidth(), ((int) (getHeight() / 4.8))));
		topPanel.setBackground(new Color(0x1E90FF));
		
		JLabel topLabel = new JLabel("Виберіть файл з @mail-адресами");
		
		_mailPath = new JTextField();
		_mailPath.setPreferredSize(new Dimension(((int) (getWidth() * 0.7)), ((int) (getHeight() * 0.04))));
		
		JButton search = new JButton("Огляд");
		search.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				UIManager.put("FileChooser.lookInLabelText", "Дивитись в:");
				UIManager.put("FileChooser.upFolderToolTipText", "Вище");
				UIManager.put("FileChooser.homeFolderToolTipText", "Робочий стіл");
				UIManager.put("FileChooser.newFolderToolTipText", "Створити папку");
				UIManager.put("FileChooser.listViewButtonToolTipText", "Список");
				UIManager.put("FileChooser.detailsViewButtonToolTipText", "Детально");
				UIManager.put("FileChooser.fileNameLabelText", "Назва файлу:");
				UIManager.put("FileChooser.filesOfTypeLabelText", "Тип файлів:");
				UIManager.put("FileChooser.acceptAllFileFilterText", "Всі файли");
				UIManager.put("FileChooser.directoryOpenButtonText", "ОК");
				UIManager.put("FileChooser.cancelButtonText", "Відміна");
				UIManager.put("FileChooser.filesLabelText", "Тип файлів:");
				UIManager.put("FileChooser.acceptAllFileFilterText", "Всі файли");
				UIManager.put("FileChooser.directoryOpenButtonText", "ОК");
				UIManager.put("FileChooser.cancelButtonText", "Відміна");
				
				JFileChooser fileopen = new JFileChooser();             
                
				int ret = fileopen.showDialog(null, "Вибрати файл");                
                
                if (ret == JFileChooser.APPROVE_OPTION)
                {
                    File file = fileopen.getSelectedFile();
                    _mailPath.setText(file.getAbsolutePath());
                }
			}
		});
		
		JPanel mailPanel = new JPanel();
		
		JLabel mailLabel = new JLabel("Логін:");
		_mail = new JTextField();
		_mail.setPreferredSize(new Dimension(((int) (getWidth() * 0.765)), ((int) (getHeight() * 0.035))));
		
		mailPanel.add(mailLabel);
		mailPanel.add(_mail);
		
		JPanel passPanel = new JPanel();
		
		JLabel passLabel = new JLabel("Пароль:");
		_pass = new JTextField();
		_pass.setPreferredSize(new Dimension(((int) (getWidth() * 0.73)), ((int) (getHeight() * 0.035))));
		
		passPanel.add(passLabel);
		passPanel.add(_pass);
		
		topPanel.add(topLabel);
		topPanel.add(_mailPath);
		topPanel.add(search);
		topPanel.add(mailPanel);
		topPanel.add(passPanel);
		
		add(topPanel, BorderLayout.NORTH);
	}
	
	public void createMiddlePanel()
	{
		JPanel middlePanel = new JPanel();
		
		JPanel topMidPanel = new JPanel();
		topMidPanel.setBackground(new Color(0x9C9C9C));
		topMidPanel.setPreferredSize(new Dimension(new Dimension(((int) (getWidth() * 0.95)), getHeight() / 3)));
		
		JLabel fromLabel = new JLabel("Від кого:");
		_from = new JTextField();
		_from.setPreferredSize(new Dimension(((int) (getWidth() * 0.73)), ((int) (getHeight() * 0.035))));
		
		JLabel subjectLabel = new JLabel("      Тема:");
		_subject = new JTextField();
		_subject.setPreferredSize(new Dimension(((int) (getWidth() * 0.73)), ((int) (getHeight() * 0.035))));
		
		_text = new JTextArea();
		
		JScrollPane textScroll = new JScrollPane(_text);
		textScroll.setPreferredSize(new Dimension(((int) (getWidth() * 0.95)), ((int) (getHeight() / 4.15))));
		
		topMidPanel.add(fromLabel);
		topMidPanel.add(_from);
		topMidPanel.add(subjectLabel);
		topMidPanel.add(_subject);
		topMidPanel.add(textScroll);
		
		JLabel logLabel = new JLabel("Логи:");
		
		_logArea = new JTextArea();
		_logArea.setEditable(false);
		_logArea.setBackground(new Color(0xCFCFCF));
		
		JScrollPane scrollPane = new JScrollPane(_logArea);
		scrollPane.setAutoscrolls(true);
		scrollPane.setPreferredSize(new Dimension(((int) (getWidth() * 0.95)), getHeight() / 8));
		
		middlePanel.add(topMidPanel);
		middlePanel.add(logLabel);
		middlePanel.add(scrollPane);
		
		add(middlePanel, BorderLayout.CENTER);
	}
	
	public void createLowPanel()
	{
		JPanel lowPanel = new JPanel();
		
		lowPanel.setPreferredSize(new Dimension(getWidth(), ((int) (getHeight() / 5.5))));
		lowPanel.setBackground(new Color(0xEEDD82));
		
		Dimension buttonSize = new Dimension(150, 26);
		
		JButton start = new JButton("Почати розсилку");
		start.setPreferredSize(buttonSize);
		start.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setProgress1(0);
				setProgress2(0);
				setProgress3(0);
				
				_logArea.setText("");
				
				if (_mail.getText().length() > 0 && _pass.getText().length() > 0)
					Engine.getInstance().startSending(_mail.getText(), _pass.getText(), FileManager.getInstance().parseMailList(new File(_mailPath.getText())), _from.getText(), _subject.getText(), _text.getText());
				
				else
					JOptionPane.showMessageDialog(null, "Заповнть обов`язкові поля!", "Помилка!", JOptionPane.WARNING_MESSAGE);
			}	
		});
		
		JButton stop = new JButton("Зупинити розсилку");
		stop.setPreferredSize(buttonSize);
		stop.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Engine.getInstance().stopSending();
			}	
		});
		
		_progressBar1 = new JProgressBar();
		_progressBar1.setPreferredSize(new Dimension(((int) (getWidth() * 0.96)), ((int) (getHeight() * 0.035))));
		_progressBar1.setStringPainted(true);
		_progressBar1.setMinimum(0);
		_progressBar1.setMaximum(100);
		_progressBar1.setValue(0);
		
		_progressBar2 = new JProgressBar();
		_progressBar2.setPreferredSize(new Dimension(((int) (getWidth() * 0.96)), ((int) (getHeight() * 0.035))));
		_progressBar2.setStringPainted(true);
		_progressBar2.setMinimum(0);
		_progressBar2.setMaximum(100);
		_progressBar2.setValue(0);
		
		_progressBar3 = new JProgressBar();
		_progressBar3.setPreferredSize(new Dimension(((int) (getWidth() * 0.96)), ((int) (getHeight() * 0.035))));
		_progressBar3.setStringPainted(true);
		_progressBar3.setMinimum(0);
		_progressBar3.setMaximum(100);
		_progressBar3.setValue(0);
		
		lowPanel.add(start);
		lowPanel.add(stop);
		lowPanel.add(_progressBar1);
		lowPanel.add(_progressBar2);
		lowPanel.add(_progressBar3);
		
		add(lowPanel, BorderLayout.SOUTH);
	}
	public static void logLine(String text)
	{
		_logArea.append(text + "\n");
		
		_logArea.setCaretPosition(_logArea.getDocument().getLength());
	}
	
	public static void log(String text)
	{
		_logArea.append(text);
	}
	
	public static void setProgress1(int value)
	{
		if (value > _progressBar1.getMaximum())
			value = _progressBar1.getMaximum();
		
		_progressBar1.setValue(value);
	}
	
	public static void setProgress2(int value)
	{
		if (value > _progressBar2.getMaximum())
			value = _progressBar2.getMaximum();
		
		_progressBar2.setValue(value);
	}
	
	public static void setProgress3(int value)
	{
		if (value > _progressBar3.getMaximum())
			value = _progressBar3.getMaximum();
		
		_progressBar3.setValue(value);
	}
}
