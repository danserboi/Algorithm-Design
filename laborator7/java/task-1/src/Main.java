import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 100005; // 10^5

		int n;
		int m;

		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];

		int source;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();
				source = sc.nextInt();

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

		private void writeOutput(int[] result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				for (int i = 1; i <= n; i++) {
					pw.printf("%d%c", result[i], i == n ? '\n' : ' ');
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int[] getResult() {
			int parent[] = new int[n+1];
			int d[] = new int[n + 1];
			// 1 daca nodul a fost vizitat, 0 altfel
			int visited[] = new int[n + 1];
			// este optional, dar am ales sa calculez si parintele
			// e un numar intre 1 si n daca exista, altfel e -1
			for (int i = 0; i <= n; i++) parent[i] = -1;
			// BFS contruieste in d valorile d[i] = 
			// numarul minim de muchii de parcurs de la nodul source la nodul i.
			// d[x] = -1 daca nu exista drum de la source la x.
			for (int i = 0; i <= n; i++) d[i] = -1;
			d[source] = 0;
			visited[source] = 1;
			Queue<Integer> q = new LinkedList<Integer>();
			q.add(source);
			while(!q.isEmpty()) {
				Integer currNode = q.peek();
				for(Integer succesor : adj[currNode]) {
					if(visited[succesor] == 0) {
						d[succesor] = d[currNode] + 1;
						parent[succesor] = currNode;
						visited[succesor] = 1;
						q.add(succesor);
					}
				}
				q.poll();
			}
			return d;
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
