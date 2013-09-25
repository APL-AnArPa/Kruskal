
public class UnionFind
{
	//Creates a singleton set and makes x as the root of the set
	//if x does not already belong to some other set
	public void Make_Set(Vertex x)
	{
		if(x != null && x.parent == null)
		{
			x.parent = x;
			x.rank = 0;
		}
	}	
	
	//Find reference of root of the set to which x belongs
	public Vertex Find_Set(Vertex x)
	{
		if(x == null)
			return null;
		if(x != x.parent)
			x.parent = Find_Set(x.parent);
		return x.parent;
	}

	//Union two sets by specifying some elements in the two sets
	public void Union(Vertex x, Vertex y)
	{
		if(x == null || y == null)
			return;
		Vertex u = Find_Set(x);
		Vertex v = Find_Set(y);
		if(u == v) 
			return;
		if(u.rank > v.rank)
			v.parent = u;
		else
		{			
			u.parent = v;
			if(u.rank == v.rank)
				v.rank++;
		}
	}
}
