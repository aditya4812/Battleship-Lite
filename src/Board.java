import java.util.*;
import java.awt.*;

/**
 * Board - A class that creates a Board object, made out of a two dimensional
 * array with randomly created ships
 *
 */
public class Board {
	public int[][] myBoard;
	boolean spawnOnTop = false;
	public int difficulty = 0;
	public ArrayList<Ship> myShips = new ArrayList<Ship>(3);
	public Ship myCruiser;
	public Ship mySubmarine;
	public Ship myDestroyer;
	public int hitCount = 0;

	/**
	 * Board - A constructor that creates and initializes both the board, the
	 * ships, and the arraylist of ships
	 * 
	 * @param i
	 *            the difficulty which determines the board size
	 */
	public Board(int i) {
		difficulty = i;
		// initializing board size based on difficulty
		myBoard = new int[8 + (i * 2)][8 + (i * 2)];
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
		for (int row = 0; row < myBoard.length; row++) {
			for (int col = 0; col < myBoard[0].length; col++) {
				myBoard[row][col] = 0;
			}
		}
	}

	/**
	 * getHitCount - A method that returns the number of hits where each hit is
	 * a guess that strikes the same position as a ship
	 * 
	 * @return hitCount (the number of hits)
	 */
	public int getHitCount() {
		return hitCount;
	}

	/**
	 * getDimensions - A method that returns the number of rows or columns of
	 * the board (both are the same because the board is a square)
	 * 
	 * @return - myBoard.length (the length of the board)
	 */
	public int getDimensions() {
		return myBoard.length;
	}

	/**
	 * getBoard - A method that returns the board
	 * 
	 * @return myBoard (the two dimensional matrix)
	 */
	public int[][] getBoard() {
		return myBoard;
	}

	/**
	 * displayBoard - A method that displays each point on the board based on
	 * the integer values, should be implemented after each turn
	 * 
	 */
	public void displayBoard() {
		// print headers for rows
		System.out.print("   ");
		for (int i = 0; i < myBoard[0].length; i++) {
			System.out.printf("%3s", i + 1);
		}
		System.out.println();
		System.out.println();
		// print out columns with column headers
		for (int row = 0; row < myBoard.length; row++) {
			System.out.printf("%3s", row + 1);
			for (int col = 0; col < myBoard[0].length; col++) {
				if (myBoard[row][col] == -1) {
					// prints a blank if miss
					System.out.printf("%3s", " ");
				} else if (myBoard[row][col] < -1) {
					// prints the hit ships with red X's
					System.out.printf("%3s", "X");
				} else if (myBoard[row][col] >= 0) {
					// prints the available spaces with black O's
					System.out.printf("%3s", "O");
				}
			}
			System.out.println();
			System.out.println();
		}
	}

	/**
	 * fillComputerBoard - A method that recursively fills the computer board by
	 * randomly spawning ships and rotating the board
	 */
	public void fillComputerBoard() {
		spawnOnTop = false;
		ArrayList<Integer> positions = new ArrayList<Integer>(3);
		ArrayList<Integer> colPositions = new ArrayList<Integer>(3);
		// ships are shuffled in the array
		ArrayList<Ship> tempShips = new ArrayList<Ship>(3);
		Random rand = new Random();
		// generate random integers for rows
		int first = 0;
		int mid = rand.nextInt(myBoard[0].length - 1) + 1;
		if (mid == 1) {
			first = 0;
		} else {
			first = rand.nextInt(mid - 1) + 1;
		}
		int last = rand.nextInt(myBoard[0].length - mid) + mid;
		// special recursion case if mid value equals last value of the array
		if (mid == last) {
			fillComputerBoard();
		} else if (mid == myBoard.length) {
			fillComputerBoard();
		} else {
			positions.add(first);
			positions.add(mid);
			positions.add(last);
			// reshuffle the ship order
			int increment = rand.nextInt(2);
			for (int i = 2; i >= 1; i--) {
				int index = rand.nextInt(i);
				tempShips.add(myShips.get(index + increment));
				myShips.remove(index + increment);
			}
			tempShips.add(myShips.get(0));
			myShips = tempShips;
			// generate random integers for columns
			for (int i = 0; i <= 2; i++) {
				colPositions.add(rand.nextInt(myBoard[0].length - myShips.get(i).getSpaceCount()));
			}
			// assigning
			for (int row = 0; row <= 2; row++) {
				int start = positions.get(row);
				for (int col = colPositions.get(row); col < myShips.get(row).getSpaceCount()
						+ colPositions.get(row); col++) {
					if (myBoard[start][col] > 0) {
						spawnOnTop = true;
					}
					myBoard[start][col] = myShips.get(row).getSpaceCount();
				}
				// randomly rotates the ship board
				int odds = rand.nextInt(2);
				if (odds == 1) {
					rotateBoard();
				}
			}

		}

	}

	/**
	 * rotateBoard - A method that rotates the board by 90 degrees to the right
	 */
	public void rotateBoard() {
		int[][] temp = new int[myBoard.length][myBoard[0].length];
		for (int i = 0; i < myBoard[0].length; ++i) {
			for (int j = 0; j < myBoard.length; ++j) {
				temp[i][j] = myBoard[myBoard.length - j - 1][i];
			}
		}
		myBoard = temp;
	}

	/**
	 * invalidBoard - A method that returns a boolean value if two ships spawn
	 * on top of one another during rotation, main method will then generate a
	 * new board
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
	 * @param x
	 *            x-coordinate (col)
	 * @param y
	 *            y-coordinate (row)
	 */
	public void guess(int x, int y) {
		if (myBoard[x][y] == 3) {
			hitCount++;
			myCruiser.hit();
			myBoard[x][y] = -3;
			System.out.println("HIT");
			if (myCruiser.getHitCount() == 3) {
				System.out.println(myCruiser.sunkMessage());
			}
		}
		if (myBoard[x][y] == 4) {
			hitCount++;
			mySubmarine.hit();
			myBoard[x][y] = -4;
			System.out.println("HIT");
			if (mySubmarine.getHitCount() == 4) {
				System.out.println(mySubmarine.sunkMessage());
			}
		}
		if (myBoard[x][y] == 5) {
			hitCount++;
			myDestroyer.hit();
			myBoard[x][y] = -5;
			System.out.println("HIT");
			if (myDestroyer.getHitCount() == 5) {
				System.out.println(myDestroyer.sunkMessage());
			}
		}

		if (myBoard[x][y] == 0) {
			myBoard[x][y] = -1;
		}
	}

	/**
	 * printShipList - A method that prints the list of ships including their
	 * name and space count out for the user to see
	 */
	public void printShipList() {
		Iterator<Ship> iter = myShips.iterator();
		while (iter.hasNext()) {
			Ship temp = iter.next();
			System.out.println(temp.getShipType() + " has " + temp.getSpaceCount() +" spaces.");

		}

	}
}
