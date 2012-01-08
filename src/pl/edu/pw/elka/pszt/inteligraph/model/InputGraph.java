package pl.edu.pw.elka.pszt.inteligraph.model;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;

public class InputGraph 
{
	private Graph<VertexName, String> g = new SparseGraph<VertexName, String>();
	
	public void addNewVertex(VertexName vertex)
		{
			g.addVertex(vertex);
		}
	public void addNewEdge(String edegName ,VertexName begin, VertexName end )
		{
			g.addEdge(edegName, begin, end);
		}
	public Graph<VertexName, String> getGraph()
	{
		return g;
	}
	
	
}