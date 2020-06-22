import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    static class Result {
        int len;
        int[] subsequence;

        public Result() {
            len = 0;
            subsequence = null;
        }
    }

    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        int n, m;
        int[] v;
        int[] w;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                m = sc.nextInt();

                v = new int[n + 1]; // Adaugare element fictiv - indexare de
                                    // la 1.
                for (int i = 1; i <= n; i++) {
                    v[i] = sc.nextInt();
                }

                w = new int[m + 1]; // Adaugare element fictiv - indexare de
                                    // la 1.
                for (int i = 1; i <= m; i++) {
                    w[i] = sc.nextInt();
                }
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(Result result) {
            try {
                PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
                pw.printf("%d\n", result.len);
                if (result.subsequence != null) {
                    for (int i = 1; i <= result.len; i++) {
                        pw.printf("%d ", result.subsequence[i]);
                    }
                }
                pw.printf("\n");
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private Result getResult() {
            Result result = new Result();
            int [][] dp = new int[n+1][m+1];
            //initializam configuratia initiala cu 0
            //de asemenea, rezultatul pentru un sir vid este 0
            for(int i = 0; i <= n; i++)
            	dp[i][0] = 0;
            for(int j = 0; j <= m; j++)
            	dp[0][j] = 0;
            //se parcurg elementele din fiecare vector
            for(int i = 1; i <= n; i++) {
            	for(int j = 1; j <= m; j++) {
            		//daca avem un element comun, incrementam rezultatul de la pasul anterior(subsirul fara elementul curent)
            		if(v[i] == w[j])
            			dp[i][j] = 1 + dp[i-1][j-1];
            		//daca nu avem un element comun, pastram rezultatul maxim
            		//avem 2 posibilitati: 
            		//rezultatul pentru subsecventa pana la i in v si pana la j-1 in w
            		//rezultatul pentru subsecventa pana la i-1 in v si pana la j in w
            		else
            			dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
            	}
            }
            result.len = dp[n][m];
            result.subsequence = new int[result.len + 1];
            int i = n, j = m;
            int k = result.len;
            while(i != 0 && j != 0) {
            	if(v[i] == w[j]) {
            		result.subsequence[k--] = v[i];
            		i--;
            		j--;
            	}
            	//ma duc inapoi pe unde am rezulatul subproblemei mai mare
            	else if(dp[i-1][j] < dp[i][j-1])
            		j--;
            	else
            		i--;
            }
            // obtinem cel mai lung subsir comun intre v (de lungime n) si w (de lungime m).
            // si reprezentarea acestui subsir
            return result;
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
