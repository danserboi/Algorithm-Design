import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    static class Task {
        public static final String INPUT_FILE = "in";
        public static final String OUTPUT_FILE = "out";
        public static final int NMAX = 200005;
        public static final int MAX = 100005;
        int n;
        int m;

        public class Edge {
            public int node;
            public int cost;

            Edge(int _node, int _cost) {
                node = _node;
                cost = _cost;
            }

			@Override
			public String toString() {
				return "Edge [node=" + node + ", cost=" + cost + "]";
			}
            
        }
        
		class Node implements Comparable<Node> { 
		    public int node; 
		    public int cost; 
		  
		    public Node(int node, int cost) 
		    { 
		        this.node = node; 
		        this.cost = cost; 
		    }   

			@Override
			public int compareTo(Node node) {
		        if (this.cost < node.cost) 
		            return -1; 
		        if (this.cost > node.cost) 
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
                Scanner sc = new Scanner(new BufferedReader(new FileReader(
                                INPUT_FILE)));
                n = sc.nextInt();
                m = sc.nextInt();

                for (int i = 1; i <= n; i++)
                    adj[i] = new ArrayList<>();
                for (int i = 1; i <= m; i++) {
                    int x, y, w;
                    x = sc.nextInt();
                    y = sc.nextInt();
                    w = sc.nextInt();
                    adj[x].add(new Edge(y, w));
                    adj[y].add(new Edge(x, w));
                }
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(int result) {
            try {
                PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
                pw.printf("%d\n", result);
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Calculam costul minim al unui arbore de acoperire folosind algoritmul lui Prim.
        // Complexitatea temporala este O(|V|lg|V|+|E|lg|V|), adica O(|E|lg|V|).
        private int getResult() {
            int totalCost = 0;
            PriorityQueue<Node> pq = new PriorityQueue<Node>(n);
			// vector de parinti pentru fiecare nod
			int[] p = new int[n+1];
			// vector de distante
			int[] d = new int[n+1];
			// alegem ca sursa nodul 1
			int source = 1;
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
			//cat timp mai sunt noduri neadaugate
			while(!pq.isEmpty()) {
				//se selecteaza cel mai apropiat nod u
				int u = pq.remove().node;
				// il adaugam in multimea celor tratate
				settled[u] = 1;
				// optional, se adauga muchia care uneste u cu un nod din arborele principal 
				// MuchiiAMA = MuchiiAMA U {(u, p[u])};
				
				// parcurgem toti vecinii lui u
		        for (int i = 0; i < adj[u].size(); i++) { 
		            Edge v = adj[u].get(i); 
		            // pentru toate nodurile adiacente lui u se verifica daca se poate ajunge la ele cu un cost imbunatatit
		            if (settled[v.node] == 0 && v.cost < d[v.node]) {
		            	// scoatem nodul din coada de prioritate pentru a-l reintroduce cu valoarea actualizata
		            	pq.remove(new Node(v.node, d[v.node]));
		                // actualizam distanta si parintele
		            	d[v.node] = v.cost;
		            	p[v.node] = u;
		            	// introducem nodul cu valoarea actualizata
		            	pq.add(new Node(v.node, d[v.node])); 
		            } 
		        } 
			}
			// calculam costul total al muchilor arborelui de acoperire
			for(int i = 1; i <= n; i++) {
				totalCost += d[i];
			}
			// optional, MuchiiAMA = MuchiiAMA \ {(root, p[root])};
            return totalCost;
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
