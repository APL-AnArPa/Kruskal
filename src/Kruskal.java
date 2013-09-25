import java.util.ArrayList;


public class Kruskal 
{
	public ArrayList<Edge> Kruskal_Algo(Graph G)
	{
		ArrayList<Edge> A = new ArrayList<Edge>();
		UnionFind ufObj = new UnionFind();
		for (Vertex v: G.VertexList)
		{
			ufObj.Make_Set(v);
		}
		Sort sObj = new Sort();
		ArrayList<Edge> temp = new ArrayList<Edge>();
		for (Edge edge : G.EdgeList) 
		{
			temp.add(new Edge(edge.vertex1, edge.vertex2, edge.edgeWeight));
		}
		sObj.MergeSort(temp, 0, G.EdgeList.size() - 1);
		for (Edge edge : temp) 
		{
			Vertex U = ufObj.Find_Set(edge.vertex1);
			Vertex V = ufObj.Find_Set(edge.vertex2);
			if(U != V)
			{
				A.add(edge);
				ufObj.Union(U, V);
			}
		}
		return A;
	}
}
