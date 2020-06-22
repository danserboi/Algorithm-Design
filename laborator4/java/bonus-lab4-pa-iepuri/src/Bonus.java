import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Bonus {
	static class Task {
		public final static String INPUT_FILE = "iepuri.in";
		public final static String OUTPUT_FILE = "iepuri.out";

		int t, x[], y[], z[], a[], b[], c[], n[];

		private final static int MOD = 666013;
		private final static int SIZE = 3;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				t = sc.nextInt();
				x = new int[t];
				y = new int[t];
				z = new int[t];
				a = new int[t];
				b = new int[t];
				c = new int[t];
				n = new int[t];
				for(int i = 0; i < t; i++) {
					x[i] = sc.nextInt();
					y[i] = sc.nextInt();
					z[i] = sc.nextInt();
					a[i] = sc.nextInt();
					b[i] = sc.nextInt();
					c[i] = sc.nextInt();
					n[i] = sc.nextInt();	
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int[] result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				for(int i = 0; i < t; i++) {
					pw.printf("%d\n", result[i]);
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		// VARIANTA % MOD
		private int[] getResult() {
			// vom rezolva problema folosind exponentiere logaritmica pe matrice
			int[] result = new int[t];
			for(int i = 0; i < t; i++) {
				int[][] C = { { a[i], b[i], c[i] }, { 1, 0, 0 }, { 0, 1, 0 } };
				int[][] aux = { { a[i], b[i], c[i] }, { 1, 0, 0 }, { 0, 1, 0 } };
				power(C, n[i] - 2, aux);
				result[i] = ((result[i] % MOD) + ((C[0][0] % MOD) * (z[i] % MOD)) % MOD) % MOD;
				result[i] = ((result[i] % MOD) + ((C[0][1] % MOD) * (y[i] % MOD)) % MOD) % MOD;
				result[i] = ((result[i] % MOD) + ((C[0][2] % MOD) * (x[i] % MOD)) % MOD) % MOD;	
			}
			return result;
		}
		
		// Inmultim 2 matrici
		void mul(int a[][], int b[][]) {
			int res[][] = new int[SIZE][SIZE];

			for (int i = 0; i < SIZE; i++)
				for (int j = 0; j < SIZE; j++)
					for (int k = 0; k < SIZE; k++) {
						res[i][j] = (res[i][j] % MOD) + ((a[i][k] % MOD) * (b[k][j] % MOD)) % MOD;
					}
			for (int i = 0; i < SIZE; i++)
				for (int j = 0; j < SIZE; j++)
					a[i][j] = res[i][j];
		}

		// Calculam aux^n retinand rezultatul in res
		void power(int res[][], int n, int[][] aux) {
			if (n == 1)
				return;

			power(res, n / 2, aux);

			mul(res, res);

			if (n % 2 == 1)
				mul(res, aux);

			return;
		}
		/*
		//varianta normala
		private int getResult() {
			// vom rezolva problema folosind exponentiere logaritmica pe matrice
			int[][] C = { { a, b, c }, { 1, 0, 0 }, { 0, 1, 0 } };
			int[][] aux = { { a, b, c }, { 1, 0, 0 }, { 0, 1, 0 } };
			power(C, n - 2, aux);
			return C[0][0] * z + C[0][1] * y + C[0][2] * x;
		}

		// Inmultim 2 matrici
		void mul(int a[][], int b[][]) {
			int res[][] = new int[SIZE][SIZE];

			for (int i = 0; i < SIZE; i++)
				for (int j = 0; j < SIZE; j++)
					for (int k = 0; k < SIZE; k++) {
						res[i][j] += a[i][k] * b[k][j];
					}
			for (int i = 0; i < SIZE; i++)
				for (int j = 0; j < SIZE; j++)
					a[i][j] = res[i][j];
		}

		// Calculam aux^n retinand rezultatul in res
		void power(int res[][], int n, int[][] aux) {
			if (n == 1)
				return;

			power(res, n / 2, aux);

			mul(res, res);

			if (n % 2 == 1)
				mul(res, aux);

			return;
		}
		*/
		public void solve() {
			readInput();
			writeOutput(getResult());
		}
	}

	public static void main(String[] args) {
		Task t = new Task();
		t.solve();
	}
}
