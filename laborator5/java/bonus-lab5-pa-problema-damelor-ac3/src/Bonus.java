import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
//TODO AC3 
public class Bonus {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int n;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<Integer> result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				for (int i = 1; i <= n; i++) {
					pw.printf("%d%c", result.get(i), i == n ? '\n' : ' ');
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		// aceasta functie verifica validitatea unei eventuale asezari pentru dama
		// avand in vedere ca vom completa tabla cu elemente, coloana cu coloana, de la
		// stanga la dreapta
		// vom verifica conditiile doar pentru partea deja completata si anume cea din
		// stanga
		boolean checkConds(int board[], int row, int col) {
			// verificam daca mai sunt elemente puse pe acest rand
			for (int i = 1; i <= col; i++)
				if (board[i] == row)
					return false;
			// verificam daca mai sunt elemente puse pe diagonala superioara
			for (int i = row, j = col; i >= 1 && j >= 1; i--, j--)
				if (board[j] == i)
					return false;
			// verificam daca mai sunt elemente puse pe diagonala inferioara
			for (int i = row, j = col; j >= 1 && i <= n; i++, j--)
				if (board[j] == i)
					return false;
			// daca toate conditiile sunt indeplinite, asezarea este una valida
			return true;
		}

		
		boolean backtrack(int board[], int col) {
			// ne oprim atunci cand punem n regine pe tabla(=ajungem la ultima coloana) 
			if (col == n + 1) {
				return true;
			}
			// incercam sa punem regina pe fiecare rand 
			for (int i = 1; i <= n; i++) {
				// verificam daca asezarea e valida
				if (checkConds(board, i, col)) {
					// atunci punem regina pe tabla
					board[col] = i;
					// incercam sa completam si celelalte randuri
					if (backtrack(board, col + 1) == true)
						return true;
					// daca nu avem solutie, stergem randul pus(completam cu 0, pe care nu-l luam in seama deoarece pornim indexarea de la 1)
					board[col] = 0; // BACKTRACK
				}
			}
			return false;
		}

		private ArrayList<Integer> getResult() {
			ArrayList<Integer> sol = new ArrayList<Integer>();
			int[] board = new int[n + 1];
			backtrack(board, 1);
			sol.add(0);
			for (int i = 1; i <= n; i++)
				sol.add(board[i]);
			return sol;
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
