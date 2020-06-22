import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
				for (int i = 0; i < result.size(); i++) {
					pw.printf("%d ", result.get(i));
				}
				pw.printf("\n");
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		// Sortam topologic graful stocat cu liste de adiacenta in adj.
		// realizand parcurgerea DFS pentru a determina timpii de descoperire si finalizare
		// dupa care sortam descrescator in functie de timpul de finalizare
		// nodurile sunt indexate de la 1 la n 
		private ArrayList<Integer> getResult() {
			// retinem parintele fiecarui nod
			// parintele e un numar intre 1 si n daca exista, altfel e 0
			int parent[] = new int[n+1];
			// aici retinem timpul de descoperire
			int d[] = new int[n + 1];
			// aici retinem timpul de finalizare
			int f[] = new int[n + 1];
			// tinem minte daca un nod a mai fost sau nu tratat
			// 1 daca nodul a fost vizitat, 0 altfel
			int visited[] = new int[n + 1];
			int timp = 0;
			for(int i = 1; i <= n; i++) {
				if(visited[i] == 0) {
					timp = explorare(i, parent, d, f, visited, timp);
				}
			}
			// cream un vector de Node (contine nodurile cu timpul de finalizare)
			// pe care il sortam descrescator dupa timpul de finalizare
			// pentru a obtine sortarea topologica
			Node[] sortedNodes = new Node[n];
			for(int i = 1; i <= n; i++) {
				sortedNodes[i-1] = new Node(i, f[i]);
			}
			Arrays.sort(sortedNodes);
			// adaugam in lista nodurile sortate topologic
			ArrayList<Integer> topsort = new ArrayList<>();
			for(int i = 0; i < n; i++) {
				topsort.add(sortedNodes[i].nodeNo);
			}
			return topsort;
		}
		
		private int explorare(int u, int[] parent, int[] d, int[] f, int[] visited, int timp) {
			d[u] = timp++;
			visited[u] = 1;
			for(int succesor : adj[u]) {
				if(visited[succesor] == 0) {
					parent[succesor] = u;
					timp = explorare(succesor, parent, d, f, visited, timp);
				}
			}
			f[u] = timp++;
			return timp;
		}
		
		/*
		// Varianta care implementeaza algoritmul lui Kahn pentru sortare topologica
		private ArrayList<Integer> getResult() {
			// lista va contine elementele sortate
			ArrayList<Integer> topsort = new ArrayList<>();
			int[] inEdges = new int[n+1];
			for(int i = 1; i<= n; i++) {
				for(int node : adj[i]) {
					inEdges[node]++;
				}
			}
			// initializam stiva cu nodurile in care nu intra nicio muchie
			Stack <Integer> stack = new Stack<Integer>();
			for(int i = 1; i <= n; i++) {
				if(inEdges[i] == 0) {
					stack.push(i);
				}
			}
			// cat timp mai am noduri de procesat
			while(!stack.isEmpty()) {
				System.out.println("S: " + stack);
				System.out.println("L: " + topsort);
				// se scoate un nod de pe stiva si se adauga in lista finala
				int u = stack.pop();
				// System.out.println(u);
				topsort.add(u);
				// pentru toti vecinii
				for(int i = 0; i < adj[u].size(); i++) {
					int v = adj[u].get(i);
					// se sterge muchia u-v si se actualizeaza nr de muchii care intra
					adj[u].remove((Integer)v);
					inEdges[v]--;
					i--;
					// daca nu mai exista muchii care sa intre in nod, nod-ul se pune pe stiva
					if(inEdges[v] == 0) {
						stack.push(v);
					}
				}
			}
			for(int i = 1; i <= n; i++) {
				if(adj[i].size() != 0) {
					System.err.println("graf ciclic");
				}
			}
			return topsort;
		}
		*/
		public void solve() {
			readInput();
			writeOutput(getResult());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}

class Node implements Comparable<Node>{
	int nodeNo;
	int fTime;
	public Node(int nodeNo, int fTime) {
		super();
		this.nodeNo = nodeNo;
		this.fTime = fTime;
	}
	@Override
	public int compareTo(Node arg) {
		return arg.fTime - this.fTime;
	}
	@Override
	public String toString() {
		return "Node [nodeNo=" + nodeNo + ", fTime=" + fTime + "]";
	}
	
}