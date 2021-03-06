package pl.edu.pw.elka.pszt.inteligraph.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import pl.edu.pw.elka.pszt.inteligraph.Constans;
import pl.edu.pw.elka.pszt.inteligraph.events.Event;
import pl.edu.pw.elka.pszt.inteligraph.events.EventName;
import pl.edu.pw.elka.pszt.inteligraph.events.EventsBlockingQueue;

/**
 * Górny panel służący do wprowadzania parametrów algorytmu oraz kontroli jego wykonywania.
 */
public class GraphParametersPanel extends JPanel {

    private TextField lambdaField;
    private TextField miField;
    private TextField stepsField;
    private JButton nStepButton;
    private JButton infStepButton;
    private JButton stopButton;
    private JLabel miLabel;
    private JLabel lambdaLabel;
    private JLabel stepsLabel;


    private EventsBlockingQueue blockingQueue;

    private final int textFieldsWidth = 10;

    private double edgeWeight = 500.0;
    private int edgeWeightRange = 1000;
    private int lambda = 30;
    private int mi = 40;
    private int steps = 1;

    public GraphParametersPanel(EventsBlockingQueue blockingQueue) {
	super(new FlowLayout());
	this.blockingQueue = blockingQueue;
	lambdaField = new TextField(String.valueOf(lambda) ,textFieldsWidth);
	miField = new TextField(String.valueOf(mi), textFieldsWidth);
	stepsField = new TextField(String.valueOf(steps), textFieldsWidth);
	lambdaLabel = new JLabel(" λ:");
	miLabel = new JLabel(" μ:");
	stepsLabel = new JLabel(" n:");
	nStepButton = new JButton("n-krok");
	infStepButton = new JButton("∞-krok");
	stopButton = new JButton("Stop");
	stopButton.setEnabled(false);
	nStepButton.setEnabled(false);
	infStepButton.setEnabled(false);

	nStepButton.addActionListener(new ListenNStepButton());
	infStepButton.addActionListener(new ListenInfStepButton());
	stopButton.addActionListener(new ListenStopButton());


	
	setPreferredSize(new Dimension(Constans.WINDOW_WIDTH,
		Constans.PANEL_HEIGHT));
	this.add(lambdaLabel);
	this.add(lambdaField);	
	this.add(miLabel);
	this.add(miField);
	this.add(stepsLabel);
	this.add(stepsField);
	this.add(nStepButton);
	this.add(infStepButton);
	this.add(stopButton);
    }

    /**
     * Listener dla Buttona n-step.
     */
    public class ListenNStepButton implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    try {
		lambda = Integer.parseInt(lambdaField.getText());
		mi = Integer.parseInt(miField.getText());
		steps = Integer.parseInt(stepsField.getText());
		if (lambda >= 2 && mi >= 1 && steps >= 1) {
		    blockingQueue.add(new Event(EventName.DRAW_GRAPH_N));
		} else {
		    throw new NumberFormatException();
		}
	    } catch (NumberFormatException e1) {
		blockingQueue.add(new Event(EventName.WRONG_GRAPH_PARAMS));
	    } 
	}
    }

    /**
     * Listener dla Buttona inf-step.
     */
    public class ListenInfStepButton implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    try {
		lambda = Integer.parseInt(lambdaField.getText());
		mi = Integer.parseInt(miField.getText());
		steps = Integer.parseInt(stepsField.getText());
		if (lambda >= 2 && mi >= 1 && steps >= 1) {
		    blockingQueue.add(new Event(EventName.DRAW_GRAPH_INF));
		} else {
		    throw new NumberFormatException();
		}	
	    } catch (NumberFormatException e1) {
		blockingQueue.add(new Event(EventName.WRONG_GRAPH_PARAMS));
	    }

	}
    }

    /**
     * Listener dla Buttona stop.
     */
    public class ListenStopButton implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    blockingQueue.add(new Event(EventName.DRAW_GRAPH_STOP));
	}
    }

    
    /**
     * Zwraca parametry dla algorytmu.
     * @return tablica parametrów 
     */
    public int[] getAlgorithmParams() {
	int[] ret = {mi, lambda, steps};
	return ret;
    }

    public double getEdgeWeight() {
	return edgeWeight;
    }

    /**
     * Włącza i wyłącza stopButton.
     * @param value TRUE jak włączony.
     */
    public void setStopButtonActive(boolean value) {
	stopButton.setEnabled(value);
    }
    
    /**
     * Włącza i wyłącza buttony do liczenia
     * @param value TRUE jak włączony.
     */
    public void setStepsButtonActive(boolean value) {
	nStepButton.setEnabled(value);
	infStepButton.setEnabled(value);
    }

}
