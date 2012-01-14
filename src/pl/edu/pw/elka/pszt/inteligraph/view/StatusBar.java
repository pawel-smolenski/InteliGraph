package pl.edu.pw.elka.pszt.inteligraph.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pl.edu.pw.elka.pszt.inteligraph.Constans;
import pl.edu.pw.elka.pszt.inteligraph.events.EventsBlockingQueue;

public class StatusBar extends JPanel {
    
    private JLabel appStateLabel;
    private String appStateString;
    
    private JLabel evolutionStepsLabel;
    private JLabel graphVerticesLabel;
    private JLabel graphEdgesLabel;
    
    public StatusBar(EventsBlockingQueue blockingQueue) {
	super(new GridLayout(1, 4));
	setPreferredSize(new Dimension(Constans.WINDOW_WIDTH, Constans.BAR_HEIGHT));
	appStateLabel = new JLabel(appStateString);
	evolutionStepsLabel = new JLabel("Liczba kroków: 0");
	graphEdgesLabel = new JLabel();
	graphVerticesLabel = new JLabel();

	this.add(appStateLabel);
	this.add(evolutionStepsLabel);
	this.add(graphVerticesLabel);
	this.add(graphEdgesLabel);
    }
    
    public void setAppState(String string) {
	appStateLabel.setText(string);
	this.updateUI();
    }
    
    public void setEvolutionSteps(int steps) {
	evolutionStepsLabel.setText("Liczba kroków: " + steps);
	this.updateUI();
    }
    
    public void setGraphParams(int vertices, int edges) {
	graphVerticesLabel.setText("wierzchołki: " + vertices);
	graphEdgesLabel.setText("krawędzie: " + edges);
	this.updateUI();
    }
}
