import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
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

        private void writeOutput(ArrayList<ArrayList<Integer>> result) {
            try {
                PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
                pw.printf("%d\n", result.size() + 1);
                for (ArrayList<Integer> arr : result) {
                    for (int i = 0; i < arr.size(); i++) {
                        pw.printf("%d%c", arr.get(i), i + 1 == arr.size() ?
                                '\n' : ' ');
                    }
                }
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        
        void printSolution(Integer[] solution, int stop) {
            for (int i = 0; i < stop; ++i) {
                System.out.print(solution[i] + " ");
            }
            System.out.println();
        }
        
        private ArrayList<ArrayList<Integer>> getResult() {
            ArrayList<ArrayList<Integer>> all = new ArrayList<>();
        	Integer[] solution = new Integer[n+1];
        	back(0, n, solution, all);
            return all;
        }
        private boolean check(Integer[] solution) {
            return true;
        }
        
        private void back(int step, int stop,
                Integer[] solution,  ArrayList<ArrayList<Integer>> all) {
        	if (step == stop) {

                if(check(solution)) {
          
                }
                return;
            }
         
            int i = step > 0 ? solution[step - 1] + 1 : 1;
            for (; i <= n; ++i) {
                solution[step] = i;
	        	ArrayList<Integer> newSolution = new ArrayList<Integer>();
	        	for(int j = 0; j <= step; j++)
	        		newSolution.add(solution[j]);
	        	all.add(newSolution);
	        	printSolution(solution, step + 1);
	        	back(step + 1, stop, solution, all);
            }
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
