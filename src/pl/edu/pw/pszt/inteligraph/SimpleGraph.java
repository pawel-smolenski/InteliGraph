package pl.edu.pw.pszt.inteligraph;

import java.awt.Dimension;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
 
public class SimpleGraph extends JFrame {
  public SimpleGraph (){
    super("Mój pierwszy graf");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    Graph g = getGraph();
    VisualizationViewer<Integer,String> vv = 
    		new VisualizationViewer<Integer,String>(new FRLayout(g),
     new Dimension (300,200));
    getContentPane().add(vv);
 
    //pack();
    setSize (new Dimension (400, 300));
    setVisible(true);
  }
 
  public Graph getGraph() {
    Graph<Integer, String> g = new SparseGraph<Integer, String>();
    g.addVertex((Integer)1);
    g.addVertex((Integer)2);
    g.addVertex((Integer)3);
    g.addEdge("Edge-A", 1, 2);
    g.addEdge("Edge-B", 2, 3);
    g.addEdge("Edge-C", 3, 1);
    return g;
  }
}
