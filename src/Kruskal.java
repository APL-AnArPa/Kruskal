import java.util.ArrayList;

//Implementation Of Kruskal's Algorithm
public class Kruskal 
{
	public ArrayList<Edge> Kruskal_Algo(Graph G)
	{
		ArrayList<Edge> A = new ArrayList<Edge>();
		UnionFind ufObj = new UnionFind();
		for (Vertex v: G.VertexList)        //Makeset for each vertex
		{
			ufObj.Make_Set(v);
		}
		Sort sObj = new Sort();    
		ArrayList<Edge> temp = new ArrayList<Edge>();
		for (Edge edge : G.EdgeList) 
		{
			temp.add(new Edge(edge.vertex1, edge.vertex2, edge.edgeWeight)); //store sorted edges in a temporary array temp.
		}
		sObj.MergeSort(temp, 0, G.EdgeList.size() - 1);    //sort edges in increasing order of their weights.
		for (Edge edge : temp) //for each e=(U,V), check whether by adding e in MST whether cycle is formed or not.
		{
			Vertex U = ufObj.Find_Set(edge.vertex1);   //find set of U
			Vertex V = ufObj.Find_Set(edge.vertex2);   //find set of V
			if(U != V)                                 //If U and V are from different set, means they don't form cycle.
			{
				A.add(edge);                       //add e as MST edge
				ufObj.Union(U, V);                 //Union U, V
			}
		}
		return A;
	}
}
