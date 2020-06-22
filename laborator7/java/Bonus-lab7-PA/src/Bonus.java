import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Bonus {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		static int n;
		int[][] matrix;
		int sourceI;
		int sourceJ;
		// TODO: define members

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				// TODO: read input
				n = sc.nextInt();
				matrix = new int[n+1][n+1];
				for(int i = 1; i <= n; i++) {
					for(int j = 1; j <= n; j++) {
						matrix[i][j] = sc.nextInt();
					}
				}
				sourceI = sc.nextInt();
				sourceJ = sc.nextInt();
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(String result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(OUTPUT_FILE)));
				pw.print(result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private String getResult() {
			StringBuilder sb = new StringBuilder();
			// 0 daca nodul a fost vizitat, 1 altfel
			int[][] visited = new int[n+1][n+1];
			visited[sourceI][sourceJ] = 1;
			Queue<Node> q = new LinkedList<Node>();
			// distanta de la sursa la nod este 0 iar parintele nu exista
			q.add(new Node(sourceI, sourceJ, 0, null));
			while(!q.isEmpty()) {
				Node u = q.peek();
				// vecinii unui nod sunt 4:
				// (i-1, j), (i+1,j), (i, j-1), (i, j+1)
				// ii luam doar pe cei valizi
				LinkedList<Node> succ = getNodes(u, visited);
				while(!succ.isEmpty()) {
					Node v = succ.remove();
					if(matrix[v.i][v.j] == 2) {
						int dist = v.dist;
						while(v != null) {
							sb.insert(0, "(" + v.i + "," + v.j + ")\n");
							v = v.parent;
						}
						sb.insert(0, dist+"\n");
						return sb.toString();
					}
					visited[v.i][v.j] = 1;
					q.add(v);
				}
				visited[u.i][u.j] = 1;
				q.poll();
			}
			// nu exista
			return null;
		}

		// verifica daca nodul se afla in matrice si poate fi accesat
		private boolean isValid(int i, int j, int[][] visited) 
		{ 
		    return (i >= 1) && (i <= n) && (j >= 1) && (j <= n) 
		    		&& (matrix[i][j] == 0 || matrix[i][j] == 2)
		    		&& (visited[i][j] == 0); 
		} 
		
		private LinkedList<Node> getNodes(Node u, int[][] visited) {
			LinkedList<Node> nodes = new LinkedList<Node>();
			if(isValid(u.i-1, u.j, visited)) {
				nodes.add(new Node(u.i-1, u.j, u.dist + 1, u));
			}
			if(isValid(u.i+1, u.j, visited)) {
				nodes.add(new Node(u.i+1, u.j, u.dist + 1, u));
			}
			if(isValid(u.i, u.j-1, visited)) {
				nodes.add(new Node(u.i, u.j-1, u.dist + 1, u));
			}
			if(isValid(u.i, u.j+1, visited)) {
				nodes.add(new Node(u.i, u.j+1, u.dist + 1, u));
			}
			return nodes;
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

class Node{
	int i;
	int j;
	int dist;
	Node parent;
	public Node(int i, int j, int dist, Node parent) {
		super();
		this.i = i;
		this.j = j;
		this.dist = dist;
		this.parent = parent;
	}
}