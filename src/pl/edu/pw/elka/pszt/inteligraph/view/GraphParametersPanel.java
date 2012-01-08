package pl.edu.pw.elka.pszt.inteligraph.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pl.edu.pw.elka.pszt.inteligraph.Constans;
import pl.edu.pw.elka.pszt.inteligraph.events.Event;
import pl.edu.pw.elka.pszt.inteligraph.events.EventName;
import pl.edu.pw.elka.pszt.inteligraph.events.EventsBlockingQueue;

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

    private int lambda = 0;
    private int mi = 0 ;
    private int steps = 0;

    public GraphParametersPanel(EventsBlockingQueue blockingQueue) {
	super(new FlowLayout());
	this.blockingQueue = blockingQueue;
	lambdaField = new TextField(String.valueOf(lambda) ,textFieldsWidth);
	miField = new TextField(String.valueOf(mi), textFieldsWidth);
	stepsField = new TextField(String.valueOf(steps), textFieldsWidth);
	lambdaLabel = new JLabel(" λ:");
	miLabel = new JLabel(" μ:");
	stepsLabel = new JLabel(" kroki:");
	nStepButton = new JButton("n-krok");
	infStepButton = new JButton("inf-krok");
	stopButton = new JButton("Stop");
	stopButton.setEnabled(false);

	nStepButton.addActionListener(new ListenNStepButton());
	infStepButton.addActionListener(new ListenInfStepButton());
	stopButton.addActionListener(new ListenStopButton());

	setPreferredSize(new Dimension(Constans.WINDOW_WIDTH,
		(int) (Constans.WINDOW_HEIGHT * (0.08))));
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
		blockingQueue.add(new Event(EventName.DRAW_GRAPH_INF));
	    } catch (NumberFormatException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
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
		blockingQueue.add(new Event(EventName.DRAW_GRAPH_INF));
	    } catch (NumberFormatException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
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

}
