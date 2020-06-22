package bonus;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

// Se da un sir S de n numere intregi. Sa se detemine cate inversiuni sunt in sirul dat. 
// Numim inversiune o pereche de indici 1<=i<j<=n astfel incat S[i]>S[j]
public class Bonus {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int n;
		int[] v;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				v = new int[n];
				for (int i = 0; i < n; i++) {
					v[i] = sc.nextInt();
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int inv) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", inv);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		private int mergeWithInv(int[] v, int left, int mid, int right) {
			//cream atat subvectorul stang cat si subvectorul drept
			//am adaugat o santinela reprezentand cel mai mare numar intreg
			int[] leftV = new int[mid - left + 2];
			for(int i = 0; i < leftV.length - 1; i++) {
				leftV[i] = v[left + i];
			}
			leftV[leftV.length - 1] = Integer.MAX_VALUE;
			
			int[] rightV = new int[right - mid + 1];
			for(int i = 0; i < rightV.length - 1; i++) {
				rightV[i] = v[i + mid + 1];
			}
			rightV[rightV.length - 1] = Integer.MAX_VALUE;
			
			// index iterare vector
			int t = left;
			// index iterare subvectorul stang
			int i = 0;
			// index iterare subvectorul drept
			int j = 0;
			// numarul de inversiuni
			int inv = 0;
			// vom obtine inversiuni atunci cand un element din subvectorul stang
			// este mai mare decat un element din subvectorul drept
			// ceea ce inseamna ca restul de elemente din subvectorul stang sunt mai mari
			// decat elementul din subvectorul drept = numarul de inversiuni
			for(t = left; t <= right; t++){
				if(leftV[i] <= rightV[j]) {
					v[t] = leftV[i++];
				}
				else {
					v[t] = rightV[j++];
					inv += mid + 1 - i - left;
				}
			}
			return inv;
		}
		
		
		//vom implementa un merge sort modificat care tine minte si numarul de inversiuni
		private int mergeSortWithInv(int[] v, int left, int right) {
			//numarul de inversiuni este egal cu numarul de inversiuni din subvectorul stang
			//+ numarul de inversiuni din subvectorul drept
			//+ numarul de inversiuni din reuniunea subvectorilor
			int inv = 0;
			if(left < right) {
				int mid = (left + right) / 2;
				inv += mergeSortWithInv(v, left, mid);
				inv += mergeSortWithInv(v, mid + 1, right);
				inv += mergeWithInv(v, left, mid, right);
			}
			return inv;
		}
		
		private int getAnswer(int[] v) {
			return mergeSortWithInv(v, 0, v.length - 1);
		}


		public void solve() {
			readInput();
			writeOutput(getAnswer(v));
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
