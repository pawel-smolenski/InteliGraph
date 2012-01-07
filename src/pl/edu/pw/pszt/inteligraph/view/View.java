package pl.edu.pw.pszt.inteligraph.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import pl.edu.pw.pszt.inteligraph.Constans;
import pl.edu.pw.pszt.inteligraph.events.EventsBlockingQueue;

public class View {

	private JFrame f = new JFrame(Constans.APP_NAME);
	// Menu
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menuFile = new JMenu("File");
	private JMenuItem menuItemQuit = new JMenuItem("Quit");
	private JMenuItem menuItemOpen = new JMenuItem("Open");
	private JMenu menuHelp = new JMenu("Help");
	private JMenuItem menuItemAbout = new JMenuItem("About");

	public View(EventsBlockingQueue blockingQueue) {

		f.setJMenuBar(menuBar);

		// menu File
		menuFile.add(menuItemOpen);
		menuFile.add(menuItemQuit);
		// menu About
		menuHelp.add(menuItemAbout);
		menuBar.add(menuFile);
		menuBar.add(menuHelp);

		f.getContentPane().setLayout(new BorderLayout());
		f.addWindowListener(new ListenCloseWdw());
		menuItemQuit.addActionListener(new ListenMenuQuit());
		menuItemOpen.addActionListener(new ListenMenuOpen());
	}

	public class ListenMenuQuit implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	public class ListenMenuOpen implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Otwieramy pliczek!");
			// TODO otwieranie plików tutaj zrobić

		}
	}

	public class ListenCloseWdw extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}


	public void showWindow() {
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(Constans.WINDOW_WIDTH, Constans.WINDOW_HEIGHT);
		
		f.setVisible(true);
	}

}
