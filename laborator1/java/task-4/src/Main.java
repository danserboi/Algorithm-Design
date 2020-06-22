import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int base;
		int exponent;
		int mod;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				base = sc.nextInt();
				exponent = sc.nextInt();
				mod = sc.nextInt();
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(long result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private long fastPow(int base, int exponent, int mod) {
			if(base == 0)
				return 0;
			if(exponent == 0)
				return 1;
			if(exponent % 2 == 1) {
				return (base % mod)*(fastPow(base, exponent -1, mod) % mod) % mod;
			}
			else {
				long f = (fastPow(base, exponent / 2, mod) % mod);
				return (f * f) % mod;
			}
		}

		public void solve() {
			readInput();
			long result = fastPow(base, exponent, mod);
			writeOutput(result);
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
