import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		private final static long MOD = 1000000007;

		int n;
		char[] expr;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				String s = sc.next().trim();
				s = " " + s;
				expr = s.toCharArray();
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
		
		/*
		// VARIANTA FARA % MOD
		private long getResult() {
			// folosim folosim urmatoarele matrici de programare dinamica in care se vor
			// retine toate solutiile partiale:
			// prima matrice va retine numarul total de moduri in case se pot aseza
			// parantezele astfel inca rezultatul expresiei sa fie TRUE
			// a doua matrice va retine numarul total de moduri in care se pot aseza
			// parantezele astfel incat rezultatul expresiei sa fie FALSE
			long[][] T = new long[n + 1][n + 1];
			long[][] F = new long[n + 1][n + 1];
			// cazul de baza: daca caracterul este true, inseamna ca avem deja o
			// expresie(atom) evaluata ca true, altfel avem una evaluata ca false
			for (long i = 1; i <= n; i++) {
				if (expr[i] == 'T')
					T[i][i] = 1;
				if (expr[i] == 'F')
					F[i][i] = 1;
			}
			// tratam subexpresiile pornind de la cele mai mici ca lungime pana ajungem la
			// expresia de dimensiune maxima
			// deci vom itera dupa numarul de operatori inclusi in expresie
			// Atentie ! Toate calculele se fac raportat la operator, nu la operanzi. Deci
			// prescuratarea op se refera la operatori.
			for (long noOp = 1; noOp <= n / 2; noOp++) {
				// parcugem fiecare operator, stim ca ei sunt aflati pe pozitii pare in vector,
				// dat fiind formatul
				// practic calculam pentru fiecare subexpresie cu lungimea dorita numarul de
				// parantezari a.i. rez sa fie true/respectiv false
				for (long stOp = 2; stOp + 2 * (noOp - 1) <= n - 1; stOp += 2) {
					long lastOp = stOp + 2 * (noOp - 1);
					// alegem unde sa facem parantezarea in cadrul subexpresiei actuale si calculam
					// cate rezultate de true avem in cazul acesta, respectiv de false
					for (long currOp = stOp; currOp <= lastOp; currOp += 2) {

						long totalStCurr = T[stOp - 1][currOp - 1] + F[stOp - 1][currOp - 1];

						long totalCurrLast = T[currOp + 1][lastOp + 1]
								+ F[currOp + 1][lastOp + 1];

						if (expr[currOp] == '&') {
							T[stOp - 1][lastOp + 1] += T[stOp - 1][currOp - 1]
									* T[currOp + 1][lastOp + 1];
							F[stOp - 1][lastOp + 1] += (totalStCurr * totalCurrLast
									- T[stOp - 1][currOp - 1] * T[currOp + 1][lastOp + 1]);
						}
						if (expr[currOp] == '|') {
							T[stOp - 1][lastOp + 1] += (totalStCurr * totalCurrLast
									- F[stOp - 1][currOp - 1] * F[currOp + 1][lastOp + 1]);
							F[stOp - 1][lastOp + 1] += F[stOp - 1][currOp - 1]
									* F[currOp + 1][lastOp + 1];
						}
						if (expr[currOp] == '^') {
							T[stOp - 1][lastOp + 1] += F[stOp - 1][currOp - 1]
									* T[currOp + 1][lastOp + 1]
									+ T[stOp - 1][currOp - 1] * F[currOp + 1][lastOp + 1];
							F[stOp - 1][lastOp + 1] += T[stOp - 1][currOp - 1]
									* T[currOp + 1][lastOp + 1]
									+ F[stOp - 1][currOp - 1] * F[currOp + 1][lastOp + 1];
						}
					}
				}
			}
			return T[1][n];
		}
		*/
		
		// Calculam numarul de moduri in care se pot aseza
		// parantezele astfel incat rezultatul expresiei sa fie TRUE.
		// Numarul de moduri se va calcula modulo MOD (1000000007).
		private long getResult() {
			// folosim folosim urmatoarele matrici de programare dinamica in care se vor
			// retine toate solutiile partiale:
			// prima matrice va retine numarul total de moduri in case se pot aseza
			// parantezele astfel inca rezultatul expresiei sa fie TRUE
			// a doua matrice va retine numarul total de moduri in care se pot aseza
			// parantezele astfel incat rezultatul expresiei sa fie FALSE
			long[][] T = new long[n + 1][n + 1];
			long[][] F = new long[n + 1][n + 1];
			// cazul de baza: daca caracterul este true, inseamna ca avem deja o
			// expresie(atom) evaluata ca true, altfel avem una evaluata ca false
			for (int i = 1; i <= n; i++) {
				if (expr[i] == 'T')
					T[i][i] = 1;
				if (expr[i] == 'F')
					F[i][i] = 1;
			}
			// tratam subexpresiile pornind de la cele mai mici ca lungime pana ajungem la
			// expresia de dimensiune maxima
			// deci vom itera dupa numarul de operatori inclusi in expresie
			// Atentie ! Toate calculele se fac raportat la operator, nu la operanzi. Deci
			// prescuratarea op se refera la operatori.
			for (int noOp = 1; noOp <= n / 2; noOp++) {
				// parcugem fiecare operator, stim ca ei sunt aflati pe pozitii pare in vector,
				// dat fiind formatul
				// practic calculam pentru fiecare subexpresie cu lungimea dorita numarul de
				// parantezari a.i. rez sa fie true/respectiv false
				for (int stOp = 2; stOp + 2 * (noOp - 1) <= n - 1; stOp += 2) {
					int lastOp = stOp + 2 * (noOp - 1);
					// alegem unde sa facem parantezarea in cadrul subexpresiei actuale si calculam
					// cate rezultate de true avem in cazul acesta, respectiv de false
					for (int currOp = stOp; currOp <= lastOp; currOp += 2) {

						long totalStCurr = (T[stOp - 1][currOp - 1] % MOD + F[stOp - 1][currOp - 1] % MOD) % MOD;

						long totalCurrLast = (T[currOp + 1][lastOp + 1] % MOD + F[currOp + 1][lastOp + 1] % MOD) % MOD;

						if (expr[currOp] == '&') {
							T[stOp - 1][lastOp + 1] = (T[stOp - 1][lastOp + 1] % MOD + ((T[stOp - 1][currOp - 1] % MOD)
									* (T[currOp + 1][lastOp + 1] % MOD)) % MOD) % MOD;
							F[stOp - 1][lastOp + 1] = ((F[stOp - 1][lastOp + 1] % MOD) + (((totalStCurr % MOD)* (totalCurrLast % MOD))%MOD
									- ((T[stOp - 1] [currOp - 1] % MOD)* (T[currOp + 1][lastOp + 1] % MOD))%MOD + MOD) % MOD)%MOD;
						}
						if (expr[currOp] == '|') {
							T[stOp - 1][lastOp + 1] = ((T[stOp - 1][lastOp + 1] % MOD) + (((totalStCurr % MOD) * (totalCurrLast % MOD)) % MOD
									- ((F[stOp - 1][currOp - 1] % MOD)* (F[currOp + 1][lastOp + 1] % MOD)) % MOD + MOD)%MOD)%MOD;
							F[stOp - 1][lastOp + 1] = ((F[stOp - 1][lastOp + 1] % MOD)+ ((F[stOp - 1][currOp - 1] % MOD)
									* (F[currOp + 1][lastOp + 1] % MOD)) % MOD) % MOD;
						}
						if (expr[currOp] == '^') {
							T[stOp - 1][lastOp + 1] = (((T[stOp - 1][lastOp + 1] % MOD)+ ((F[stOp - 1][currOp - 1] % MOD)
									* (T[currOp + 1][lastOp + 1] % MOD)) % MOD) % MOD
									+ ((T[stOp - 1][currOp - 1] % MOD) * (F[currOp + 1][lastOp + 1] % MOD)) % MOD) % MOD;
							F[stOp - 1][lastOp + 1] = (((F[stOp - 1][lastOp + 1] % MOD )+ ((T[stOp - 1][currOp - 1] % MOD)
									* (T[currOp + 1][lastOp + 1] % MOD)) % MOD) % MOD
									+ ((F[stOp - 1][currOp - 1] % MOD) * (F[currOp + 1][lastOp + 1] % MOD)) % MOD) % MOD;
						}
					}
				}
			} 
			return T[1][n];
		}
		

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
