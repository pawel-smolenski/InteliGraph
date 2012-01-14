package pl.edu.pw.elka.pszt.inteligraph.view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.TransformerUtils;

import pl.edu.pw.elka.pszt.inteligraph.Constans;
import pl.edu.pw.elka.pszt.inteligraph.model.VertexName;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
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

    private Graph<VertexName, String> graph;
    private Map<VertexName, Point2D> map;
    private Transformer<VertexName, Point2D> trans;
    private Layout<VertexName, String> layout;
    private VisualizationViewer<VertexName, String> visualizationViewer;
    private DefaultModalGraphMouse<Object, Object> graphMouse;

    public GraphView(Graph<VertexName, String> g, Map<VertexName, Point2D> m) {
	graph = g;
	map = m;

	trans = TransformerUtils.mapTransformer(map);

	layout = new StaticLayout<VertexName, String>(graph, trans);
	//layout = new CircleLayout<VertexName, String>(graph);
	layout.setSize(new Dimension(Constans.WINDOW_WIDTH,
		Constans.WINDOW_HEIGHT));

	visualizationViewer = new VisualizationViewer<VertexName, String>(layout);

	// oznaczenia wierzchołków i
	visualizationViewer.getRenderContext().setVertexLabelTransformer(
		new ToStringLabeller());
//	visualizationViewer.getRenderContext().setEdgeLabelTransformer(
//		new ToStringLabeller());
	// linie proste
	visualizationViewer.getRenderContext().setEdgeShapeTransformer(
		new EdgeShape.Line());

	// Graf dla obsługi myszy
	graphMouse = new DefaultModalGraphMouse<Object, Object>();
	graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
	visualizationViewer.setGraphMouse(graphMouse);

    }

    public void setGraphMapData(Graph<VertexName, String> g,
	    Map<VertexName, Point2D> m) {
	graph = g;
	map = m;
    }

    /**
     * Odświeżanie widoku grafu.
     */
    public void refresh() {
	visualizationViewer.updateUI();
    }

    /**
     * Zwraca widok utworzonego grafu, wraz z dołączoną obsługą myszki.
     * 
     * @return
     */
    public VisualizationViewer<VertexName, String> getVisualizationViewer() {
	return visualizationViewer;
    }

}
