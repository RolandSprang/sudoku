import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple Class to generate a Classic sudoku.<br>
 * The generated sudoku puzzles 'might' have multiple solutions!
 * @author Roland Sprang
 *@version 1.0.0
 */

public class BasicSudoku {
	
	public static void main(String[] args) {
		BasicSudoku s =  new BasicSudoku(9, 20);
		s.viewSudokuBoard(s.getSudokuBoard(), s.getEdgeLength());
	}
	
	private int[][] solvedSudokuBoard;	// the solved sudoku board
	private int[][] sudokuBoard;		// the sudoku board to 
	private double size;				// the edge length of the sudoku board
	private int SQRN;					// Square root Number
	private int fieldsToDelete;			// Amount of Fields to delete
	
	private ArrayList<Integer> posibileNumbers = new ArrayList<Integer>();

	public BasicSudoku(int[][] sudokuBoard) {
		this.sudokuBoard = sudokuBoard;
	}
	
	public BasicSudoku(int size, int fieldsToDelete) {
		this.size = size;
		this.fieldsToDelete = fieldsToDelete;
		this.SQRN = 0;
		this.solvedSudokuBoard = new int[ size][ size];
		this.sudokuBoard = new int[ size][ size];
		
		double tempSQR = Math.sqrt(size);
		if((tempSQR % 1) > 0) {
			System.out.println("Can not generate sudoku with this edge length: " + size);
			System.exit(0);
		}
		this.SQRN = (int)tempSQR;
		
		generateSudoku();
	}
	
	/**
	 * Prints the Sudoku board on the console.
	 * @param sudoku
	 * @param edgeLength
	 */
	public void viewSudokuBoard(int[][] sudoku, int edgeLength) {
		for(int x = 0; x < edgeLength; x++) {
			System.out.println("-------------------");
			System.out.print("|");
			for(int y = 0; y < edgeLength; y++) {
				int v = sudoku[x][y];
				System.out.print( v + "|");
			}
			System.out.println();
		}
	}

	private void generateSudoku() {
		fillDiagonalFields();
		
		setPosibileNumbers();
		
		if(!fillRemainingFields(0, SQRN)) {
			System.out.println("Sudoku can not be generated");
			return;
		}
		
		sudokuBoard = solvedSudokuBoard;
		deleteFields();
	}

	private void fillDiagonalFields() {
		for(int i = 0; i < size; i+=SQRN) {
			setPosibileNumbers();
			for(int y = 0; y < SQRN; y++) {
				for (int x = 0; x < SQRN; x++) {
					solvedSudokuBoard[i + x][i + y] = posibileNumbers.get(0);
					posibileNumbers.remove(0);
				}
			}
		}
	}

	private boolean fillRemainingFields(int x, int y) {
		
		if((x >= size) && (y >= size))
			return true;
		
		if(x < size && y >= size) {
			x++;
			y = 0;
		}
		
		while(solvedSudokuBoard[x][y] != 0) {
			if(y < size - 1) {
				y++;;
			}else if(x < size) {
				y = 0;
				x++;
			}
			if (x >= size) {
				return true;
			}
		}
		
		for(int number = 1; number <= size; number++) {
			if(isValid(number, x, y)) {
				solvedSudokuBoard[x][y] = number;
				if (fillRemainingFields(x, y + 1)) return true;
				solvedSudokuBoard[x][y] = 0;
			}
		}
		return false;
	}

	
	private boolean isValid(int number, int posX, int posY) {
		return(	checkRow(posX, number) &&
				checkCol(posY, number) &&
				checkBox(posX - posX % SQRN, posY - posY % SQRN, number));
	}

	private boolean checkRow(int posX, int number) {
		for(int y = 0; y < size; y++) {
			if(solvedSudokuBoard[posX][y] == number) return false;
		}
		return true;
	}

	private boolean checkCol(int posY, int number) {
		for(int x = 0; x < size; x++) {
			if(solvedSudokuBoard[x][posY] == number) return false;
		}
		return true;
	}

	private boolean checkBox(int posX, int posY, int number) {
		for(int x = posX; x < posX + SQRN; x++) {
			for(int y = posY; y < posY + SQRN; y++) {
				if(solvedSudokuBoard[x][y] == number) return false;
			}
		}
		return true;
	}

	private void setPosibileNumbers() {
		for(int i = 1; i <= size; i++) {
			posibileNumbers.add(i);
			Collections.shuffle(posibileNumbers);
		}
	}

	private void deleteFields() {
		for (int i = fieldsToDelete; i > 0; i--) {
			int randomX = (int) ( Math.random() * size);
			int randomY = (int) ( Math.random() * size);
			sudokuBoard[randomX][randomY] = 0;
		}
	}
	
	public int[][] getSudokuBoard() {
		return sudokuBoard;
	}

	public int[][] getSolvedSudokuBoard() {
		return solvedSudokuBoard;
	}

	public void setSudokuBoard(int[][] sudokuBoard) {
		this.sudokuBoard = sudokuBoard;
	}

	public int getEdgeLength() {
		return sudokuBoard.length;
	}
}
