package pl.edu.pw.pszt.inteligraph;

import pl.edu.pw.pszt.inteligraph.GUI.WindowView;


public class InteliGraph {

    private static WindowView windowView; 
    
    /**
     * @param args
     */
    public static void main(String[] args) {
	windowView = new WindowView();
	windowView.show();
    }

}
