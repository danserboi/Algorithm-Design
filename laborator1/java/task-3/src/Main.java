import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int n, x, y;
		static int i = 0;
		static int result = 0;
		
		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				x = sc.nextInt();
				y = sc.nextInt();
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int answer) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", answer);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		// Calculam valoarea de pe pozitia (x, y)
		// din matricea de dimensiune 2^N * 2^N.
		private int getAnswer(int n, int x, int y) {
			return aux(n, 1, (int)Math.pow(2, n), 1, (int)Math.pow(2, n), x, y);
		}
		//TIMP EXPONENTIAL
		/*
		private void aux(int n, int startX, int stopX, int startY, int stopY, int x, int y) {
			if(n == 0) {
				i++;
				if(stopX == x && stopY == y)
					result = i;
			}
			if(n > 0 ) {
				aux(n-1, startX, (startX + stopX)/ 2, startY, (startY + stopY) / 2, x, y);
				aux(n-1, (startX + stopX) / 2 + 1, stopX, startY, (startY + stopY) / 2, x, y);
				aux(n-1, startX, (startX + stopX) / 2, (startY + stopY) / 2 + 1, stopY, x, y);
				aux(n-1, (startX + stopX) / 2 + 1, stopX, (startY + stopY) / 2 + 1, stopY, x, y);
			}
		}
		*/
		private int aux(int n, int startX, int stopX, int startY, int stopY, int x, int y) {
			
			if(n == 0) {
				return 1;
			}
			
			if(startX <= x && x <= ((startX + stopX)/ 2) && startY <= y && y <= ((startY + stopY) / 2))
				return aux(n-1, startX, (startX + stopX)/ 2, startY, (startY + stopY) / 2, x, y);
			else if(((startX + stopX) / 2 + 1) <= x && x <= stopX && startY <= y && y <= ((startY + stopY) / 2)) {
				return (int)Math.pow(2, 2*n-2) + aux(n-1, ((startX + stopX) / 2 + 1), stopX, startY, ((startY + stopY) / 2), x, y);
			}
			else if(startX <= x && x <= ((startX + stopX) / 2) && ((startY + stopY) / 2 + 1) <=y && y <= stopY) {
				return (int)(2*Math.pow(2, 2*n-2)) + aux(n-1, startX, (startX + stopX) / 2, (startY + stopY) / 2 + 1, stopY, x, y);
			}
			else {
				return (int)(3*Math.pow(2, 2*n-2)) + aux(n-1, (startX + stopX) / 2 + 1, stopX, (startY + stopY) / 2 + 1, stopY, x, y);
			}
			
		}
		/*
		private void matrixCalc(int n, int startX, int stopX, int startY, int stopY) {
			if(n == 0) {
				i++;
				System.out.printf("Numarul %d are coordonatele (%d, %d)\n", i, stopX, stopY);
				return;
			}

			matrixCalc(n-1, startX, (startX + stopX)/ 2, startY, (startY + stopY) / 2);
			matrixCalc(n-1, (startX + stopX) / 2 + 1, stopX, startY, (startY + stopY) / 2);
			matrixCalc(n-1, startX, (startX + stopX) / 2, (startY + stopY) / 2 + 1, stopY);
			matrixCalc(n-1, (startX + stopX) / 2 + 1, stopX, (startY + stopY) / 2 + 1, stopY);
		}
		*/
		public void solve() {
			readInput();
			writeOutput(getAnswer(n, y, x));
		}
	}

	public static void main(String[] args) {

		Task t = new Task();
		t.solve();
		//t.matrixCalc(3, 1, 8, 1, 8);
		//System.out.println(t.getAnswer(2, 3, 3));
	}
}
