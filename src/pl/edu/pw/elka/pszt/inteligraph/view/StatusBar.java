package pl.edu.pw.elka.pszt.inteligraph.view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pl.edu.pw.elka.pszt.inteligraph.Constans;
import pl.edu.pw.elka.pszt.inteligraph.events.EventsBlockingQueue;

/**
 * Dolny statusBar informujący użytkownika o danych grafu, stanie aplikacji, oraz liczby wykonanych iteracji algorytmu.
 */
public class StatusBar extends JPanel {
    
    private JLabel appStateLabel;
    private String appStateString;
    
    private JLabel evolutionStepsLabel;
    private JLabel graphVerticesLabel;
    private JLabel graphEdgesLabel;
    private JLabel graphQualityLabel;
    
    public StatusBar(EventsBlockingQueue blockingQueue) {
	super(new GridLayout(1, 6));
	setPreferredSize(new Dimension(Constans.WINDOW_WIDTH, Constans.BAR_HEIGHT));
	appStateLabel = new JLabel(appStateString);
	evolutionStepsLabel = new JLabel("Liczba kroków: 0");
	graphEdgesLabel = new JLabel();
	graphVerticesLabel = new JLabel();
	graphQualityLabel = new JLabel();

	this.add(appStateLabel);
	this.add(evolutionStepsLabel);
	this.add(graphVerticesLabel);
	this.add(graphEdgesLabel);
	this.add(graphQualityLabel);
    }
    
    public void setAppState(String string) {
	appStateLabel.setText(string);
	this.updateUI();
    }
    
    public void setEvolutionResults(int steps, int quality, double edgeWeight) {
	evolutionStepsLabel.setText("Liczba kroków: " + steps);
	graphQualityLabel.setText("Jakość: " + quality);
	this.updateUI();
    }
	
    
    public void setGraphParams(int vertices, int edges) {
	graphVerticesLabel.setText("wierzchołki: " + vertices);
	graphEdgesLabel.setText("krawędzie: " + edges);
	this.updateUI();
    }
}
