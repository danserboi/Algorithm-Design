import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 50005;
		public static final int MAX = 1000007;
		int n;
		int m;
		int source;

		public class Edge {
			public int node;
			public int cost;

			Edge(int _node, int _cost) {
				node = _node;
				cost = _cost;
			}
		}

		@SuppressWarnings("unchecked")
		ArrayList<Edge> adj[] = new ArrayList[NMAX];

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();
				source = sc.nextInt();

				for (int i = 1; i <= n; i++)
					adj[i] = new ArrayList<>();
				for (int i = 1; i <= m; i++) {
					int x, y, w;
					x = sc.nextInt();
					y = sc.nextInt();
					w = sc.nextInt();
					adj[x].add(new Edge(y, w));
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<Integer> result) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(
								OUTPUT_FILE));
				StringBuilder sb = new StringBuilder();
				if (result.size() == 0) {
					sb.append("Ciclu negativ!\n");
				} else {
					for (int i = 1; i <= n; i++) {
						sb.append(result.get(i)).append(' ');
					}
					sb.append('\n');
				}
				bw.write(sb.toString());
				bw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		// Folosind Bellman-Ford pe graful orientat cu n noduri, m arce stocat in
		// aflam distantele minime de la nodul source la celelalte noduri
		// Complexitatea algoritmului este O(|E|*|V|). 
		private ArrayList<Integer> getResult() {
			//	d[node] = costul minim / lungimea minima a unui drum de la source la nodul node;
			//	d[source] = 0;
			//	d[node] = -1, daca nu se poate ajunge de la source la node.
			ArrayList<Integer> d = new ArrayList<Integer>();
			for (int i = 0; i <= n; i++)
				d.add(MAX);
			d.set(source, 0);
			int[] p = new int[n+1];
			for(int i = 1; i <= n - 1; i++) {
				for(int u = 1; u <= n; u++) {
					for(int j = 0; j <= adj[u].size() - 1; j++) {
						int v = adj[u].get(j).node;
						// incercam sa relaxam muchia u->v
						if(d.get(v) > d.get(u) + adj[u].get(j).cost) {
							d.set(v, d.get(u) + adj[u].get(j).cost);
							p[v] = u;
						}
					}
				}
			}
			for(int u = 1; u <= n; u++) {
				for(int j = 0; j <= adj[u].size() - 1; j++) {
					int v = adj[u].get(j).node;
					// daca mai exista o muchie care poate fi relaxata, inseamna ca avem un ciclu de cost negativ
					if(d.get(v) > d.get(u) + adj[u].get(j).cost) {
						return new ArrayList<Integer>();
					}
				}
			}
			// daca nu avem drum de la sursa la nod, consideram distanta -1
			for(int i = 1; i <= n; i++) {
				if(d.get(i) == MAX) {
					d.set(i,-1);
				}
			}
			return d;
		}

		public void solve() {
			readInput();
			writeOutput(getResult());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
