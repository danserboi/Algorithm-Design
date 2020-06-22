import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        int n, m;
        int[] dist;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                m = sc.nextInt();
                dist = new int[n];
                for (int i = 0; i < n; i++) {
                    dist[i] = sc.nextInt();
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
            // a ajunge la destinatie.
        	int result = 0;
        	//cu siguranta masina va ajunge la prima benzinarie
        	//altfel nu ar putea efectua distanta
        	int currentEnergy = m - dist[0];
        	//tebuie verificat daca face plinul chiar la prima benzinarie
        	if(n > 1)
	        	if(currentEnergy == 0) {
	        		result++;
	        		currentEnergy = m;
	        	}
        	for(int i = 0; i < n - 1; i++) {
        		if(dist[i + 1] - dist[i] > currentEnergy) {
        			result++;
        			currentEnergy = m;
        			currentEnergy -= dist[i + 1] - dist[i];
        		}
        		else {
        			currentEnergy -= dist[i + 1] - dist[i];
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
