package pl.edu.pw.elka.pszt.inteligraph.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import edu.uci.ics.jung.graph.Graph;

import pl.edu.pw.elka.pszt.inteligraph.Constans;
import pl.edu.pw.elka.pszt.inteligraph.events.Event;
import pl.edu.pw.elka.pszt.inteligraph.events.EventName;
import pl.edu.pw.elka.pszt.inteligraph.events.EventsBlockingQueue;
import pl.edu.pw.elka.pszt.inteligraph.model.VertexName;

/**
 * Główna klasa tworząca graficzny interfejs użytkownika.
 */
public class View {

    private JFrame f = new JFrame(Constans.APP_NAME);
    // Menu
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuItemQuit = new JMenuItem("Quit");
    private JMenuItem menuItemOpen = new JMenuItem("Open");
    private JMenu menuHelp = new JMenu("Help");
    private JMenuItem menuItemAbout = new JMenuItem("About");

    private GraphParametersPanel graphParametersPanel;
    private StatusBar statusBar;
    private GraphView graphView;

    private EventsBlockingQueue blockingQueue;

    private File fileGraph = null;

    /**
     * Tworzy elementy wyświetlanego okna.
     * 
     * @param blockingQueue
     *            kolejka zdarzeń
     */
    public View(EventsBlockingQueue blockingQueue) {

	this.blockingQueue = blockingQueue;

	f.setJMenuBar(menuBar);

	// menu File
	menuFile.add(menuItemOpen);
	menuFile.add(menuItemQuit);
	// menu About
	menuHelp.add(menuItemAbout);
	menuBar.add(menuFile);
	menuBar.add(menuHelp);

	statusBar = new StatusBar(blockingQueue);
	graphParametersPanel = new GraphParametersPanel(blockingQueue);

	f.getContentPane().setLayout(new BorderLayout());
	f.addWindowListener(new ListenCloseWdw());
	menuItemQuit.addActionListener(new ListenMenuQuit());
	menuItemOpen.addActionListener(new ListenMenuOpen());

	f.getContentPane().add(graphParametersPanel, BorderLayout.NORTH);
	f.getContentPane().add(statusBar, BorderLayout.SOUTH);

    }

    public void setGraphView(Graph<VertexName, String> g,
	    Map<VertexName, Point2D> m) {
	graphView = new GraphView(g, m);
	f.getContentPane().add(graphView.getVisualizationViewer(),
		BorderLayout.CENTER);
	graphView.refresh();
    }
    
    
    public GraphParametersPanel getGraphParametersPanel() {
	return graphParametersPanel;
    }
    
    public StatusBar getStatusBar() {
	return statusBar;
    }

    public int getMi(){
	return graphParametersPanel.getAlgorithmParams()[0];
    }
    
    public int getLambda(){
	return graphParametersPanel.getAlgorithmParams()[1];
    }
    
    public int getSteps(){
	return graphParametersPanel.getAlgorithmParams()[2];
    }
    
    /**
     * Listener dla opcji "Quit".
     */
    public class ListenMenuQuit implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    System.exit(0);
	}
    }
    
    /**
     * Listener dla otwierania pliku.
     */
    public class ListenMenuOpen implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    JFileChooser fd = new JFileChooser(".");

	    int returnVal = fd.showOpenDialog(null);
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
		// String fileName = fd.getCurrentDirectory().toString() + "/"+
		// fd.getSelectedFile().getName();
		// System.out.println(fileName);
		fileGraph = fd.getSelectedFile();
		blockingQueue.add(new Event(EventName.CHOOSE_FILE));
	    }
	}
    }

    /**
     * Listener obsługujący zamykanie okna.
     */
    public class ListenCloseWdw extends WindowAdapter {
	public void windowClosing(WindowEvent e) {
	    System.exit(0);
	}
    }

    /**
     * Wyświetla okno.
     */
    public void showWindow() {
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setSize(Constans.WINDOW_WIDTH, Constans.WINDOW_HEIGHT);

	f.setVisible(true);
    }

    public File getGraphFile() {
	return fileGraph;
    }
}
