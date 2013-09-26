import java.util.ArrayList;


//Graph object contains list of vertices and edges
public class Graph 
{
	ArrayList<Vertex> VertexList = new ArrayList<Vertex>();
	ArrayList<Edge> EdgeList = new ArrayList<Edge>();
	
	//Checking whteher graph is connected or disconnected using Breadth First Search
	public boolean isConnected()
	{
		if(VertexList.size() == 0)
		{
			return false;
		}
		Vertex vertex = VertexList.get(0);
		ArrayList<Vertex> Q = new ArrayList<Vertex>();
		Q.add(vertex);
		int count = 0;
		while(Q.size() != 0)
		{
			vertex = Q.get(0);
			Q.remove(0);
			if(!vertex.visited)
			{
				vertex.visited = true;
				count++;
			}
			for (Edge edge : EdgeList) 
			{
				if(edge.vertex1 == vertex)
				{
					if(!edge.vertex2.visited)
					{
						Q.add(edge.vertex2);
					}
				} 
				else if(edge.vertex2 == vertex)
				{
					if(!edge.vertex1.visited)
					{
						Q.add(edge.vertex1);
					}
				}
			}
		}
		if(count == VertexList.size())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}

class Vertex
{
	String name;
	Vertex parent;
	int rank;
	boolean visited;
	
	public Vertex(String name) 
	{
		this.name = name;
		parent = null;
		rank = 0;
		visited = false;
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
