package pl.edu.pw.pszt.inteligraph.view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.TransformerUtils;

import pl.edu.pw.pszt.inteligraph.Constans;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class GraphView {

    private Graph graph;
    private Map<Integer, Point2D> map;
    private Transformer<Integer, Point2D> trans;
    private Layout<Integer, String> layout;
    private VisualizationViewer<Integer,String> visualizationViewer;
    private DefaultModalGraphMouse graphMouse;
    
    public GraphView() {
	graph = createFakeGraph();
	map = new HashMap<Integer, Point2D>();
	
	map.put(1, new Point(0, 0));
	map.put(2, new Point(150,200));
	map.put(3, new Point(0,300));
	
	trans = TransformerUtils.mapTransformer(map);
	
        layout = new StaticLayout<Integer, String>(graph, trans);
        layout.setSize(new Dimension(Constans.WINDOW_WIDTH, Constans.WINDOW_HEIGHT));
        
        visualizationViewer =  new VisualizationViewer<Integer,String>(layout);
        
        // oznaczenia wierzchołków i 
        visualizationViewer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        visualizationViewer.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        // linie proste
        visualizationViewer.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line());
        
        // Graf dla obsługi myszy
        graphMouse = new DefaultModalGraphMouse();
        graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        visualizationViewer.setGraphMouse(graphMouse);
	
    }
    
    public void refresh() {
	
    }
    
    /**
     * Zwraca widok utworzonego grafu, wraz z obsługą myszki.
     * @return
     */
    public VisualizationViewer<Integer, String> getVisualizationViewer() {
	return visualizationViewer;
    }
    

    
    private Graph createFakeGraph() {
	Graph<Integer, String> g = new SparseGraph<Integer, String>();
	g.addVertex((Integer) 1);
	g.addVertex((Integer) 2);
	g.addVertex((Integer) 3);
	g.addEdge("Edge-A", 1, 2);
	g.addEdge("Edge-B", 2, 3);
	g.addEdge("Edge-C", 3, 1);
	return g;
    }
}
