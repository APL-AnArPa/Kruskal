import java.util.ArrayList;


public class Graph 
{
	ArrayList<Vertex> VertexList = new ArrayList<Vertex>();
	ArrayList<Edge> EdgeList = new ArrayList<Edge>();
}

class Vertex
{
	String name;
	Vertex parent;
	int rank;
	
	public Vertex(String name) 
	{
		this.name = name;
		parent = null;
		rank = 0;
	}
}

class Edge
{
	Vertex vertex1;
	Vertex vertex2;
	int edgeWeight;
	
	public Edge(Vertex vertex1,	Vertex vertex2,	int edgeWeight) 
	{
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.edgeWeight = edgeWeight;
	}
}
