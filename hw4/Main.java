package hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		Scanner file = null;
		try {
			file = new Scanner(new File("./src/hw4/file.in"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for (int nCases = file.nextInt(); nCases > 0; nCases--) {
			
			int nVertices = file.nextInt();
			ArrayList<Vertex> allRegions = new ArrayList<Vertex>();
			ArrayList<Vertex> myRegions = new ArrayList<Vertex>();
			int nMyRegions = 0;

			// Read in the armies in each region
			for (int i = 0; i < nVertices; i++) {
				//store noArmies and an id to be use for comparison in an arraylist
				Vertex v = new Vertex(file.nextInt(), i);
				allRegions.add(v);
				//if i control v add it to myRegions
				if (allRegions.get(i).armies > 0) {
					myRegions.add(v);
					nMyRegions++;
				}
			}

			// Read in the N/Y matrix
			for (int i = 0; i < nVertices; i++) {
				char[] adjLine = file.next().toCharArray();
				for (int j = 0; j < nVertices; j++) {
					if (adjLine[j] == 'Y') {
						//input[i][j] represents the edge u->v where u is the ith vertex and v is the jth.
						Vertex u = allRegions.get(i);
						Vertex v = allRegions.get(j);
						//if u has no armies and I control v then v is a border.
						//I know this because connection are bidirectional so I know v->u will happen later
						if (u.armies == 0 && myRegions.contains(v)) {
							myRegions.get(myRegions.indexOf(v)).border = true;
						} else if (myRegions.contains(u) && myRegions.contains(v)) {
							myRegions.get(myRegions.indexOf(u)).addEdge(v);
						}
					}
				}
			}

			// s, t, all v, all v'
			int vInFlowGraph = 2 + nMyRegions * 2;
			// Index for source and sink are the last two vertices in the graph.
			int s = vInFlowGraph - 2;
			int t = vInFlowGraph - 1;

			int[][] flowGraph = new int[vInFlowGraph][vInFlowGraph];
			int nBorders = 0;
			// Construct New Flow Graph
			for (int v = 0; v < nMyRegions; v++) {
				Vertex curV = myRegions.get(v);
				//increment if v.border == true
				nBorders = curV.border ? nBorders + 1 : nBorders;
				// cap(s -> v) = noArmies - 1
				flowGraph[s][v] = curV.armies - 1;
				// cap(v -> v') = noArmies
				flowGraph[v][v + nMyRegions] = curV.armies;
				// cap(v' -> all nbor of v) = noArmies
				for (int j = 0; j < curV.adj.size(); j++) {
					int nborV = myRegions.indexOf(curV.adj.get(j));
					flowGraph[v + nMyRegions][nborV] = curV.armies;
				}
				// cap(border v -> t) = 1
				if (curV.border)
					flowGraph[v][t] = 1;
			}

			int maxFlow = nBorders;
			int i = 0;
			while (maxFlow == nBorders) {
				i++;
				maxFlow = FordFulkerson.findMaxFlow(s, t, flowGraph);

				for (Vertex v : myRegions) {
					if (v.border)
						flowGraph[myRegions.indexOf(v)][t] = 1;
				}

			}

			System.out.println(i);

		}
		file.close();

	}
	
	public static class Vertex {
		boolean border;
		int armies;
		int id;
		ArrayList<Vertex> adj;

		public Vertex(int armies, int id) {
			this.armies = armies;
			this.id = id;
			this.border = false;
			adj = new ArrayList<Vertex>();
		}

		public void addEdge(Vertex v) {
			if (!adj.contains(v))
				adj.add(v);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Vertex other = (Vertex) obj;
			if (id != other.id)
				return false;
			return true;
		}

	}

	public static class FordFulkerson {
		private static boolean[] visited;
		private static int[] parent;
		private static Queue<Integer> queue;

		public static int findMaxFlow(int s, int t, int graph[][]) {
			int maxFlow = 0;

			queue = new LinkedList<Integer>();
			parent = new int[graph.length];
			visited = new boolean[graph.length];

			while (bfs(s, t, graph)) {
				int u, v, flow = Integer.MAX_VALUE;
				//find bottleneck
				for (v = t; v != s; v = parent[v]) {
					u = parent[v];
					flow = Math.min(flow, graph[u][v]);
				}
				//set u->v = capacity-flow and v->u = f(v->u)+flow
				for (v = t; v != s; v = parent[v]) {
					u = parent[v];
					graph[u][v] -= flow;
					graph[v][u] += flow;
				}
				maxFlow += flow;
			}

			return maxFlow;
		}
		
		private static boolean bfs(int s, int t, int graph[][]) {
			//initialize parent and visited arrays
			for (int vertex = 0; vertex < graph.length; vertex++) {
				parent[vertex] = -1;
				visited[vertex] = false;
			}
			
			queue.add(s);
			parent[s] = -1;
			visited[s] = true;

			while (!queue.isEmpty()) {
				int elt = queue.remove();

				for(int dest = 0; dest < graph.length; dest++) {
					if (graph[elt][dest] > 0 && !visited[dest]) {
						parent[dest] = elt;
						queue.add(dest);
						visited[dest] = true;
					}
				}
			}
			
			return visited[t];
		}
	}

}
