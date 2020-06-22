import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        int n, k;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                k = sc.nextInt();
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(ArrayList<ArrayList<Integer>> result) {
            try {
                PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
                pw.printf("%d\n", result.size());
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
        
        private ArrayList<ArrayList<Integer>> getResult() {
            ArrayList<ArrayList<Integer>> all = new ArrayList<>();
            Integer[] solution = new Integer[k];
            Integer[] domain = new Integer[n];
            HashSet<Integer> visited = new HashSet<>();
            for (int i = 0; i < n; ++i) {
                domain[i] = (i+1);
            }
            back(0, k, domain, solution, visited, all);
            return all;
        }
        private boolean check(Integer[] solution) {
            return true;
        }
        private void back(int step, int stop, Integer[] domain,
        		Integer[] solution, HashSet<Integer> visited, ArrayList<ArrayList<Integer>> all) {
         
            if (step == stop) {
                if(check(solution)) {
                	
                	ArrayList<Integer> newSolution = new ArrayList<Integer>();
                	Collections.addAll(newSolution, solution);
                	all.add(newSolution);
                }
                return;
            }
         
            for (int i = 0; i < domain.length; ++i) {
                if (!visited.contains(domain[i])) {
                    visited.add(domain[i]);
         
                    solution[step] = domain[i];
         
                    back(step + 1, stop, domain, solution, visited, all);
         
                    visited.remove(domain[i]);
                }
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
