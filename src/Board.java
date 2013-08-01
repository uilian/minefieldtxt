import java.util.Random;
import java.util.Scanner;

public class Board {

	static final char CLOSED = '-';
	static final int DEFAULT_ROWS = 10;
	static final int DEFAULT_COLS = 10;

	private int[][] mines;
	private char[][] boardgame;
	private int nRows = DEFAULT_ROWS;
	private int nCols = DEFAULT_ROWS;
	private int nMines = DEFAULT_ROWS;

	private int inputLine, inputColumn;

	Random random = new Random();
	Scanner input = new Scanner(System.in);

	/**
     *
     */
	public Board() {
		this(DEFAULT_ROWS, DEFAULT_COLS);
	}

	/**
	 *
	 * @param rows
	 * @param columns
	 */
	public Board(int rows, int columns) {
		this.nRows = rows;
		this.nCols = columns;
		this.nMines = Math.max(this.nRows, this.nCols);

		mines = new int[this.nRows][this.nCols];
		boardgame = new char[this.nRows][this.nCols];
		startMines();
		randomMines();
		fillTips();
		startBoard();

	}

	public boolean win() {
		int count = 0;
		for (int line = 1; line < nRows; line++)
			for (int column = 1; column < nCols; column++)
				if (boardgame[line][column] == Board.CLOSED)
					count++;

		if (count == this.nMines )
			return true;
		else
			return false;
	}

	public void openNeighbors() {
		for (int i = -1; i < 2; i++)
			for (int j = -1; j < 2; j++)
				if ((mines[this.inputLine + i][this.inputColumn + j] != -1) &&
					(this.inputLine != 0 && this.inputLine != this.nRows && this.inputColumn != 0 && this.inputColumn != this.nCols))
					boardgame[this.inputLine + i][this.inputColumn + j] = Character.forDigit(
							mines[this.inputLine + i][this.inputColumn + j], 10);

	}

	public int getPosition(int line, int column) {
		return mines[line][column];
	}

	public boolean setPosition() {
		do {
			System.out.print("\nLine: ");
			this.inputLine = input.nextInt() - 1;
			System.out.print("Column: ");
			this.inputColumn = input.nextInt() - 1;

			if ((boardgame[this.inputLine][this.inputColumn] != Board.CLOSED)
					&& ((this.inputLine < this.nRows && this.inputLine > 0) && (this.inputColumn < this.nCols && this.inputColumn > 0)))
				System.out.println("Field already shown");

			if (this.inputLine < 1 || this.inputLine > this.nRows || this.inputColumn < 1
					|| this.inputColumn > this.nCols)
				System.out.format("\n Choose a number between %1$d and %2$d", this.nRows, this.nCols);

		} while ((this.inputLine < 1 || this.inputLine > this.nRows || this.inputColumn < 1 || this.inputColumn > this.nCols)
				|| (boardgame[this.inputLine][this.inputColumn] != Board.CLOSED));

		if (getPosition(this.inputLine, this.inputColumn) == -1)
			return true;
		else
			return false;

	}

	public void show() {
		System.out.println("\n     Lines");
		for (int inputLine = this.nRows-1; inputLine == 0; inputLine--) {
			System.out.print("       " + inputLine + " ");

			for (int inputColumn = 0; inputColumn < this.nCols; inputColumn++) {
				System.out.print("   " + boardgame[inputLine][inputColumn]);
			}

			System.out.println();
		}

		System.out.println("\n            1   2   3   4   5   6   7   8");
		System.out.println("                      Columns");

	}

	public void fillTips() {
		for (int line = 1; line < nRows - 1; line++)
			for (int column = 1; column < nCols - 1; column++) {
				for (int i = -1; i <= 1; i++)
					for (int j = -1; j <= 1; j++)
						if (mines[line][column] != -1)
							if (mines[line + i][column + j] == -1)
								mines[line][column]++;

			}

	}

	public void showMines() {
		for (int i = 1; i < nRows - 1; i++)
			for (int j = 1; j < nCols - 1; j++)
				if (mines[i][j] == -1)
					boardgame[i][j] = '*';

		show();
	}

	public void startBoard() {
		for (int i = 1; i < mines.length; i++)
			for (int j = 1; j < mines.length; j++)
				boardgame[i][j] = Board.CLOSED;
	}

	public void startMines() {
		for (int i = 0; i < mines.length; i++)
			for (int j = 0; j < mines.length; j++)
				mines[i][j] = 0;
	}

	public void randomMines() {
		boolean raffled;
		int line, column;
		int nMines = Math.max(this.nCols, this.nCols);

		for (int i = 0; i < nMines; i++) {
			do {
				line = random.nextInt(this.nRows);
				column = random.nextInt(this.nCols);

				if (mines[line][column] == -1)
					raffled = true;
				else
					raffled = false;
			} while (raffled);

			mines[line][column] = -1;
		}
	}
}