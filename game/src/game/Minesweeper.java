package game;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.io.*;


public class Minesweeper extends JFrame {
    private JLabel status,remainTime;
	public JMenuBar menubar;
	public JMenuItem Save;
	public JPanel timePanel;
	public Timer timer;
	int second=1001;
	//public boolean inGame=true;
	
	public JMenuBar MenuFrame() {
		menubar=new JMenuBar();
		setJMenuBar(menubar);
		menubar.add(createFileMenu());
		return menubar;
	}
	private JMenu createFileMenu() {
		JMenu menu=new JMenu("File");
		menu.add(New("New"));
		//menu.add(Open("Open"));
		//menu.add(Save("Save"));
		menu.add(Exit("Exit"));
		return menu;
	}
	private JMenuItem Exit(final String name) {
		JMenuItem item=new JMenuItem(name);
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				//dispose();
				System.exit(0);
			}
		}
		ActionListener listener =new MenuItemListener();
		item.addActionListener(listener);
		return item;
	}
	private JMenuItem Open(final String name) {
		JMenuItem item=new JMenuItem(name);
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {

				try {
					SaveData data=(SaveData)Resource.Load("save");
					second=data.second;
				}catch (Exception e) {
				}
			}
		}
		ActionListener listener =new MenuItemListener();
		item.addActionListener(listener);
		return item;
	}
	public JMenuItem Save(final String name) {
		JMenuItem item=new JMenuItem(name);
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				SaveData data= new SaveData();
				data.second=second;
				try {
					Resource.Save(data,"save");
				}catch (Exception e) {
					
				}
			}
		}
		ActionListener listener =new MenuItemListener();
		item.addActionListener(listener);
		return item;
	}
	private JMenuItem New(final String name) {
		JMenuItem item=new JMenuItem(name);
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				new Minesweeper();
			}
		}
		ActionListener listener =new MenuItemListener();
		item.addActionListener(listener);
		return item;
	}
 
	
	private JPanel createTimePanel() {
		timePanel=new JPanel();
		remainTime=new JLabel("Remain Time:1000");
		timePanel.add(remainTime);
		return timePanel;
	}

    public Minesweeper() {
    	status = new JLabel("");
    	MenuFrame(); 
    	createTimePanel();
    	add(timePanel,BorderLayout.NORTH);
        add(status, BorderLayout.SOUTH);
        add(new Board(status,remainTime));
        //add
        setResizable(false);
        pack();
        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
