package pl.edu.pw.elka.pszt.inteligraph.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pl.edu.pw.elka.pszt.inteligraph.Constans;

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
    
    private final int textFieldsWidth = 10;
    
    
    
    public GraphParametersPanel() {
	super(new FlowLayout());
	lambdaField = new TextField(textFieldsWidth);
	miField = new TextField(textFieldsWidth);
	stepsField = new TextField(textFieldsWidth);
	lambdaLabel= new JLabel(" λ:");
	miLabel = new JLabel(" μ:");
	stepsLabel = new JLabel(" kroki:");
	nStepButton = new JButton("n-krok");
	infStepButton = new JButton("inf-krok");
	stopButton = new JButton("Stop");
	stopButton.setEnabled(false);
	setPreferredSize(new Dimension(Constans.WINDOW_WIDTH, (int)(Constans.WINDOW_HEIGHT*(0.08))));
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
    
    
}
