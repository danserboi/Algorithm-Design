import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Generic class to represent a regular pair (C++ style).
 * 
 * Use it for storing the indexes of a grid cell like this <row, col>.
 */
class Pair<T, U> {
    public T first;
    public U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object other) {
        Pair<T, U> p = (Pair<T, U>) other;
        
        return this.first.equals(p.first) && this.second.equals(p.second);
    }

    @Override
    public int hashCode() {
        int result = this.first == null ? 0 : this.first.hashCode();
        result = result * 31 + (this.second == null ? 0 : this.second.hashCode());
        return result;  
    }

    @Override
    public String toString() {
        return "<" + this.first + ", " + this.second + ">";
    }
}

public class Main {
    static int rowPacman, colPacman;
    static int rowFood, colFood;
    static int numRows, numCols;
    /**
     * Generic method to print the path to a goal state.
     *
     * Each element in the path is a pair containing a matrix cell's row and column.
     *
     * @param path        The path to a goal state.
     */
    public static <T, U> void printPath(List<Pair<T, U>> path) {
        System.out.println(path.size() - 1);
        for (int i = path.size() - 1; i >= 0; --i) {
            System.out.println(path.get(i).first + " " + path.get(i).second);
        }
    }

    /**
     * Method to implement the A* algorithm.
     *
     * @param M             Encoding of the grid.
     * @param rowPacman     Pacman's starting row.
     * @param colPacman     Pacman's starting column.
     * @param rowFood       Food row.
     * @param colFood       Fool column.
     *
     * @return              The shortest path between Pacman and food, determined with A*.
     *                      If no such path exists, returns an empty path.
     */
    public static List<Pair<Integer, Integer>> astar(List<List<Character>> M) {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        
        // TODO: Implement A*.
        
        // INITIALIZARI
        HashMap<Pair<Integer, Integer>, Integer> f = new HashMap<Pair<Integer, Integer>, Integer>();
        HashMap<Pair<Integer, Integer>, Integer> g = new HashMap<Pair<Integer, Integer>, Integer>();
        HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> p = new HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>();
        
        Pair<Integer, Integer>  n0 = new Pair<Integer, Integer>(rowPacman, colPacman);
        int f0 = heuristic(n0);
        int g0 = 0;
        Pair<Integer, Integer> p0 = null;
        
        
        List<Pair<Integer, Integer>> open = new ArrayList<>();
        List<Pair<Integer, Integer>> closed = new ArrayList<>();

        open.add(n0);
        f.put(n0, f0);
        g.put(n0, g0);
        p.put(n0, p0);
        
        
        Pair<Integer, Integer> res = null;
        
        while(!open.isEmpty()) {
        	// selectam nodul cu f(nod) minim, nod fiind in open
        	Pair<Integer, Integer> node = open.get(0);
        	int min = f.get(open.get(0));
        	for(int i = 0; i < open.size(); i++) {
        		if(f.get(open.get(i)) < min) {
        			node = open.get(i);
        			min = f.get(open.get(i));
        		}
        	}
        	// verificam daca obtinem solutie
        	if(node.first == rowFood && node.second == colFood) {
        		res = node;
        		break;
        	}
        	// scoatem nodul din open si il adaugam in close
        	open.remove(node);
        	closed.add(node);
        	// expandam nodul
        	List<Pair<Integer, Integer>> succs = expand(node, M);
        	for(Pair<Integer, Integer> succ : succs) {
        		// cost(nod, succ) = 1
        		int g_succ = g.get(node) + 1;
        		int f_succ = g_succ + heuristic(succ);
        		// daca avem nod nou descoperit
        		if(open.indexOf(succ) == -1 && closed.indexOf(succ) == -1) {
        			open.add(succ);
        			g.put(succ, g_succ);
        			f.put(succ, f_succ);
        			p.put(succ, node);
        		} else {
        			// a mai fost prelucrat
        			
        			// verific daca noul g este mai mic decat anteriorul
        			// == cale mai buna
        			if(g_succ < g.get(succ)) {
            			g.replace(succ, g_succ);
            			f.replace(succ, f_succ);
            			p.replace(succ, node);
            			// daca era considerat expandat, il redeschid
            			if(closed.indexOf(succ) != -1) {
            				closed.remove(succ);
            				open.add(succ);
            			}
        			}
        		}
        	}
        }
        Pair<Integer, Integer> parent = p.get(res);
        while(parent != null) {
        	path.add(res);
        	res = parent;
        	parent = p.get(parent);
        }
        path.add(n0);
        return path;
    }

    public static List<Pair<Integer, Integer>> expand ( Pair<Integer, Integer> n, 
    													List<List<Character>> M) {
    	
    	List<Pair<Integer, Integer>> succs = new ArrayList<>();
    	// n.first - 1, n.second
    	if(checkCoord(n.first - 1, n.second) &&(M.get(n.first - 1).get(n.second) == '-' ||
    			M.get(n.first - 1).get(n.second) == '.')) {
    		Pair<Integer, Integer>  n0 = new Pair<Integer, Integer>(n.first - 1, n.second);
    		succs.add(n0);
    	}
    	// n.first + 1, n.second
    	if(checkCoord(n.first + 1, n.second) && (M.get(n.first + 1).get(n.second) == '-' ||
    			M.get(n.first + 1).get(n.second) == '.')) {
    		Pair<Integer, Integer>  n0 = new Pair<Integer, Integer>(n.first + 1, n.second);
    		succs.add(n0);
    	}
    	// n.first, n.second - 1
    	if(checkCoord(n.first, n.second - 1) && (M.get(n.first).get(n.second -  1) == '-' ||
    			M.get(n.first).get(n.second -  1) == '.')) {
    		Pair<Integer, Integer>  n0 = new Pair<Integer, Integer>(n.first, n.second - 1);
    		succs.add(n0);
    	}
    	// n.first, n.second + 1
    	if(checkCoord(n.first, n.second + 1) && (M.get(n.first).get(n.second + 1) == '-' ||
    			M.get(n.first).get(n.second + 1) == '.')) {
    		Pair<Integer, Integer>  n0 = new Pair<Integer, Integer>(n.first, n.second + 1);
    		succs.add(n0);
    	}
    	return succs;
    }
    
    public static boolean checkCoord(int row, int col) {
    	if(row >= 0 && row < numRows && col >= 0 && col < numCols) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public static int heuristic( Pair<Integer, Integer>  n) {
    	return Math.abs(n.first - rowFood) + Math.abs(n.second - colFood);
    }
    
    public static void main(String[] args) {
        List<List<Character>> M = new ArrayList<>();
        List<Pair<Integer, Integer>> path;

        Scanner s = new Scanner(System.in);

        rowPacman = s.nextInt();
        colPacman = s.nextInt();
        rowFood = s.nextInt();
        colFood = s.nextInt();
        numRows = s.nextInt();
        numCols = s.nextInt();

        M = new ArrayList<>();

        for (int i = 0; i < numRows; ++i) {
            List<Character> currentRow = new ArrayList<>();
            String nextRow = s.next();
            for (int j = 0; j < numCols; ++j) {
                currentRow.add(nextRow.charAt(j));
            }
            M.add(currentRow);
        }

        s.close();

        path = astar(M);
        printPath(path);
    }
}

