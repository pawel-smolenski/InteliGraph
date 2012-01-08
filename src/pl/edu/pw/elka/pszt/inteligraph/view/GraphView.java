package pl.edu.pw.elka.pszt.inteligraph.view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.TransformerUtils;

import pl.edu.pw.elka.pszt.inteligraph.Constans;
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

    private Graph<Integer, String> graph;
    private Map<Integer, Point2D> map;
    private Transformer<Integer, Point2D> trans;
    private Layout<Integer, String> layout;
    private VisualizationViewer<Integer, String> visualizationViewer;
    private DefaultModalGraphMouse<Object, Object> graphMouse;

    public GraphView() {
	graph = createFakeGraph();
	map = new HashMap<Integer, Point2D>();

	createFakePointPositions();

	trans = TransformerUtils.mapTransformer(map);

	layout = new StaticLayout<Integer, String>(graph, trans);
	layout.setSize(new Dimension(Constans.WINDOW_WIDTH,
		Constans.WINDOW_HEIGHT));

	visualizationViewer = new VisualizationViewer<Integer, String>(layout);

	// oznaczenia wierzchołków 
	visualizationViewer.getRenderContext().setVertexLabelTransformer(
		new ToStringLabeller());
	// oznaczenia krawędzi
	visualizationViewer.getRenderContext().setEdgeLabelTransformer(
		new ToStringLabeller());
	// linie proste
	visualizationViewer.getRenderContext().setEdgeShapeTransformer(
		new EdgeShape.Line());

	// Graf dla obsługi myszy
	graphMouse = new DefaultModalGraphMouse<Object, Object>();
	graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
	visualizationViewer.setGraphMouse(graphMouse);

    }

    /**
     * Odświeżanie widoku grafu.
     */
    public void refresh() {
	visualizationViewer.updateUI();
    }

    /**
     * Ustawia liczbę wierzchołków grafu.
     * @param howMany Ile wierzchołków
     */
    public void setNumberOfVertices(int howMany) {
	for (int i = 0; i < howMany; ++i) {
	    graph.addVertex((Integer) i);
	}
    }
    
    /**
     * Zwraca widok utworzonego grafu, wraz z dołączoną obsługą myszki.
     * 
     * @return
     */
    public VisualizationViewer<Integer, String> getVisualizationViewer() {
	return visualizationViewer;
    }

    // TODO usunąć te metody, zamienić je na coś co normalne dane dostaje
    private void createFakePointPositions() {
	map.put(1, new Point(0, 0));
	map.put(2, new Point(150, 200));
	map.put(3, new Point(0, 300));
    }

    private Graph<Integer, String> createFakeGraph() {
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
