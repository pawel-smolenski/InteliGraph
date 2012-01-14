package pl.edu.pw.elka.pszt.inteligraph.view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pl.edu.pw.elka.pszt.inteligraph.Constans;
import pl.edu.pw.elka.pszt.inteligraph.events.EventsBlockingQueue;

public class StatusBar extends JPanel {
    
    private JLabel appStateLabel;
    private String appStateString;
    
    private JLabel evolutionStepsLabel;
    
    public StatusBar(EventsBlockingQueue blockingQueue) {
	super(new FlowLayout());
	setPreferredSize(new Dimension(Constans.WINDOW_WIDTH, Constans.BAR_HEIGHT));
	appStateLabel = new JLabel(appStateString);
	evolutionStepsLabel = new JLabel("Liczba kroków: 0");
	
	this.add(appStateLabel);
	this.add(evolutionStepsLabel);
    }
    
    public void setAppState(String string) {
	appStateLabel.setText(string);
	this.updateUI();
    }
    
    public void setEvolutionSteps(int steps) {
	evolutionStepsLabel.setText("Liczba kroków: " + steps);
	this.updateUI();
    }
}
