import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static class Obj implements Comparable<Obj>{
        public int weight;
        public int price;

        public Obj() {
            weight = 0;
            price = 0;
        }
        //trebuie sa facem cast la double pentru fiecare raport
        //altfel, putem avea egalitate atunci cand catul numerelor este acelasi, desi restul e diferit
        @Override
        public int compareTo(Obj obj) {
        	if((double)this.price/this.weight > (double)obj.price/obj.weight)
        		return -1;
        	else if((double)this.price/this.weight - (double)obj.price/obj.weight < 0.001)
        		return 0;
        	else
        		return 1;
        }
		@Override
		public String toString() {
			return "Obj [weight=" + weight + ", price=" + price + "]";
		}
        
    };

    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        int n, w;
        Obj[] objs;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                w = sc.nextInt();
                objs = new Obj[n];
                for (int i = 0; i < n; i++) {
                    objs[i] = new Obj();
                    objs[i].weight = sc.nextInt();
                    objs[i].price = sc.nextInt();
                }
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        
        private void writeOutput(double result) {
            try {
                PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
                pw.printf("%.4f\n", result);
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        
        //Complexitatea temporala este T(n)=O(n*log(n)) deoarece
        //sortarea se face in O(n*log(n))
        //parcurgerea vectorului este O(n)
        //complexitatea spatiala este O(1) daca pentru sortare este O(1)
        private double getResult() {
        	//sortam obiectele descrescator dupa raportul price/weight
            //pentru aceasta, am implementat metoda compareTo in clasa Obj
        	Arrays.sort(objs);
        	int dispWeight = w, i = 0;
        	double result = 0;
        	while(dispWeight > 0) {
            	if(dispWeight > objs[i].weight) {
            		result += objs[i].price;
            		dispWeight -= objs[i].weight;
            		i++;
            	}
            	else {
            		result += objs[i].price * (double)dispWeight / objs[i].weight;
            		dispWeight = 0;
            	}
            }
        	//profitul maxim
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
