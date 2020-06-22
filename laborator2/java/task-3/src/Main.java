import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.PriorityQueue;

public class Main {
    static class Homework implements Comparable<Homework>{
        public int deadline;
        public int score;

        public Homework() {
            deadline = 0;
            score = 0;
        }
        
        //prima varianta
        //vrem sa sortam descrescator dupa punctaj si crescator dupa deadline
        @Override
        public int compareTo(Homework homework) {
        	if(this.score > homework.score || (this.score == homework.score && this.deadline < homework.deadline))
        		return -1;
        	else if(this.score == homework.score && this.deadline == homework.deadline)
        		return 0;
        	else
        		return 1;
        } 
              
		@Override
		public String toString() {
			return "Homework [deadline=" + deadline + ", score=" + score + "]";
		}
        
    }

    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        int n;
        int lastWeek;
        Homework[] hws;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                lastWeek = -1;
                n = sc.nextInt();
                hws = new Homework[n];
                for (int i = 0; i < n; i++) {
                    hws[i] = new Homework();
                    hws[i].deadline = sc.nextInt();
                    hws[i].score = sc.nextInt();
                    if(lastWeek < hws[i].deadline)
                    	lastWeek = hws[i].deadline;
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
        
        //Complexitatea temporala este T(n) = O(n^2) deoarece
        //sortarea se face in O(n*log(n))
        //cand parcurgem elementele cautam si prima saptamana disponibila
        //astfel avem for si while imbricate si in cel mai rau caz avem O(n^2)
        //Complexitatea spatiala este O(n) daca complexitatea sortarii este O(1)
        //asta fiindca folosim un vector auxiliar pentru a marca saptamana in care
        //se face tema curenta
        private int getResult() {
            //sortam temele descrecator dupa punctaj si crescator dupa deadline
        	Arrays.sort(hws);
        	//cream un vector care sa mentioneze daca intr-o anumita saptamana este stabilita deja o tema
        	int[] weeks = new int[lastWeek + 1];
        	int resolvedHws = 0;
        	int result = 0;
        	for(int i = 0; i < n && resolvedHws <= lastWeek; i++) {
        		//vom face temele cu punctaj maxim local neaparat
            	//ele vor fi realizate cat mai tarziu cu putinta
            	//tocmai pentru a da posibilitatea realizarii unor teme cu deadline mai mic
        		int weekToBeMade = hws[i].deadline;
        		while(weeks[weekToBeMade] == 1 && weekToBeMade >= 1) {
        			weekToBeMade--;
        		}
        		if(weekToBeMade != 0) {
	        		weeks[weekToBeMade] = 1;
	        		resolvedHws++;
	        		result += hws[i].score;
        		}
        	}
        	// punctajul maxim pe care il puteti obtine planificand optim temele.
        	return result;
        }
        
        
        //varianta a doua
        //sortam elementele descrescator dupa deadline
        //parcurgem temele dupa ce am efectuat sortarea
        //cream o coada de prioritate in care introducem temele din saptamana la care ne aflam cu parcurgerea
        //extragem maximul din coada de prioritate si efectuam tema in saptamana in care ne aflam
  
        public void solve() {
            readInput();
            writeOutput(getResult());
        }
    }

    public static void main(String[] args) {
        new Task().solve();
    }
}
