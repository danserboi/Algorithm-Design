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

		private void writeOutput(ArrayList<Integer> result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				for (int node : result) {
					pw.printf("%d ", node);
				}
				pw.printf("\n");
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		/*
		// VARIANTA 1
		// Calculam punctele de articulatie(nodurile critice)
		// ale grafului neorientat stocat cu liste de adiacenta in adj.
		private ArrayList<Integer> getResult() {
			ArrayList<Integer> sol = new ArrayList<>();
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
					timp = dfsCV(sol, timp, parent, low, idx, v);
				}
			}
			return sol;
		}

		public int dfsCV(ArrayList<Integer> sol, int timp, int[] parent, int[] low, int[] idx, int v) {
			idx[v] = timp;
			low[v] = timp;
			timp++;
			ArrayList<Integer> childs = new ArrayList<Integer>();
			for(int i = 0; i <= adj[v].size() - 1; i++) {
				int u = adj[v].get(i);
				// daca u nu este parintele lui v
				if(parent[v] != u) {
					// daca nodul este nevizitat
					if(idx[u] == -1) {
						childs.add(u);
						parent[u] = v;
						timp = dfsCV(sol, timp, parent, low, idx, u);
						low[v] = Integer.min(low[v], low[u]);
					}
					else {
						// nodul u este descoperit, verificam daca gasim o scurtatura catre un stramos
						low[v] = Integer.min(low[v], idx[u]);
					}
				}
			}
			// v este punct de articulatie daca:
			if(parent[v] == 0) {				
				// v este radacina arborelui de adancime si v are doi sau mai multi copii 
				if(childs.size() >= 2) {
					sol.add(v);
				}
			} else {
				// v nu este radacina arborelui de adancime si are un copil u in arbore, 
				// astfel incat niciun nod din subarborele dominat de u 
				// nu este conectat cu un stramos al lui v printr-o muchie inapoi 
				// (copii lui nu pot ajunge pe alta cale pe un nivel superior 
				// in arborele de adancime)
				int ok = 0;
				for(int i = 0; i <= childs.size() - 1; i++) {
					int u = childs.get(i);
					if(low[u] >= idx[v]) {
						ok = 1;
						break;
					}
				}
				if(ok == 1) {
					sol.add(v);
				}
			}
			return timp;
		}
		*/
		// VARIANTA 2
		// Calculam punctele de articulatie(nodurile critice)
		// ale grafului neorientat stocat cu liste de adiacenta in adj.
		private ArrayList<Integer> getResult() {
			// reține punctele de articulație
			ArrayList<Integer> sol = new ArrayList<>();
			int timp = 0;
			int idx[] = new int[n+1];
			int low[] = new int[n+1];
			int parent[] = new int[n+1];
			// reține numărul de subarbori dominați de u
			int subarb[] = new int[n+1];
			// reține punctele de articulație
			int art[] = new int[n+1];
			for(int i = 0; i <= n; i++) {
				idx[i] = -1;
				low[i] = -1;
			}
			for(int v = 1; v <= n; v++) {
				if(idx[v] == -1) {
					timp = dfsCV(sol, timp, art, subarb, parent, low, idx, v);
					// cazul în care u este rădăcina arborelui DFS si are mai multi subarbori
					if(subarb[v] >= 2) {
						art[v] = 1;
					}
				}
			}
			for(int i = 1; i <= n; i++) {
				if(art[i] == 1)
					sol.add(i);
			}
			return sol;
		}

		public int dfsCV(ArrayList<Integer> sol, int timp, int[] art, int[] subarb, int[] parent, int[] low, int[] idx, int v) {
			idx[v] = timp;
			low[v] = timp;
			timp++;
			for(int i = 0; i <= adj[v].size() - 1; i++) {
				int u = adj[v].get(i);
				// daca nodul este nevizitat
				if(idx[u] == -1) {
					parent[u] = v;
					subarb[v]++;
					timp = dfsCV(sol, timp, art, subarb, parent, low, idx, u);
					low[v] = Integer.min(low[v], low[u]);
					// v nu este radacina arborelui de adancime si are un copil u in arbore, 
					// astfel incat niciun nod din subarborele dominat de u 
					// nu este conectat cu un stramos al lui v printr-o muchie inapoi 
					// (copii lui nu pot ajunge pe alta cale pe un nivel superior 
					// in arborele de adancime)
					if(parent[v] != 0 && low[u] >= idx[v])
						art[v] = 1;
				}
				else {
					// nodul u este descoperit, verificam daca gasim o scurtatura catre un stramos
					if(parent[v] != u)
						low[v] = Integer.min(low[v], idx[u]);
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
