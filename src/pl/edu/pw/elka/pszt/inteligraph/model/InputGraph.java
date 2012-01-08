package pl.edu.pw.elka.pszt.inteligraph.model;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;

public class InputGraph 
{
	private Graph<Integer, String> g = new SparseGraph<Integer, String>();
	
	public void addNewVertex(Integer vertex)
		{
			g.addVertex(vertex);
		}
	public void addNewEdge(String edegName ,Integer begin, Integer end )
		{
			g.addEdge(edegName, begin, end);
		}
	public Graph<Integer, String> getGraph()
	{
		return g;
	}
	
	
}