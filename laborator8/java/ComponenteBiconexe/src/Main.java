import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "biconex.in";
		public static final String OUTPUT_FILE = "biconex.out";
		public static final int NMAX = 100005; // 10^5

		int n;
		int m;

		
		Set<Integer> hashSet = new HashSet<Integer>(); 
		
		ArrayList<Integer> component = new ArrayList<Integer>();
		
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
				BufferedReader br = new BufferedReader(new FileReader(
						INPUT_FILE));
				String line = br.readLine();
				String[] fields = line.split(" ");
				n = Integer.parseInt(fields[0]);
				m = Integer.parseInt(fields[1]);
				
				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y;
					line = br.readLine();
					fields = line.split(" ");
					x = Integer.parseInt(fields[0]);
					y = Integer.parseInt(fields[1]);
					adj[x].add(y);
				}
				br.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<ArrayList<Integer>> result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				
				pw.printf("%d\n", result.size());
				for(int i = 0; i <= result.size() - 1; i++) {
					ArrayList<Integer> comp = result.get(i);
					for(int j = 0; j <= comp.size() - 2; j++) {
						pw.printf("%d ", comp.get(j));						
					}
					pw.printf("%d", comp.get(comp.size() - 1));
					pw.println();
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private ArrayList<ArrayList<Integer>> getResult() {
			ArrayList<ArrayList<Integer>> sol = new ArrayList<>();
			int idx[] = new int[n+1];
			int low[] = new int[n+1];
			Stack<Edge> stack = new Stack<Edge>();
			int p = 0;
			int timp = 0;
			for(int i = 0; i <= n; i++) {
				idx[i] = -1;
			}
			for(int v = 1; v <= n; v++) {
				if(idx[v] == -1) {
					timp = dfsBC(sol, stack, timp, p, low, idx, v);
				}
			}
			return sol;
		}

		public int dfsBC(ArrayList<ArrayList<Integer>> sol, Stack<Edge> stack, int timp, int parent, int[] low, int[] idx, int v) {
			idx[v] = timp;
			low[v] = timp;
			timp++;
			for(int i = 0; i <= adj[v].size() - 1; i++) {
				int u = adj[v].get(i);
				// daca u nu este parintele lui v
				if(parent != u) {
					// daca nodul este nevizitat
					if(idx[u] == -1) {
						System.out.println("Nod nou stiva: " + v + "-" + u);
						stack.push(new Edge(v, u));
						timp = dfsBC(sol, stack, timp, v, low, idx, u);
						low[v] = Integer.min(low[v], low[u]);
					
						int x,y;
						if(low[u] >= idx[v]) {
							hashSet = new HashSet<Integer>(); ;
							component = new ArrayList<Integer>();
							do {
								x = stack.peek().x;
								y = stack.peek().y;
								hashSet.add(x);
								hashSet.add(y);
								stack.pop();
							} while((x != v || y != u));
							
							for(Integer el : hashSet) {
								component.add(el);
							}
							sol.add(component);
						}
					} else {
						// nodul u este descoperit, verificam daca gasim o scurtatura catre un stramos
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
