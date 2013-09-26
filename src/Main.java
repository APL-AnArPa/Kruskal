import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Main 
{
	public static void main(String[] args) 
	{
		if(args.length == 2)
		{
			//check the input and output file formats
			if(args[0].substring(args[0].length() - 3, args[0].length()).equals(".gv") && args[1].substring(args[1].length() - 3, args[1].length()).equals(".gv"))
			{
				Graph G = new Graph();
				
				//parse the *.gv input file to form a graph G
				if(ReadGraphFile(G, args[0]))
				{
					//Spanning tree is generated only if Graph is connected
					if(G.isConnected())
					{
					//run Kruskal's algorithm
					Kruskal krObj = new Kruskal();
					ArrayList<Edge> A = krObj.Kruskal_Algo(G);
					
					//Write to output file
					WriteGraphFile(G, A, args[1]);
					System.out.println("Minimum Spanning Tree successfully generated");
					}
					else
					{
						System.out.println("Graph is disconnected. Cannot generate spanning tree");
					}
				}
			}
			else
			{
				System.out.println("Input and Output files should have .gv extension");
			}
		}
		else
		{
			System.out.println("Usage: java Main <InputGraphFileName> <OutputGraphFileName>");
		}
	}
	
	//Parse the input *.gv file to for a graph G
	public static boolean ReadGraphFile(Graph G, String inputFileName)
	{
		try
		{
			FileReader fileReader = new FileReader(inputFileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String sInput;
			boolean isNodes = false;	//isNode is true for a line that contains vertex information
			boolean isEdges = false;	//isEdge is true for a line that contains edge information
			int braceIndex = -1;
			int noOfBraces = 0;
			int count = 0;
			boolean isError = false;
			while((sInput = bufferedReader.readLine()) != null)
			{
				if(!isNodes && !isEdges)
				{
					if(count == 0)
					{
						//Check for opening braces
						if(sInput.indexOf("graph G {") == -1)
						{
							isError = true;
							System.out.println("Error at line number " + (count + 1) + ". 1st line is always just: 'graph G {'.");
							break;
						}
					}
					else
					{
						//check for the beginning of list of vertices
						if(sInput.indexOf("// nodes") == -1)
						{
							isError = true;
							System.out.println("Error at line number " + (count + 1) + ". 2nd line is always just: '// nodes'.");
							break;
						}
						else
						{
							isNodes = true;
						}
					}
				}
				else if(isNodes)
				{
					if(!sInput.substring(sInput.length() - 1, sInput.length()).equals(";"))
					{
						if(sInput.equals("// edges"))
						{
							isNodes = false;
							isEdges = true;
						}
						else
						{
							isError = true;
							System.out.println("Error at line number " + (count + 1) + ". Format for Node declaration is: <node>;");
							break;
						}
					}
					else
					{
						if(sInput.contains(" -- ") && sInput.contains(" [label=")  && sInput.contains("];"))
						{
							isError = true;
							System.out.println("Error at line number " + (count + 1) + ". After listing the node names, the following line is always just: '// edges'.");
							break;
						}
						else
						{
							//check whether the current vertex is a duplicate of a previous vertex 
							boolean isDuplicate = false;
							for (Vertex v : G.VertexList) 
							{
								if(v.name.equals(sInput.substring(0, sInput.length() - 1)))
								{
									isDuplicate = true;
									break;
								}
							}
							
							//add vertex to the graph if no errors and vertex is not duplicate
							if(!isDuplicate)
							{
								G.VertexList.add(new Vertex(sInput.substring(0, sInput.length() - 1)));
							}
						}
					}
				}
				else
				{
					//check for closing braces
					if(sInput.equals("}"))
					{
						braceIndex = count;
						noOfBraces++;
					}
					else
					{
						if(sInput.contains(" -- ") && sInput.contains(" [label=\"")  && sInput.contains("\"];"))
						{
							String vertexName1 = sInput.substring(0, sInput.indexOf(" -- "));
							String vertexName2 = sInput.substring(sInput.indexOf(" -- ") + 4, sInput.indexOf(" [label=\""));
							boolean present1 = false;
							boolean present2 = false;
							Vertex vertex1 = null;
							Vertex vertex2 = null;
							for (Vertex v : G.VertexList) 
							{
								if(v.name.equals(vertexName1))
								{
									present1 = true;
									vertex1 = v;
								}
								if(v.name.equals(vertexName2))
								{
									present2 = true;
									vertex2 = v;
								}
								if(present1 && present2)
								{
									break;
								}
							}
							//check if edge refers to undeclared vertices
							if(!present1 || !present2)
							{
								isError = true;
								System.out.println("Error at line number " + (count + 1) + ". Edge contains undeclared node.");
								break;
							}
							//check if edge weights are integers
							if(!isInteger(sInput.substring(sInput.indexOf(" [label=\"") + 9, sInput.indexOf("\"];"))))
							{
								isError = true;
								System.out.println("Error at line number " + (count + 1) + ". Edge weight is not numeric.");
								break;
							}
							//add edge to the graph G if no errors
							G.EdgeList.add(new Edge(vertex1, vertex2, 
													Integer.parseInt(sInput.substring(sInput.indexOf(" [label=\"") + 9, sInput.indexOf("\"];")))));
						}
						else
						{
							isError = true;
							System.out.println("Error at line number " + (count + 1) + ". Format of Edge declaration is: <node1> -- <node2> [label=\"<edge weight>\"];");
							break;
						}
					}
				}
				count++;
			}
			bufferedReader.close();
			if(!isError)
			{
				if(braceIndex != count - 1)
				{
					System.out.println("Error at line number " + count + ". The last line should just be closing curly bracket: '}'.");
					isError = true;
				}
				else if(noOfBraces != 1)
				{
					System.out.println("Error at line number " + count + ". There should only a single '}' in the file.");
					isError = true;
				}
			}
			return !isError;			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	//check if the number represented by string s is an integer or not
	public static boolean isInteger(String s) 
	{
		if(s.isEmpty()) 
		{
			return false;
		}
	    try 
	    {
	    	Integer.parseInt(s); 
	    } 
	    catch(NumberFormatException e) 
	    {
	    	return false; 
	    }
	    return true;
	}
	
	//write a graph G to output *.gv file in the specified format
	public static void WriteGraphFile(Graph G, ArrayList<Edge> A, String outputFileName)
	{
		try 
		{
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName, false)));
			out.println("graph G {");
			out.println("// nodes");
			for (Vertex v : G.VertexList) 
			{
				out.println(v.name + ";");
			}
			out.println("// edges");
			for (Edge edge : G.EdgeList) 
			{
				out.print(edge.vertex1.name + " -- " + edge.vertex2.name +"[label=\"" + edge.edgeWeight + "\",");
				boolean isPresent = false;
				for (Edge minEdge : A) 
				{
					if(minEdge.vertex1 == edge.vertex1 && minEdge.vertex2 == edge.vertex2 && minEdge.edgeWeight == edge.edgeWeight)
					{
						out.print("color=blue];");
						isPresent = true;
					}
				}
				if(!isPresent)
				{
					out.print("style=dotted];");
				}
				out.println();
			}
			out.print("}");
		    out.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
