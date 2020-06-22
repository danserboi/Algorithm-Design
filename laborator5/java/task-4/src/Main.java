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

		int n, k, genStringLen;
		char[] caractere;
		int[] freq;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				k = sc.nextInt();
				String s = sc.next().trim();
				s = " " + s;
				caractere = s.toCharArray();
				freq = new int[n + 1];
				for (int i = 1; i <= n; i++) {
					freq[i] = sc.nextInt();
					genStringLen += freq[i];
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<ArrayList<Character>> result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", result.size());
				for (ArrayList<Character> arr : result) {
					for (int i = 0; i < arr.size(); i++) {
						pw.printf("%c", arr.get(i));
					}
					pw.printf("\n");
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private ArrayList<ArrayList<Character>> getResult() {
			ArrayList<ArrayList<Character>> all = new ArrayList<>();
			char[] solution = new char[genStringLen];
			back(0, genStringLen, solution, all);
			return all;
		}
        private boolean check(char[] solution, int step, char current, int frecventa) {
            int fr = 0, maxAp = 0;
            int i = step;
            //calculez numarul de aparitii consecutive si am grija daca sunt pe cazul initial
            if(i > 0)
            {
            	i--;
	            while(i >= 0 && solution[i] == current) {
	            	fr++;
	            	maxAp++;
	            	i--;
	            }
        	}
            //daca deja am k aparitii, nu mai pot adauga la final
            if(maxAp == k)
            	return false;
            //calculez frecventa caracterului in sir
        	for(; i >= 0; i--) {
            	if(solution[i] == current)
            		fr++;
            }
        	//daca deja frecventa este atinsa, nu mai pot adauga
        	if(fr == frecventa) {
        		return false;
        	}
        	return true;
        }
        
        private void back(int step, int stop,
        		char[] solution, ArrayList<ArrayList<Character>> all) {
            if (step == stop) {
            	ArrayList<Character> newSolution = new ArrayList<Character>();
            	for(int i = 0; i < genStringLen; i++)
            		newSolution.add(solution[i]);
            	all.add(newSolution);
                return;
            }
         
            for (int i = 1; i < caractere.length; ++i) {
                if (check(solution, step, caractere[i], freq[i])) {
                    solution[step] = caractere[i];
                    back(step + 1, stop, solution, all);
                    solution[step] = ' ';
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
