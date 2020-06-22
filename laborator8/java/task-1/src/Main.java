import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;
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
		@SuppressWarnings("unchecked")
		ArrayList<Integer> adjt[] = new ArrayList[NMAX];

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
					adjt[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y;
					line = br.readLine();
					fields = line.split(" ");
					x = Integer.parseInt(fields[0]);
					y = Integer.parseInt(fields[1]);
					adj[x].add(y);
					adjt[y].add(x);
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
				for (ArrayList<Integer> ctc : result) {
					for (int nod : ctc) {
						pw.printf("%d ", nod);
					}
					pw.printf("\n");
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		// Gasim componentele tare conexe ale grafului orientat cu
		// n noduri, stocat in adj. Rezultatul se va returna sub forma
		// unui ArrayList, ale carui elemente sunt componentele tare conexe
		// detectate. Nodurile si componentele tare conexe pot fi puse in orice
		// ordine in arraylist. Graful transpus este stocat in adjt.
		
		/*
		// Varianta Kosaraju
		// Algoritmul foloseşte două DFS (una pe graful iniţial şi una pe graful transpus)
		// şi o stivă pentru a reţine ordinea terminării parcurgerii nodurilor grafului 
		// original (evitând astfel o sortare a nodurilor după acest timp 
		// la terminarea parcurgerii).
		private ArrayList<ArrayList<Integer>> getResult() {
			ArrayList<ArrayList<Integer>> sol = new ArrayList<>();
			Stack<Integer> stack = new Stack<Integer>();
			int[] visited = new int[n+1];			
			// cat timp exista un nod v din V care nu e pe stiva	
			for(int v = 1; v <= n; v++) {
				if(stack.search(v) == -1) {
					dfs(stack, visited, v);
				}				
			}
			for(int i = 1; i <= n; i++) {
				visited[i] = 0;
			}
			while(!stack.empty()) {
				int v = stack.pop();
				ArrayList<Integer> stronglyConnComp = new ArrayList<Integer>();
				dfsT(stronglyConnComp, visited, v);
				sol.add(stronglyConnComp);
				stack.removeAll(stronglyConnComp);
			}
			return sol;
		}
		
		private void dfs(Stack<Integer> stack, int[] visited, int v) {
			visited[v] = 1;
			for(int i = 0; i <= adj[v].size() - 1; i++) {
				int u = adj[v].get(i);
				if(visited[u] == 0) {
					dfs(stack, visited, u);
				}
			}
			stack.push(v);
		}

		private void dfsT(ArrayList<Integer> stronglyConnComp, int[] visited, int v) {
			stronglyConnComp.add(v);
			visited[v] = 1;
			for(int i = 0; i <= adjt[v].size() - 1; i++) {
				int u = adjt[v].get(i);
				if(visited[u] == 0) {
					dfsT(stronglyConnComp, visited, u);
				}
			}
			
		}
		*/
		
		// Aflam numarul de componente tare conexe aplicand Tarjan
		private ArrayList<ArrayList<Integer>> getResult() {
			ArrayList<ArrayList<Integer>> sol = new ArrayList<>();
			int index = 0;
			int[] idx = new int[n+1];
			int[] lowlink = new int[n+1];
			for(int i = 0; i <= n; i++) {
				idx[i] = -1;
				lowlink[i] = -1;
			}			
			Stack<Integer> stack = new Stack<Integer>();
			for(int v = 1; v <= n; v++) {
				// daca v nu a fost vizitat
				if(idx[v] == -1) {
					index = tarjan(sol, v, stack, lowlink, idx, index);
				}
			}
			return sol;
		}
		
		private int tarjan (ArrayList<ArrayList<Integer>> sol, int v, Stack<Integer> stack, int [] lowlink, int [] idx, int index) {
			idx[v] = index;
			lowlink[v] = index;
			index++;
			stack.push(v);
			for(int i = 0; i <= adj[v].size() - 1; i++) {
				int u = adj[v].get(i);
				if(idx[u] == -1) {
					index = tarjan(sol, u, stack, lowlink, idx, index);
					lowlink[v] = Integer.min(lowlink[v], lowlink[u]);
				} else {
					if(stack.search(u) != -1) {
						lowlink[v] = Integer.min(lowlink[v], idx[u]);
					}
				}
			}
			// daca v e radacina a unei CTC
			if(lowlink[v] == idx[v]) {
				ArrayList<Integer> stronglyConnComp = new ArrayList<Integer>();
				int u;
				do {
					u = stack.pop();
					stronglyConnComp.add(u);
				} while(u != v);
				sol.add(stronglyConnComp);
			}
			return index;
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
