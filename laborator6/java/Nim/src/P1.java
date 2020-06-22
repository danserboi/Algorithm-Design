import java.util.ArrayList;
import java.util.Scanner;

/**
 * O clasa cu 2 membri de orice tip
 * Echivalent cu std::pair din C++
 */
class Pair<F, S> {
    public F first;
    public S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
}

/**
 * Reprezinta o mutare efectuata de un jucator
 */
class Move {
    public int amount, heap;

    public Move(int amount, int heap) {
        this.amount = amount; /* Cantitatea extrasa, 1, 2 sau 3 */
        this.heap = heap; /* Indicile multimii din care se face extragerea */
    }

    public Move() {
        this(0, -1);
    }
}

/**
 * Reprezinta starea jocului
 */
class Nim {
    public static int Inf = 123456789;

    public int heaps[];

    public Nim() {
        heaps = new int[3];
        heaps[0] = 3;
        heaps[1] = 4;
        heaps[2] = 5;
    }

    /**
     * Returneaza o lista cu mutarile posibile
     * care pot fi efectuate de player
     */
    public ArrayList<Move> get_moves(int player) {
        ArrayList<Move> ret = new ArrayList<Move>();
        for (int i = 0; i < 3; i++)
            for (int k = 1; k <= 3; k++)
                if (k <= heaps[i])
                    ret.add(new Move(k, i));
        return ret;
    }

    /**
     * Intoarce true daca jocul este intr-o stare finala
     */
    public boolean ended() {
        // jocul e terminat dupa ce s-a luat si ultima bila
    	int s = 0;
        for (int i = 0; i < 3; i++)
            s += heaps[i];
        return s == 0;
    }

    /**
     * Functia de evaluare a starii curente a jocului
     * Evaluarea se face din perspectiva jucatorului
     * aflat curent la mutare (player)
     */
    public int eval(int player) {
    	if(ended() && winner(player)) {
    		return Nim.Inf;
    	}
    	if(ended() && !winner(player)) {
    		return -Nim.Inf;
    	}    	
    	// la acest joc, cel care incepe va fi castigatorul daca respecta urmatoarea strategie
    	// daca jocul nu e pe sfarsite(cand mai ramane doar cel mult o multime cu minim 2 elemente)
    	// jucatorul trebuie sa faca asfel incat xor-ul aplicat pe fiecare numarul de elemente din multime
    	// sa fie 0 dupa mutare
    	// altfel, daca jocul e pe sfarsite, el trebuie sa faca astfel incat
    	// numarul de multimi de doar 1 element sa fie par 
    	// avand in vedere ca putem prezice un castigator doar pe baza configuratiei
    	// vom intoarce doar Nim.Inf respectiv - Nim.Inf
    	int non01 = 0, size1Heaps = 0;
    	for(int i = 0; i < 3 ; i++) {
    		if(heaps[i] > 1)
    			non01++;
    		if(heaps[i] == 1)
    			size1Heaps++;
    	}
    	// daca exista cel putin o multime cu cel putin 2 elemente(jocul se desfasoara normal)
    	if(non01 >= 1) {
        	int nimSum = heaps[0] ^ heaps[1] ^ heaps[2];
        	//System.out.println("NimSum este:" + nimSum);
        	// daca nimSum e diferita de 0, castigam, altfel pierdem
        	if(nimSum != 0) {
        		return Nim.Inf;
        	} else {
        		return -Nim.Inf;
        	}
    	}
    	// daca jocul e pe sfarsite
    	else {
    		// daca numarul de multimi de doar 1 element este par, castigam, altfel pierdem 
    		if(size1Heaps % 2 == 0) {
    			return Nim.Inf;
    		} else {
    			return -Nim.Inf;
    		}
    	}
    }

    /**
     * Aplica o mutarea a jucatorului asupra starii curente
     * Returneaza false daca mutarea e invalida
     */
    public boolean apply_move(Move move) {
    	if(move.amount <= heaps[move.heap]) {
    		heaps[move.heap] -= move.amount;
    		return true;
    	}
        return false;
    }
    
    /**
     * Reconstituie configuratia initala
     */
    public void undo_move(Move move) {
    	heaps[move.heap] += move.amount;
    }

    /**
     * Returns true if player won
     */
    boolean winner(int player)
    {
        if (!ended())
            return false;
        int s = 0;
        for (int i = 0; i < 3; i++)
            s += heaps[i];
        return s == 0;
    }

    /**
     * Afiseaza starea jocului
     */
    public String toString() {
        String ret = "";
        for (int i = 0; i < 3; i++)
        {
            ret += i + ":";
            for (int j = 0; j < heaps[i]; j++)
                ret += " *";
            ret += "\n";
        }
        ret += "\n";

        return ret;
    }

    /**
     * Returneaza o copie a starii de joc
     */
    public Nim clone() {
        Nim ret = new Nim();
        for (int i = 0; i < 3; i++)
            ret.heaps[i] = heaps[i];
        return ret;
    }
}

class P1 {

    /**
     * Implementarea algoritmului negamax (negamax)
     * Intoarce o pereche <x, y> unde x este cel mai bun scor
     * care poate fi obtinut de jucatorul aflat la mutare,
     * iar y este mutarea propriu-zisa
     */
    public static Pair<Integer, Move> negamax(Nim init, int player, int depth) {

    	if(init.ended() || depth == 0) {
    		return new Pair<Integer, Move>(init.eval(player), new Move());
    	}
        ArrayList<Move> moves = init.get_moves(player);
        
        int max = -Nim.Inf;
        Move maxMove = moves.get(0);
        
        for(Move move : moves) {
        	init.apply_move(move);
        	
        	int score = - negamax(init, -player, depth - 1).first;
        	
        	if(score > max) {
        		max = score;
        		maxMove = move;
        	}
        	
        	// e MULT mai eficient sa apelam undo_move decat sa facem mereu o copie pentru configuratie
        	init.undo_move(move);
        	
        }
        
        return new Pair<Integer, Move>(max, new Move(maxMove.amount, maxMove.heap));
    }

    /**
     * Implementarea de negamax cu alpha-beta pruning
     * Intoarce o pereche <x, y> unde x este cel mai bun scor
     * care poate fi obtinut de jucatorul aflat la mutare,
     * iar y este mutarea propriu-zisa
     */
    public static Pair<Integer, Move> negamax_abeta(Nim init, int player, int depth, int alfa, int beta) {

    	if(init.ended() || depth == 0) {
    		return new Pair<Integer, Move>(init.eval(player), new Move());
    	}
        ArrayList<Move> moves = init.get_moves(player);
        
        Move alfaMove = moves.get(0);
        for(Move move : moves) {
        	
        	init.apply_move(alfaMove);
        	int score = - negamax_abeta(init, -player, depth - 1, -beta, -alfa).first;
        	
        	if(score > alfa) {
        		alfa = score;
        		alfaMove = move;
        	}
        	
        	// e MULT mai eficient sa apelam undo_move decat sa facem mereu o copie pentru configuratie
        	init.undo_move(alfaMove);
        	
        	if(alfa >= beta) {
        		break;
        	}
        	
        }
        
        return new Pair<Integer, Move>(alfa, new Move(alfaMove.amount, alfaMove.heap));
    }

    public static void main(String [] args) {
        Nim nim = new Nim();
        nim.heaps[0] = 4;
        nim.heaps[1] = 5;
        nim.heaps[2] = 9;
        System.out.print(nim);

        /* Choose here if you want COMP vs HUMAN or COMP vs COMP */
        boolean HUMAN_PLAYER = false;
        int player = 1;
        
        while (!nim.ended())
        {
            Pair<Integer, Move> p;
            if (player == 1)
            {
                p = negamax(nim, player, 6);
                //p = negamax_abeta(nim, player, 13, -Nim.Inf, Nim.Inf);

                System.out.println("Player " + player + " evaluates to " + p.first);
                nim.apply_move(p.second);
            }
            else
            {
                if (!HUMAN_PLAYER)
                {
                    p = negamax(nim, player, 6);
                    //p = negamax_abeta(nim, player, 13, -Nim.Inf, Nim.Inf);

                    System.out.println("Player " + player + " evaluates to " + p.first);
                    nim.apply_move(p.second);
                }
                else
                {
                    boolean valid = false;
                    while (!valid)
                    {
                        Scanner keyboard = new Scanner(System.in);
                        System.out.print("Insert amount [1, 2 or 3] and heap [0, 1 or 2]: ");
                        int am = keyboard.nextInt();
                        int h = keyboard.nextInt();

                        valid = nim.apply_move(new Move(am, h));
                    }
                }
            }

            System.out.print(nim);
            player *= -1;
        }

        int w = nim.heaps[0] + nim.heaps[1] + nim.heaps[2];
        if (w == 0)
            System.out.println("Player " + player + " WON!");
        else
            System.out.println("Player " + player + " LOST!");
    }
}
