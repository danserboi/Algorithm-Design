import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        int n, S;
        int[] v;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                S = sc.nextInt();
                v = new int[n];
                for (int i = 0; i < n; i++) {
                    v[i] = sc.nextInt();
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

        private int getResult() {
        	//daca suma este negativa, nu putem folosi nicio moneda 
        	if(S <= 0)
        		return 0;
        	//numarul minim de monede ce poate fi folosit pentru a obtine suma S
        	return getResult(v, S, new int[S+1]);
        }
        //complexitatea temporala a acestei solutii este O(S*n) deoarece
        //avem in cel mai rau caz S apeluri recursive si la fiecare apel parcurgem cele n monede
        //complexitatea spatiala este O(S) deoarece avem S stack frame-uri in cel mai rau caz
        //si construim un vector de lungime S+1 pentru stocarea rezultatelor subproblemelor
        private int getResult(int[] monede, int sumaRamasa, int[] dp) {
        	//daca sumaRamasa e negativa, inseamna ca nu am putut obtine solutie
        	if(sumaRamasa < 0)
        		return -1;
        	//daca sumaRamasa e 0, inseamna ca am ajuns la solutie
        	if(sumaRamasa == 0)
        		return 0;
        	//daca deja am calculat aceasta problema, nu o mai calculam a doua oara
        	if(dp[sumaRamasa] != 0)
        		return dp[sumaRamasa];
        	int min = Integer.MAX_VALUE;
        	//recursiv abordam fiecare subproblema in parte
        	for(int moneda : monede) {
        		int rez = getResult(monede, sumaRamasa - moneda, dp);
        		//daca obtinem un rezultat viabil, actualizam minimul
        		if(rez >= 0 && rez < min)
        			min = 1 + rez;
        	}
        	//daca minimul a fost actualizat, inseamna ca acesta este minimul subproblemei actuale
        	if(min == Integer.MAX_VALUE)
        		dp[sumaRamasa] = -1;
        	else
        		dp[sumaRamasa] = min;
        	//returnam rezultatul acestei subprobleme
        	return dp[sumaRamasa];
        }
        //complexitatea temporala pentru varianta iterativa este O(S*n) deoarece
        //avem 2 for-uri imbricate cu S pasi, respectiv n pasi
        //complexitatea spatiala este O(S) deoarece construim un vector de lungime S+1 pentru stocarea rezultatelor subproblemelor
        private int iterativeResult() {
        	int max = S + 1;
        	int[] dp = new int[S+1];
        	//pentru fiecare subproblema, presupunem ca nu putem obtine un rezultat viabil
        	//in cel mai rau caz numarul de monede folosite este S
        	Arrays.fill(dp, max);
        	//pentru suma 0, rezultatul este 0
        	dp[0] = 0;
        	//cautam solutia minima = nr-ul minim dintre rezultatul fiecarei subprobleme(se incearca fiecare moneda)
        	//dp[i] = minj=0…n−1​ (dp(i−v[j]​)+1)
        	for(int i = 1; i <= S; i++) {
        		for(int j = 0; j < v.length; j++) {
        			//pentru a putea fi folosita, moneda trebuie sa fie mai mica decat suma disponibila
        			if(v[j] <= i) {
        				//calculam minimul problemei actuale
        				dp[i] = Math.min(dp[i], dp[i-v[j]] + 1);
        			}
        		}
        	}
        	//daca avem un numar de monede fezabil, inseamna ca am ajuns la rezultat
        	return dp[S] > S ? -1 : dp[S];
        }
        public void solve() {
            readInput();
            writeOutput(iterativeResult());
        }
    }

    public static void main(String[] args) {
        new Task().solve();
    }
}