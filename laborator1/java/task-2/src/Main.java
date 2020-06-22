import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		double n;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextDouble();
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(double x) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%.4f\n", x);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		// Calculeaza sqrt(n) cu o precizie de 10^-3.
		private double computeSqrt() {
			double left = 0.0;
			double right;
			// daca n este intre 0 si 1, capatul drept trebuie sa fie n + 1
			// altfel vom obtine intotdeauna un numar mai mic decat n
			if(n >= 1)
				right = n;
			else
				right = n + 1;
			double mid = 0.0;
			while(Math.abs(mid*mid - n) > 0.001) {
				mid = (left + right)/2;
				if(mid*mid < n) {
					left = mid;
				}
				else {
					right = mid;
				}
			}
			System.out.println(mid);
			// Precizie de 10^(-x) inseamna |valoarea_ta - valoarea_reala| < 10^(-x).
			return mid;
		}

		public void solve() {
			readInput();
			writeOutput(computeSqrt());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
