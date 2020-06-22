import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 50005;
		public static final int MAX = 100005;
		
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
		
		class Node implements Comparable { 
		    public int node; 
		    public int cost; 
		  
		    public Node(int node, int cost) 
		    { 
		        this.node = node; 
		        this.cost = cost; 
		    }   

			@Override
			public int compareTo(Object node) {
		        if (this.cost < ((Main.Task.Node)node).cost) 
		            return -1; 
		        if (this.cost > ((Main.Task.Node)node).cost) 
		            return 1; 
		        return 0; 
			}

			@Override
			public String toString() {
				return "Node [node=" + node + ", cost=" + cost + "]";
			} 
			
		} 
		@SuppressWarnings("unchecked")
		ArrayList<Edge> adj[] = new ArrayList[NMAX];

		private void readInput() {
			try {
				BufferedReader br = new BufferedReader(new FileReader(
								INPUT_FILE));
				String line = br.readLine();
				String[] fields = line.split(" ");
				n = Integer.parseInt(fields[0]);
				m = Integer.parseInt(fields[1]);
				source = Integer.parseInt(fields[2]);

				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y, w;
					line = br.readLine();
					fields = line.split(" ");
					x = Integer.parseInt(fields[0]);
					y = Integer.parseInt(fields[1]);
					w = Integer.parseInt(fields[2]);
					adj[x].add(new Edge(y, w));
				}
				br.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int[] result) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(
								OUTPUT_FILE));
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i <= n; i++) {
					sb.append(result[i]).append(' ');
				}
				sb.append('\n');
				bw.write(sb.toString());
				bw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		// Algoritmul lui Dijkstra cu 2 implementari pt coada de prioritate: heap binar si array
		
		// varianta cu heap binar
		// Complexitatea obținută este O(|E|lg|V|) pentru un graf conex.
		// Funcția extrage_min se va executa în timp O(lg|V|); factualizează(Q) se va executa tot în timp O(lg|V|)
		// (stergere nod cu valoarea veche si adaugarea nod cu valoarea noua). 
		private int[] getResult() {
			PriorityQueue<Node> pq = new PriorityQueue<Node>(n);
			// vector de parinti pentru fiecare nod
			int[] p = new int[n+1];
			// vector cu estimarea distantei de la sursa la nod
			int[] d = new int[n+1];
			// distanta initiala catre orice nod diferit de sursa este infinit
			for(int i = 1; i <= n; i++) {
				if(i != source) {
					d[i] = MAX;
				} else {
					// distanta pana la sursa este 0
					d[source] = 0;
					// adaugam sursa in coada de prioritate
					pq.add(new Node(source, 0));					
				}
			}
			// vectorul spune daca un nod a fost sau nu tratat
			int[] settled = new int[n+1];
			// cat timp mai avem noduri de tratat
			while(!pq.isEmpty()) {
				// scoatem nodul cu distanta minima
				int u = pq.remove().node;
				// il adaugam in multimea celor tratate
				settled[u] = 1;
				// parcurgem toti vecinii lui u
		        for (int i = 0; i < adj[u].size(); i++) { 
		            Edge v = adj[u].get(i); 
		            // incercam sa relaxam muchiile
		            // daca drumul de la sursa la nod prin u este mai mic decat cel curent
		            if (settled[v.node] == 0 && d[u] + v.cost < d[v.node]) {
		            	// scoatem nodul din coada de prioritate pentru a-l reintroduce cu valoarea actualizata
		            	pq.remove(new Node(v.node, d[v.node]));
		                // actualizam distanta si parintele
		            	d[v.node] = d[u] + v.cost;
		            	p[v.node] = u;
		            	// introducem nodul cu valoarea actualizata
		            	pq.add(new Node(v.node, d[v.node])); 
		            } 
		        }
			}
			// daca nu avem drum de la sursa la nod, consideram distanta -1
			for(int i = 1; i <= n; i++) {
				if(d[i] == MAX) {
					d[i] = -1;
				}
			}
			return d;
		}
		
		/*
		// varianta cu array
		// Complexitatea algoritmului este O(|V|^2+|E|) în cazul în care coada cu priorități 
		// este implementata ca o căutare liniara. 
		// În acest caz funcția extrage_min se executa în timp O(|V|), 
		// iar actualizează(Q) in timp O(1). 
		private int[] getResult() {
			// vector de parinti pentru fiecare nod
			int[] p = new int[n+1];
			// vector cu estimarea distantei de la sursa la nod
			int[] d = new int[n+1];
			// distanta initiala catre orice nod diferit de sursa este infinit
			for(int i = 1; i <= n; i++) {
				if(i != source) {
					d[i] = MAX;
				} else {
					// distanta pana la sursa este 0
					d[source] = 0;
				}
			}
			// vectorul spune daca un nod a fost sau nu tratat
			int[] settled = new int[n+1];
			settled[0] = 1;
			// cat timp mai avem noduri de tratat
			int j = 1;
			while(j <= n) {
				// luam nodul cu distanta minima
				int u = getMin(d, settled);
				// il adaugam in multimea celor tratate
				settled[u] = 1;
				// parcurgem toti vecinii lui u
		        for (int i = 0; i < adj[u].size(); i++) { 
		            Edge v = adj[u].get(i); 
		            // incercam sa relaxam muchiile
		            // daca drumul de la sursa la nod prin u este mai mic decat cel curent
		            if (settled[v.node] == 0 &&  d[u] + v.cost < d[v.node]) {
		                // actualizam distanta si parintele
		            	d[v.node] = d[u] + v.cost;
		            	p[v.node] = u;
		            } 
		        }
		        j++;
			}
			// daca nu avem drum de la sursa la nod, consideram distanta -1
			for(int i = 1; i <= n; i++) {
				if(d[i] == MAX) {
					d[i] = -1;
				}
			}
			return d;
		}
		
		// returneaza nodul cu distanta minima dintre cele netratate
		public static int getMin(int[] inputArray, int[] settled){ 
		    int minValue = Integer.MAX_VALUE, pos = 0; 
		    for(int i=1;i<inputArray.length;i++){ 
		    	if(inputArray[i] < minValue && settled[i] == 0){ 
		    		minValue = inputArray[i];
		    		pos = i;
		    	} 
		    } 
		    return pos; 
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
