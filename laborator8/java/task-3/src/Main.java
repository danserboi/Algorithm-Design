import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 100005; // 10^5

		int n;
		int m;

		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];

		class Edge {
			int x;
			int y;
			public Edge(int x, int y) {
				super();
				this.x = x;
				this.y = y;
			}
			@Override
			public String toString() {
				return "Edge [x=" + x + ", y=" + y + "]";
			}
			
		}

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();

				for (int i = 1; i <= n; i++)
					adj[i] = new ArrayList<>();
				for (int i = 1; i <= m; i++) {
					int x, y;
					x = sc.nextInt();
					y = sc.nextInt();
					adj[x].add(y);
					adj[y].add(x);
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<Edge> result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				pw.printf("%d\n", result.size());
				for (Edge e : result) {
					pw.printf("%d %d\n", e.x, e.y);
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		// Aflam muchiile critice ale grafului neorientat stocat cu liste
		// de adiacenta in adj.
		private ArrayList<Edge> getResult() {
			ArrayList<Edge> sol = new ArrayList<>();
			int timp = 0;
			int idx[] = new int[n+1];
			int low[] = new int[n+1];
			int parent[] = new int[n+1];
			for(int i = 0; i <= n; i++) {
				idx[i] = -1;
				low[i] = -1;
			}
			for(int v = 1; v <= n; v++) {
				if(idx[v] == -1) {
					timp = dfsB(sol, timp, parent, low, idx, v, parent[v]);
				}
			}
			return sol;
		}

		public int dfsB (ArrayList<Edge> sol, int timp, int [] parent, int [] low, int [] idx, int v, int p) {
			idx[v] = low[v] = timp++;
			// System.out.printf("idx[%d]=%d ",v,idx[v]);
			// System.out.printf("low[%d]=%d\n",v,low[v]);
			for(int i = 0; i <= adj[v].size() - 1; i++) {
				int u = adj[v].get(i);
				if(idx[u] == -1) {
					parent[u] = v;
					timp = dfsB(sol, timp, parent, low, idx, u, v);
					low[v] = Integer.min(low[v], low[u]);
					// (v,u) e muchie critica, fara ea, 
					// din u nu se mai poate ajunge pe un nivel mai mic sau egal lui v
					if(low[u] > idx[v]) {
						// System.out.println("Muchia:"+v+"-"+u);
						sol.add(new Edge(v,u));
					}
				}
				else {
					if(u != parent[v]) {
						// actualizare low
						low[v] = Integer.min(low[v], idx[u]);
					}
				}
			}
			return timp;
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
