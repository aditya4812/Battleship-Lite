import java.util.*;
import java.awt.*;

/**
 * Board - A class that creates a Board object, made out of a two dimensional
 * array with randomly created ships
 *
 */
public class AutoBoard {
	public int[][] autoBoard;
	boolean spawnOnTop = false;
	public int difficulty = 0;
	public ArrayList<Ship> myShips = new ArrayList<Ship>(3);
	public Ship myCruiser;
	public Ship mySubmarine;
	public Ship myDestroyer;
	public int hitCount = 0;
	public int shipCreate = 0;
	public int[][] guessList; //list of all possible CPU guesses, so guesses aren't repeated
	public int guessCount=0;
	
	Scanner in = new Scanner(System.in);

	/**
	 * Board - A constructor that creates and initializes both the board, the ships,
	 * and the arraylist of ships
	 * 
	 * @param i the difficulty which determines the board size
	 */
	public AutoBoard(int i) {
		difficulty = i;
		// initializing board size based on difficulty
		autoBoard = new int[8 + (i * 2)][8 + (i * 2)];
		guessList = new int[8 + (i * 2)][8 + (i * 2)];
		for (int row = 0; row < guessList.length; row++) {
			for (int col = 0; col < guessList[0].length; col++) {
				guessList[row][col] = 1;
			}
		}
		// creating ship objects and adding to arraylist
		myCruiser = new Cruiser();
		mySubmarine = new Submarine();
		myDestroyer = new Destroyer();
		myShips.add(myCruiser);
		myShips.add(mySubmarine);
		myShips.add(myDestroyer);
		// initializing empty board
		emptyBoard();
	}

	/**
	 * emptyBoard - A method that initializes all points in the empty board to 0
	 */
	public void emptyBoard() {
		for (int row = 0; row < autoBoard.length; row++) {
			for (int col = 0; col < autoBoard[0].length; col++) {
				autoBoard[row][col] = 0;
			}
		}
	}

	/**
	 * getHitCount - A method that returns the number of hits where each hit is a
	 * guess that strikes the same position as a ship
	 * 
	 * @return hitCount (the number of hits)
	 */
	public int getHitCount() {
		return hitCount;
	}

	/**
	 * getDimensions - A method that returns the number of rows or columns of the
	 * board (both are the same because the board is a square)
	 * 
	 * @return - autoBoard.length (the length of the board)
	 */
	public int getDimensions() {
		return autoBoard.length;
	}

	/**
	 * getBoard - A method that returns the board
	 * 
	 * @return autoBoard (the two dimensional matrix)
	 */
	public int[][] getBoard() {
		return autoBoard;
	}

	public int[][] getGuessList() {
		return guessList;
	}

	

	/**
	 * displayBoard - A method that displays each point on the board based on the
	 * integer values, should be implemented after each turn
	 * 
	 */
	public void displayBoard() {
		// print headers for rows
		System.out.print("   ");
		for (int i = 0; i < autoBoard[0].length; i++) {
			System.out.printf("%3s", i + 1);
		}
		System.out.println();
		System.out.println();
		// print out columns with column headers
		for (int row = 0; row < autoBoard.length; row++) {
			System.out.printf("%3s", row + 1);
			for (int col = 0; col < autoBoard[0].length; col++) {
				if (autoBoard[row][col] == -1) {
					// prints a blank if miss
					System.out.printf("%3s", " ");
				} else if (autoBoard[row][col] < -1) {
					// prints the hit ships with red X's
					System.out.printf("%3s", "X");
				} else if (autoBoard[row][col] >= 0) {
					// prints the available spaces with black O's
					System.out.printf("%3s", "O");
				}
			}
			System.out.println();
			System.out.println();
		}
	}

	/**
	 * fillComputerBoard - A method that asks user for ship input and fills the
	 * board
	 */
	// x,y are user inputs, z is ship length
	public void fillComputerBoard(int x, int y, int z) {
		shipCreate++;
		x--;
		y--;
		if (shipCreate == 1 || shipCreate == 3) {
			for (int a = 0; a < z; a++) {
				autoBoard[x + a][y] = z;
			}
		} else {
			for (int a = 0; a < z; a++) {
				autoBoard[x][y + a] = z;
			}
		}

	}

	public void autoAct() {
		emptyBoard();
		System.out.println("Please enter a row for the Cruiser: ");
		int row = in.nextInt();
		System.out.println("Please enter a column: ");
		int col = in.nextInt();
		fillComputerBoard(row, col, 3);

		System.out.println("Please enter a row for the Sub: ");
		row = in.nextInt();
		System.out.println("Please enter a column: ");
		col = in.nextInt();
		fillComputerBoard(row, col, 4);

		System.out.println("Please enter a row for the Destroyer: ");
		row = in.nextInt();
		System.out.println("Please enter a column: ");
		col = in.nextInt();
		fillComputerBoard(row, col, 5);

	}

	/**
	 * rotateBoard - A method that rotates the board by 90 degrees to the right
	 */
	public void rotateBoard() {
		int[][] temp = new int[autoBoard.length][autoBoard[0].length];
		for (int i = 0; i < autoBoard[0].length; ++i) {
			for (int j = 0; j < autoBoard.length; ++j) {
				temp[i][j] = autoBoard[autoBoard.length - j - 1][i];
			}
		}
		autoBoard = temp;
	}

	/**
	 * invalidBoard - A method that returns a boolean value if two ships spawn on
	 * top of one another during rotation, main method will then generate a new
	 * board
	 * 
	 * @return spawnOnTop (true if two ships share a point, false if not)
	 */
	public boolean invalidBoard() {
		return spawnOnTop;
	}

	/**
	 * guess - A method that takes in the user's guess and changes the board's
	 * values based on the guess
	 * 
	 * @param x x-coordinate (col)
	 * @param y y-coordinate (row)
	 */
	public void guess(int x, int y) {
		if (autoBoard[x][y] == 3) {
			hitCount++;
			myCruiser.hit();
			autoBoard[x][y] = -3;
			System.out.println("HIT");
			if (myCruiser.getHitCount() == 3) {
				System.out.println(myCruiser.sunkMessage());
			}
		}
		if (autoBoard[x][y] == 4) {
			hitCount++;
			mySubmarine.hit();
			autoBoard[x][y] = -4;
			System.out.println("HIT");
			if (mySubmarine.getHitCount() == 4) {
				System.out.println(mySubmarine.sunkMessage());
			}
		}
		if (autoBoard[x][y] == 5) {
			hitCount++;
			myDestroyer.hit();
			autoBoard[x][y] = -5;
			System.out.println("HIT");
			if (myDestroyer.getHitCount() == 5) {
				System.out.println(myDestroyer.sunkMessage());
			}
		}

		if (autoBoard[x][y] == 0) {
			autoBoard[x][y] = -1;
		}
	}

	/**
	 * printShipList - A method that prints the list of ships including their name
	 * and space count out for the user to see
	 */
	public void printShipList() {
		Iterator<Ship> iter = myShips.iterator();
		while (iter.hasNext()) {
			Ship temp = iter.next();
			System.out.println(temp.getShipType() + " has " + temp.getSpaceCount() + " spaces.");

		}

	}
}
