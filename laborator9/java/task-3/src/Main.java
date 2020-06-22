import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 105;
		public static final int MAX = 100005;
		
		int n;
		int d[][];
		int a[][];

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				d = new int[n + 1][n + 1];
				// O muchie (x, y, w) este reprezentata astfel in matricea ponderilor:
				//	a[x][y] = w;
				// Daca nu exista o muchie intre doua noduri x si y, in matricea
				// ponderilor se va afla valoarea 0:
				//	a[x][y] = 0;
				a = new int[n + 1][n + 1];
				for (int i = 1; i <= n; i++) {
					for (int j = 1; j <= n; j++) {
						a[i][j] = sc.nextInt();
					}
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput() {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(
								OUTPUT_FILE));
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i <= n; i++) {
					for (int j = 1; j <= n; j++) {
						sb.append(d[i][j]).append(' ');
					}
					sb.append('\n');
				}
				bw.write(sb.toString());
				bw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		// Gasim distantele minime intre oricare doua noduri, folosind
		// Roy-Floyd pe graful orientat cu n noduri, m arce stocat in matricea
		// ponderilor a (declarata mai sus).
		private void compute() {
			// Populam matricea distantelor d[][] :
			//	d[x][y] = distanta minima intre nodurile x si y, daca exista drum.
			//	d[x][y] = 0 daca nu exista drum intre x si y.
			//	d[x][x] = 0.
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					// drumul de la nodul i la nodul i are lungime 0 (prin conventie)
					if(i != j)
						// daca nu exista muchie intre o pereche de noduri x si y, distanta de la nodul x la nodul y din matricea ponderilor va fi 0
						if(a[i][j] == 0) {
							// completam cu infinit distanta de la i la j daca nu exista muchie
							d[i][j] = MAX;						
						}
						else {
							// daca exista muchie intre i si j completam distanta cu costul muchiei
							d[i][j] = a[i][j];
						}
				}
			}
			for(int k = 1; k <= n; k++) {
				for(int i = 1; i <= n; i++) {
					for(int j = 1; j <= n; j++) {
						d[i][j] = Integer.min(d[i][j], d[i][k] + d[k][j]);
					}
				}
			}
			// daca dupa aplicarea algoritmului nu se gaseste drum pentru o pereche de noduri x si y, se va considera distanta dintre ele egala cu 0 (se stocheaza in matricea distantelor valoarea 0)
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					if(d[i][j] == MAX) {
						d[i][j] = 0;
					}

				}
			}
	
		}

		public void solve() {
			readInput();
			compute();
			writeOutput();
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
