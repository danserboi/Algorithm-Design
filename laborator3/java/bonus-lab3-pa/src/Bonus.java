import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Bonus {
    static class Task {
        public final static String INPUT_FILE = "custi.in";
        public final static String OUTPUT_FILE = "custi.out";

        int n;
        int[][] matrix;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                matrix = new int[n][n];
                for (int i = 0; i < n; i++) {
                    for(int j = 0; j < n; j++)
                    	matrix[i][j] = sc.nextInt();
                }
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(int[] result) {
            try {
                PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
                for(int i = 0; i < n; i++)
                	pw.printf("%d\n", result[i]);
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private int[] getResult() {
        	//in result tinem minte pentru fiecare matrice ixi numarul de aparitii, i=1,n
            int[] result = new int[n];
        	//construim matricea subproblemelor
            //pe pozitia i si j din matricea subproblemelor 
            //vom tine minte dimensiunea unei matrici formate doar de 1(care poate fi de la 0 pana la n)
            //coordonatele i si j reprezentand coltul dreapta jos al acestei matrici
            //astfel fiecare matrice se recunoaste dupa coltul dreapta jos
            int[][] dp = new int[n][n];
            //initializam matricea cu marginea stanga si inferioara a matricii initiale
            //avand in vedere ca recunoastem matricile dupa coltul dreapta jos
            //rezultatele subproblemelor sunt chiar valorile din matricea dreapta
            for (int i = 0; i < n; i++) {
            	dp[i][0] = matrix[i][0];
            	dp[0][i] = matrix[0][i];
            }
            //parcurgem restul matricii
            for(int i = 1; i < n; i++) {
            	for(int j = 1; j < n; j++)
            		//daca elementul este 1, dimensiunea maxima a unei matrici doar din 1 este minimul dintre
            		//dimensiunilor matricilor adiacente(stanga sus, sus, stanga) + 1
            		if(matrix[i][j] == 1)
            			dp[i][j] = Math.min(dp[i][j-1], Math.min(dp[i-1][j], dp[i-1][j-1])) + 1;
            		//altfel, este evident 0
            		else
            			dp[i][j] = 0;
            }
            /*
            for(int i = 0; i < n; i++) {
            	for(int j = 0; j < n; j++) {
            		System.out.print(dp[i][j] + " ");
            	}
            	System.out.println();
            }
            */
            //calculam cate matrici avem pentru fiecare dimensiune
            for(int i = 0; i < n; i++) {
            	for(int j = 0; j < n; j++) {
            		int unitMatrixDimension = dp[i][j];
            		//o matrice ixi va contine, evident, si o matrice i-1 x i-1
            		while(unitMatrixDimension > 0) {
            			result[--unitMatrixDimension]++;
            		}
            	}
            }
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