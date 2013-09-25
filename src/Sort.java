import java.util.ArrayList;


public class Sort 
{
	public void MergeSort(ArrayList<Edge> A, int p, int q)
	{
		if(p < q)
		{
			int mid = (p + q)/2;
			MergeSort(A, p, mid);
			MergeSort(A, mid + 1, q);
			Merge(A, p, mid, q);
		}
	}
	
	private void Merge(ArrayList<Edge> A, int p, int q, int r)
	{
		int n1, n2;
		n1 = (q - p + 1);
		n2 = r - q;
		ArrayList<Edge> L = new ArrayList<Edge>();
		ArrayList<Edge> R = new ArrayList<Edge>();
		for(int i = 0; i < n1; i++)
		{
			L.add(new Edge(A.get(p + i).vertex1, A.get(p + i).vertex2, A.get(p + i).edgeWeight));
		}
		for(int i = 0; i < n2; i++)
		{
			R.add(new Edge(A.get(q + i + 1).vertex1, A.get(q + i + 1).vertex2, A.get(q + i + 1).edgeWeight));
		}
		int i = 0, j = 0, k;
		for(k = p; k <= r; k++)
		{
			if(i < n1 && j < n2)
			{
				if(L.get(i).edgeWeight <= R.get(j).edgeWeight)
				{
					Edge e = A.get(k);
					e.vertex1 = L.get(i).vertex1;
					e.vertex2 = L.get(i).vertex2;
					e.edgeWeight = L.get(i).edgeWeight;
					i++;
				}
				else
				{
					Edge e = A.get(k);
					e.vertex1 = R.get(j).vertex1;
					e.vertex2 = R.get(j).vertex2;
					e.edgeWeight = R.get(j).edgeWeight;
					j++;
				}
			}
			else
			{
				break;
			}
		}
		if(i < n1)
		{
			while(i < n1)
			{
				Edge e = A.get(k);
				e.vertex1 = L.get(i).vertex1;
				e.vertex2 = L.get(i).vertex2;
				e.edgeWeight = L.get(i).edgeWeight;
				i++;
				k++;
			}
		}
		else if(j < n2)
		{
			while(j < n2)
			{
				Edge e = A.get(k);
				e.vertex1 = R.get(j).vertex1;
				e.vertex2 = R.get(j).vertex2;
				e.edgeWeight = R.get(j).edgeWeight;
				j++;
				k++;
			}
		}
	}
}
