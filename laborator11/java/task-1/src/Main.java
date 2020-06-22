import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 1005;

		int n;
		int m;

		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];
		int C[][];
		int f[][];
		
		private void readInput() {
			try {
				BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
				String linie = br.readLine();
				String[] campuri = linie.split(" ");
				n = Integer.parseInt(campuri[0]);
				m = Integer.parseInt(campuri[1]);
				C = new int[n + 1][n + 1];
				f = new int[n + 1][n + 1];
				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				// In adj este stocat graful neorientat obtinut dupa ce se elimina orientarea
				// arcelor, iar in C sunt stocate capacitatile arcelor.
				// De exemplu, un arc (x, y) de capacitate z va fi tinut astfel:
				// C[x][y] = z, adj[x] contine y, adj[y] contine x.
				// C[y][x] = 0, deoarece arcul (y, x) nu este in graful initial  
				for (int i = 1; i <= m; i++) {
					linie = br.readLine();
					campuri = linie.split(" ");
					int x, y, z;
					x = Integer.parseInt(campuri[0]);
					y = Integer.parseInt(campuri[1]);
					z = Integer.parseInt(campuri[2]);
					adj[x].add(y);
					adj[y].add(x);
					C[x][y] += z;
				}
				br.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				pw.printf("%d\n", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		// Folosind algoritmul Edmonds-Karp,
		// calculam fluxul maxim pe graful orientat dat.
		// Sursa este nodul 1. Destinatia este nodul n.
		// Complexitatea temporala a algoritmului este O(V*E^2). 
		private int getResult() {
			int flow = 0;
			int dest = n;
			while(true) {
				int[] parent = BFS();
				if(parent[dest] == 0)
					break;
				int cf_path = Integer.MAX_VALUE;
				int v = dest;
				int u = parent[v];
				while(u != 0) {
					if(cf_path > C[u][v] - f[u][v]) {
						cf_path = C[u][v] - f[u][v];
					}
					v = u;
					u = parent[u];
				}
				flow += cf_path;
				v = dest;
				u = parent[v];
				while(u != 0) {
					f[u][v] += cf_path;
					f[v][u] = -f[u][v];
					v = u;
					u = parent[u];
				}
			}
			return flow;
		}
		
		private int[] BFS() {
			int parent[] = new int[n+1];
			int visited[] = new int[n + 1];
			int source = 1;
			visited[source] = 1;
			Queue<Integer> q = new LinkedList<Integer>();
			q.add(source);
			while(!q.isEmpty()) {
				Integer currNode = q.peek();
				for(Integer succesor : adj[currNode]) {
					// conditia ca muchia sa existe in graful rezidual este:
					// capacitatea reziduala sa fie diferita de 0
					if(visited[succesor] == 0 && succesor != source 
						&& C[currNode][succesor] - f[currNode][succesor] > 0) {
						parent[succesor] = currNode;
						visited[succesor] = 1;
						q.add(succesor);
					}
				}
				q.poll();
			}
			return parent;
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