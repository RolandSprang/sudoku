/**
 * 
 * @author Roland Sprang
 *@version 1.0.0
 */
public class BruteForceSolver {
	
	// <<< Singleton
	// Innere private Klasse, die erst beim Zugriff durch die umgebende Klasse initialisiert wird
	private static final class InstanceHolder {
		// Die Initialisierung von Klassenvariablen geschieht nur einmal 
		// und wird vom ClassLoader implizit synchronisiert
		static final BruteForceSolver INSTANCE = new BruteForceSolver();
	}

	// Verhindere die Erzeugung des Objektes über andere Methoden
	private BruteForceSolver () {}
	
	// Eine nicht synchronisierte Zugriffsmethode auf Klassenebene.
	public static BruteForceSolver getInstance () {
		return InstanceHolder.INSTANCE;
	}
	// >>> Singleton
	
	
	private int SIZE;
	private int[][] board;
	private int SQRN;
	
	/**
	 * This function tryes to solve the given sudoku. It use a recursive functionality to find the solution.
	 * @param board
	 * @return returns the solved Sudokubard. If it is not possible to solve the sudoku, the function returns NULL
	 */
	public int[][] solve(int[][] board) {
		this.SIZE = board.length;
		this.board = board;
		
		double tempSQR = Math.sqrt((double) SIZE);
		if((tempSQR % 1) > 0) {
			System.out.println("Can not solve sudoku with this edge length: " + SIZE);
			System.exit(0);
		}
		this.SQRN = (int)tempSQR;
		
		if (fillRemaining(0, 0))
			return board;
		return null;
	}
	
	private boolean fillRemaining(int x, int y) {
		
		if((x >= SIZE) && (y >= SIZE))
			return true;
		
		if(x < SIZE && y >= SIZE) {
			x++;
			y = 0;
		}
		
		if(x >= SIZE)
			return true;
				
		while(board[x][y] != 0) {
			if(y < SIZE - 1) {
				y++;;
			}else if(x < SIZE) {
				y = 0;
				x++;
			}
			if (x >= SIZE) {
				return true;
			}
		}
		
//		for(int number = 1; number <= SIZE; number++) {
		for(int number = 8; number >= 0; number--) {
			if(isValid(number, x, y)){
				board[x][y] = number;
				if (fillRemaining(x, y + 1)) return true;
				board[x][y] = 0;
			}
		}
		return false;
	}
	
	private boolean isValid(int number, int posX, int posY) {
		return(	checkRow(posX, number) &&
				checkCol(posY, number) &&
				checkBox(posX - posX%SQRN, posY-posY%SQRN, number));
	}

	private boolean checkRow(int posX, int number) {
		for(int y = 0; y < SIZE; y++) {
			if(board[posX][y] == number) return false;
		}
		return true;
	}

	private boolean checkCol(int posY, int number) {
		for(int x = 0; x < SIZE; x++) {
			if(board[x][posY] == number) return false;
		}
		return true;
	}

	private boolean checkBox(int posX, int posY, int number) {
		for(int x = posX; x < posX + SQRN; x++) {
			for(int y = posY; y < posY + SQRN; y++) {
				if(board[x][y] == number) return false;
			}
		}
		return true;
	}
}