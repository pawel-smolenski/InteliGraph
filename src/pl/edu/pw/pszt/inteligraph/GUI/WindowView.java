package pl.edu.pw.pszt.inteligraph.GUI;

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


/**
 * Główna klasa tworząca graficzny interfejs użytkownika.
 * 
 */
public class WindowView {
    private JFrame f = new JFrame(Constans.APP_NAME);
    // Menu
    private JMenuBar mb = new JMenuBar(); 
    private JMenu mnuFile = new JMenu("File"); 
    private JMenuItem mnuItemQuit = new JMenuItem("Quit");
    private JMenu mnuHelp = new JMenu("Help");
    private JMenuItem mnuItemAbout = new JMenuItem("About");

    /** 
     * Konstruktor GUI
     */
    public WindowView() {
	
	f.setJMenuBar(mb);
	

	mnuFile.add(mnuItemQuit); 
	mnuHelp.add(mnuItemAbout); 
	mb.add(mnuFile); 
	mb.add(mnuHelp);

	f.getContentPane().setLayout(new BorderLayout());
	f.addWindowListener(new ListenCloseWdw());
	mnuItemQuit.addActionListener(new ListenMenuQuit());
    }

    public class ListenMenuQuit implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    System.exit(0);
	}
    }

    public class ListenCloseWdw extends WindowAdapter {
	public void windowClosing(WindowEvent e) {
	    System.exit(0);
	}
    }

    public void show() {
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setSize(Constans.WINDOW_WIDTH, Constans.WINDOW_HEIGHT);
	
	f.setVisible(true);
    }
}
