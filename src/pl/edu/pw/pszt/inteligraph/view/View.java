package pl.edu.pw.pszt.inteligraph.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

/**
 * Główna klasa tworząca graficzny interfejs użytkownika.
 * 
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

    /**
     * Tworzy elementy wyświetlanego okna.
     * 
     * @param blockingQueue
     */
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

	Graph g = this.getGraph();
	// Layout<V, E>, VisualizationComponent<V,E>
	Layout<Integer, String> layout = new CircleLayout(g);
	layout.setSize(new Dimension(300, 300));
	VisualizationViewer<Integer, String> vv = new VisualizationViewer<Integer, String>(
		layout);
	vv.setPreferredSize(new Dimension(350, 350));
	// Show vertex and edge labels
	vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
	vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
	// Create a graph mouse and add it to the visualization component
	DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
	gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
	vv.setGraphMouse(gm);

	f.getContentPane().add(vv, BorderLayout.CENTER);

    }

    public Graph getGraph() {
	Graph<Integer, String> g = new SparseGraph<Integer, String>();

	g.addVertex((Integer) 1);
	g.addVertex((Integer) 2);
	g.addVertex((Integer) 3);
	g.addEdge("Edge-A", 1, 2);
	g.addEdge("Edge-B", 2, 3);
	g.addEdge("Edge-C", 3, 1);
	return g;
    }

    /**
     * Listlener dla opcji "Quit".
     */
    public class ListenMenuQuit implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    System.exit(0);
	}
    }

    /**
     * Listlener dla otwierania pliku.
     */
    public class ListenMenuOpen implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    System.out.println("Otwieramy pliczek!");
	    // TODO otwieranie plików tutaj zrobić

	}
    }

    /**
     * Listlener obsługujący zamykanie okna.
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

}
