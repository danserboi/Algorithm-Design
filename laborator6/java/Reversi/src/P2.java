import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clasa cu 2 membri de orice tip Echivalent cu std::pair din C++
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
	public int player, x, y;

	public Move(int player, int x, int y) {
		this.player = player; /* Jucatorul care face mutarea */
		this.x = x; /* Linia */
		this.y = y; /* Coloana */
	}

	public Move(int player) {
		this(player, -1, -1);
	}
}

/**
 * Reprezinta starea jocului
 */
class Reversi {
	public static int Inf = 123456789;
	public static int N = 6; /* Pastrati N par, N >= 4 */

	public int data[][];

	public Reversi() {
		data = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				data[i][j] = 0;
		data[N / 2 - 1][N / 2 - 1] = data[N / 2][N / 2] = 1;
		data[N / 2 - 1][N / 2] = data[N / 2][N / 2 - 1] = -1;
	}

	/**
	 * Intoarce true daca jocul este intr-o stare finala
	 */
	public boolean ended() {
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				Reversi tmp = (Reversi) this.clone();
				if (tmp.apply_move(new Move(1, i, j)))
					return false;
				tmp = (Reversi) this.clone();
				if (tmp.apply_move(new Move(-1, i, j)))
					return false;
			}
		return true;
	}

	/**
	 * Returns 1 if player won, 0 if draw and -1 if lost
	 */
	int winner(int player) {
		if (!ended())
			return 0;
		int s = 0;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (data[i][j] == player)
					s++;
				else if (data[i][j] == -player)
					s--;
			}
		return s > 0 ? 1 : s == 0 ? 0 : -1;
	}

	/**
	 * Functia de evaluare a starii curente a jocului Evaluarea se face din
	 * perspectiva jucatorului aflat curent la mutare (player)
	 */
	public int eval(int player) {
		if (winner(player) == 1)
			return Reversi.Inf;
		if (winner(player) == 0)
			return 0;
		if (winner(player) == -1)
			return -Reversi.Inf;
		// primul parametru tine cont de cate monezi
		// are jucatorul fata de adversar si cate monezi sunt in joc
		int playerCoins = 0;
		int opponentCoins = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (data[i][j] == player) {
					playerCoins++;
				}
				if (data[i][j] == -player) {
					opponentCoins++;
				}
			}
		}
		int coinValue = 100 * (playerCoins - opponentCoins) / (playerCoins + opponentCoins);
		// al doilea parametru tine cont de monezile puse in colt
		// acestea nu mai pot fi luate de adversar si in jurul lor se pot pune cu
		// usurinta alte monezi
		int corners = 0, opCorners = 0;
		if (data[0][0] == player)
			corners++;
		if (data[0][N - 1] == player)
			corners++;
		if (data[N - 1][0] == player)
			corners++;
		if (data[N - 1][N - 1] == player)
			corners++;
		if (data[0][0] == -player)
			opCorners++;
		if (data[0][N - 1] == -player)
			opCorners++;
		if (data[N - 1][0] == -player)
			opCorners++;
		if (data[N - 1][N - 1] == -player)
			opCorners++;
		int cornersValue = 0;
		if (corners + opCorners != 0)
			cornersValue = 100 * (corners - opCorners) / (corners + opCorners);
		// rezultatul este suma acestor 2 parametri
		return coinValue + cornersValue;
	}

	/**
	 * Aplica o mutare a jucatorului, modificand starea jocului Plaseaza piesa
	 * jucatorului move.player in pozitia move.x, move.y Mutarea move.x == -1,
	 * move.y == -1 semnifica ca jucatorul paseaza randul la mutare Returneaza true
	 * daca mutarea este valida
	 */
	public boolean apply_move(Move move) {
		if (move.x == -1 && move.y == -1)
			return true;

		if (data[move.x][move.y] != 0)
			return false;

		boolean ok = false;

		for (int x_dir = -1; x_dir <= 1; x_dir++)
			for (int y_dir = -1; y_dir <= 1; y_dir++) {
				if (x_dir == 0 && y_dir == 0)
					continue;
				int i = move.x + x_dir, j = move.y + y_dir;
				for (; i >= 0 && j >= 0 && i < N && j < N && data[i][j] == -move.player; i += x_dir, j += y_dir)
					;
				if (i >= 0 && j >= 0 && i < N && j < N && data[i][j] == move.player
						&& (P2.abs(move.x - i) > 1 || P2.abs(move.y - j) > 1)) {
					ok = true;
					for (i = move.x + x_dir, j = move.y + y_dir; i >= 0 && j >= 0 && i < N && j < N
							&& data[i][j] == -move.player; i += x_dir, j += y_dir)
						data[i][j] = move.player;
				}
			}

		if (!ok)
			return false;

		data[move.x][move.y] = move.player;

		return true;
	}

	/**
	 * Afiseaza starea jocului
	 */
	public String toString() {
		String ret = "";
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (data[i][j] == 0)
					ret += ".";
				else if (data[i][j] == 1)
					ret += "O";
				else
					ret += "X";
				ret += " ";
			}
			ret += "\n";
		}
		ret += "\n";

		return ret;
	}

	/**
	 * Intoarce o copie a starii de joc
	 */
	public Object clone() {
		Reversi ret = new Reversi();
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				ret.data[i][j] = data[i][j];
		return ret;
	}
}

class P2 {
	static int abs(int x) {
		return x > 0 ? x : -x;
	}

	static Pair<Integer, Move> negamax(Reversi init, int player, int depth) {
		if (init.ended() || depth == 0) {
			return new Pair<Integer, Move>(init.eval(player), new Move(player));
		}

		Reversi movesClone = (Reversi) init.clone();
		ArrayList<Move> moves = new ArrayList<Move>();
		for (int i = 0; i < Reversi.N; i++)
			for (int k = 0; k < Reversi.N; k++) {
				Move posMove = new Move(player, i, k);
				if (movesClone.apply_move(posMove)) {
					moves.add(posMove);
				}
			}
		int max = -Reversi.Inf;
		// avem grija ca playerul sa poate ceda randul daca nu poate efectua nicio
		// mutare
		Move maxMove = null;
		if (moves.size() == 0)
			maxMove = new Move(player);
		else
			maxMove = moves.get(0);

		for (Move move : moves) {
			Reversi clone = (Reversi) init.clone();
			clone.apply_move(move);

			int score = -negamax(clone, -player, depth - 1).first;

			if (score > max) {
				max = score;
				maxMove = move;
			}
		}

		return new Pair<Integer, Move>(max, new Move(maxMove.player, maxMove.x, maxMove.y));
	}

	/**
	 * Implementarea de negamax cu alpha-beta pruning Intoarce o pereche <x, y> unde
	 * x este cel mai bun scor care poate fi obtinut de jucatorul aflat la mutare,
	 * iar y este mutarea propriu-zisa
	 */
	static Pair<Integer, Move> negamax_abeta(Reversi init, int player, int depth, int alfa, int beta) {
		if (init.ended() || depth == 0) {
			return new Pair<Integer, Move>(init.eval(player), new Move(player));
		}
		Reversi movesClone = (Reversi) init.clone();
		ArrayList<Move> moves = new ArrayList<Move>();
		for (int i = 0; i < Reversi.N; i++)
			for (int k = 0; k < Reversi.N; k++) {
				Move posMove = new Move(player, i, k);
				if (movesClone.apply_move(posMove)) {
					moves.add(posMove);
				}
			}
		// avem grija ca playerul sa poate ceda randul daca nu poate efectua nicio
		// mutare
		Move alfaMove = null;
		if (moves.size() == 0)
			alfaMove = new Move(player);
		else
			alfaMove = moves.get(0);

		for (Move move : moves) {
			Reversi clone = (Reversi) init.clone();
			clone.apply_move(move);
			Reversi opResponse = (Reversi) clone.clone();
			int score = -negamax_abeta(opResponse, -player, depth - 1, -beta, -alfa).first;

			if (score > alfa) {
				alfa = score;
				alfaMove = move;
			}
			if (alfa >= beta) {
				break;
			}
		}

		return new Pair<Integer, Move>(alfa, new Move(alfaMove.player, alfaMove.x, alfaMove.y));
	}

	public static void main(String[] args) {
		Reversi rev = new Reversi();
		System.out.print(rev);
		/* Choose here if you want COMP vs HUMAN or COMP vs COMP */
		boolean HUMAN_PLAYER = false;
		int player = 1;

		while (!rev.ended()) {
			Pair<Integer, Move> p;
			if (player == 1) {
				// p = negamax(rev, player, 6);
				p = negamax_abeta(rev, player, 9, -Reversi.Inf, Reversi.Inf);

				System.out.println("Player " + player + " evaluates to " + p.first);
				rev.apply_move(p.second);
			} else {
				if (!HUMAN_PLAYER) {
					// p = negamax(rev, player, 6);
					p = negamax_abeta(rev, player, 9, -Reversi.Inf, Reversi.Inf);

					System.out.println("Player " + player + " evaluates to " + p.first);
					rev.apply_move(p.second);
				} else {
					boolean valid = false;
					while (!valid) {
						Scanner keyboard = new Scanner(System.in);
						System.out.print("Insert position [0..N - 1], [0..N - 1] ");

						int x = keyboard.nextInt();
						int y = keyboard.nextInt();

						valid = rev.apply_move(new Move(player, x, y));
					}
				}
			}

			System.out.print(rev);
			player *= -1;
		}

		int w = rev.winner(1);
		if (w == 1)
			System.out.println("Player 1 WON!");
		else if (w == 0)
			System.out.println("DRAW!");
		else
			System.out.println("Player -1 WON!");
	}
}