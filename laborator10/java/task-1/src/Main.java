import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Main {
    static class Task {
        public static final String INPUT_FILE = "in";
        public static final String OUTPUT_FILE = "out";
        public static final int NMAX = 200005;

        int n;
        int m;
        int[] parent;
        int[] size;

        public class Edge implements Comparable<Edge>{
            public int node1;
            public int node2;
            public int cost;

            Edge(int _node1, int _node2, int _cost) {
                node1 = _node1;
                node2 = _node2;
                cost = _cost;
            }

			@Override
			public int compareTo(Edge arg) {
				return this.cost - arg.cost;
			}
            
        }

        @SuppressWarnings("unchecked")
        ArrayList<Edge> edges = new ArrayList<>();

        private void readInput() {
            try {
                Scanner sc = new Scanner(new BufferedReader(new FileReader(
                                INPUT_FILE)));
                n = sc.nextInt();
                m = sc.nextInt();

                for (int i = 1; i <= m; i++) {
                    int x, y, w;
                    x = sc.nextInt();
                    y = sc.nextInt();
                    w = sc.nextInt();
                    edges.add(new Edge(x, y, w));
                }
                parent = new int[n + 1];
                size = new int[n + 1];
                for (int i = 1; i <= n; i++) {
                    parent[i] = i;
                    size[i] = 1;
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

        private int findRoot(int node) {
            if (node == parent[node]) {
                return node;
            }
            return parent[node] = findRoot(parent[node]);
        }

        private void mergeForests(int root1, int root2) {
            if (size[root1] <= size[root2]) {
                size[root2] += size[root1];
                parent[root1] = root2;
            } else {
                size[root1] += size[root2];
                parent[root2] = root1;
            }
        }

        // Calculam costul minim al unui arbore de acoperire folosind algoritmul lui Kruskal.
        // Complexitatea temporala este: O(|V|) + O(|E|log|E|) + O(|E|log|E|) = O(|E|log|E|).
        // Cum |E| ~ |V|^2, si O(log(|V|^2)) = O(2*log(|V|)) = O(log(|V|)), 
        // rezulta o complexitate O(|E|log|V|). 
        private int getResult() {
        	int totalCost = 0;
        	// sortam muchiile in ordinea crescatoare a costului
            Collections.sort(edges);
        	int k = 0;
            for(int i = 0; i < edges.size(); i++) {
        		Edge edge = edges.get(i);
        		int root1 = findRoot(edge.node1);
        		int root2 = findRoot(edge.node2);
        		if(root1 != root2) {
        			// optional, putem adauga muchia in multimea muchilor arborelui minim de acoperire
        			// MuchiiAMA = MuchiiAMA U {(u, v)};        			
        			totalCost += edge.cost;
        			// unim subarborii corespunzatori celor 2 noduri
        			mergeForests(root1, root2);
        			k++;
        			// daca avem toate muchiile arborelui minim de acoperire, ne oprim
        			if(k == n - 1)
        				break;
        		}
        	}
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
