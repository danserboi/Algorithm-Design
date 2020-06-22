import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        int n;
        int[] v;

        private final static int MOD = 1000000007;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                v = new int[n + 1];
                for (int i = 1; i <= n; i++) {
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
        /*
        //Pentru solutia care foloseste programare dinamica si aloca vectori, complexitatea temporala este O(n) deoarece trebuie parcurs sirul initial
        //iar complexitatea spatiala este O(n) pentru ca se aloca 2 vectori cu aceeasi dimensiune ca vectorul initial
        private int getResult() {
            // Aflam numarul de subsiruri (ale sirului stocat in v,
            // indexat de la 1 la n), nevide cu suma numerelor para.
            // Rezultatul se va intoarce modulo MOD (1000000007).
        	//vom crea 2 vectori cu solutii partiale
        	//unul care tine minte pe fiecare pozitie numarul de subsiruri cu suma numerelor para din subvectorul [v[1]...v[i]]
        	int[] dpEven = new int[n+1];
        	//unul care tine minte pe fiecare pozitie numarul de subsiruri cu suma numerelor impara din subvectorul [v[1]...v[i]]
            int[] dpOdd = new int[n+1];
        	for(int i = 1; i <= n; i++) {
            	if(v[i] % 2 == 1) {
            		//daca numarul este impar, pentru a obtine o suma impara:
            		//el poate fi adaugat la orice subsir cu suma numerelor para deja existent(impar + par = impar)
            		//la care se adauga numarul de subsiruri cu suma numerelor impara deja calculat(nu mai poate completa astfel de siruri)
            		//si subsirul format chiar din numarul insusi
            		dpOdd[i] = (dpEven[i-1] + dpOdd[i-1] + 1) % MOD;
            		//daca numarul este impar, pentru a obtine o suma para
            		//el poate fi adaugat la orice subsir cu suma numerelor impara deja existent(impar + impar = par)
            		//la care se adauga numarul de subsiruri cu suma numerelor para deja calculat(nu mai poate completa astfel de siruri)
            		dpEven[i] = (dpOdd[i-1] + dpEven[i-1]) % MOD;
            	}
            	else {
            		//daca numarul este par, pentru a obtine o suma impara:
            		//el poate fi adaugat la orice subsir cu suma numerelor impara deja existent(par + impar = impar)
            		//la care se adauga numarul de subsiruri cu suma numerelor impara deja calculat
            		dpOdd[i] = (2*dpOdd[i-1]) % MOD;
            		//daca numarul este par, pentru a obtine o suma para:
            		//el poate fi adaugat la orice subsir cu suma numerelor para deja existent(par + par = par)
            		//la care se adauga numarul de subsiruri cu suma numerelor para dejat calculat
            		//si subsirul format chiar din numarul insusi
            		dpEven[i] = (2*dpEven[i-1] + 1) % MOD;            	}
            }
        	return dpEven[n];
        }*/
        /*
        //Pentru solutia care foloseste programare dinamica si doar variabile, complexitatea temporala este O(n) deoarece trebuie parcurs sirul initial
        //iar complexitatea spatiala este O(1) deoarece folosim doar 4 variabile pentru numarul de subsiruri cu suma numerelor para, respectiv impara
        //pentru pasul actual si pentru pasul precedent si o variabila pentru iteratie
        private int getResult() {
            // Aflam numarul de subsiruri (ale sirului stocat in v,
            // indexat de la 1 la n), nevide cu suma numerelor para.
            // Rezultatul se va intoarce modulo MOD (1000000007).
        	//folosim 4 variabile pentru numarul de subsiruri cu suma numerelor para, respectiv impara
            //pentru pasul actual si pentru pasul precedent
            int newOdd = 0, oldOdd = 0, newEven = 0, oldEven = 0;
        	for(int i = 1; i <= n; i++) {
        		oldOdd = newOdd;
        		oldEven = newEven;
            	if(v[i] % 2 == 1) {
            		//daca numarul este impar, pentru a obtine o suma impara:
            		//el poate fi adaugat la orice subsir cu suma numerelor para deja existent(impar + par = impar)
            		//la care se adauga numarul de subsiruri cu suma numerelor impara deja calculat(nu mai poate completa astfel de siruri)
            		//si subsirul format chiar din numarul insusi
            		newOdd = (oldEven + oldOdd + 1) % MOD;
            		//daca numarul este impar, pentru a obtine o suma para
            		//el poate fi adaugat la orice subsir cu suma numerelor impara deja existent(impar + impar = par)
            		//la care se adauga numarul de subsiruri cu suma numerelor para deja calculat(nu mai poate completa astfel de siruri)
            		newEven = (oldOdd + oldEven) % MOD;
            	}
            	else {
            		//daca numarul este par, pentru a obtine o suma impara:
            		//el poate fi adaugat la orice subsir cu suma numerelor impara deja existent(par + impar = impar)
            		//la care se adauga numarul de subsiruri cu suma numerelor impara deja calculat
            		newOdd = (2*oldOdd) % MOD;
            		//daca numarul este par, pentru a obtine o suma para:
            		//el poate fi adaugat la orice subsir cu suma numerelor para deja existent(par + par = par)
            		//la care se adauga numarul de subsiruri cu suma numerelor para dejat calculat
            		//si subsirul format chiar din numarul insusi
            		newEven = (2*oldEven + 1) % MOD;            	}
            }
        	return newEven;
        }
        */
        //Aceasta solutie este gandita matematic:
        //Numarul de subsiruri cu suma numerelor para este egala cu
        //cu numarul total de subsiruri - numarul total de subsiruri cu suma impara
        //unde numarul total de subsiruri este 2^n -1 = suma combinarilor de n luate cate i, i =1..n
        //iar numarul total de subsiruri cu suma impara, avand in vedere ca sunt formate dintr-un nr impar de numere impare(poate fi si 0) si orice numar de numere pare)
        //este egal cu suma tuturor combinarilor de numere pare inmultita cu suma tuturor combinarilor cu un nr impar de numere impare, care este jumatate din suma tuturor combinarilor de numere impare
        //astfel, formula este:2^n - 1 - 2^(numarul de numere pare)*2^(numarul de numere impare - 1)
        //Pentru aceasta solutie, complexitatea temporala este O(n) deoarece trebuie sa iteram prin vectorul initial,
        //iar complexitatea spatiala este O(1) folosind doar variabile pentru a calcula numarul de numere pare, numarul de numere impare
        private int getResult() {
        	//calculam numarul de numere impare, respectiv pare din vectorul dat
            int noOdd = 0, noEven = 0;
        	for(int i = 1; i <= n; i++) {
            	if(v[i] % 2 == 1) {
            		noOdd++;
            	}
            	else {
            		noEven++;
            	}
            }
        	//aplicam formula si calculam restul
        	long result = (fastPow(2, n, MOD) - 1 + MOD) % MOD;
        	long auxResult = 0;
        	if(noOdd >= 1)
        		auxResult = (fastPow(2, noEven, MOD)*fastPow(2, noOdd - 1, MOD)) % MOD;
        	return (int)((result - auxResult + MOD) % MOD);
        }
        
		private long fastPow(int base, int exponent, int mod) {
			if(base == 0)
				return 0;
			if(exponent == 0)
				return 1;
			if(exponent % 2 == 1) {
				return ((base % mod)*(fastPow(base, exponent -1, mod) % mod)) % mod;
			}
			else {
				return (((fastPow(base, exponent / 2, mod) % mod) * (fastPow(base, exponent / 2, mod) % mod))) % mod;
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
