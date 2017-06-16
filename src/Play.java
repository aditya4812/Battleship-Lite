import java.util.*;

/**
 * Play - A class that contains both the methods used to play the game and the
 * main driver which runs the methods
 * 
 */
public class Play {
	// declaring variables
	private static int difficulty = 10;
	private int numGuesses = 0;
	Scanner in = new Scanner(System.in);

	/**
	 * getDifficulty - A method that returns the difficulty of the game
	 * 
	 * @return difficulty (difficulty determines the board size)
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * getNumGuesses - A method that returns the number of guesses it took to
	 * beat the game
	 * 
	 * @return numGuesses (the number of guesses by the user)
	 */
	public int getNumGuesses() {
		return numGuesses;
	}

	/**
	 * setNumGuesses - A method to set the number of guesses by the user
	 * 
	 * @param numGuesses
	 *            the number of guesses by the user
	 */
	public void setNumGuesses(int numGuesses) {
		this.numGuesses = numGuesses;
	}

	/**
	 * act - A method that simulates a single turn by the user
	 * 
	 * @param myBoard
	 *            the two dimensional array upon which the user makes a move
	 */
	public void act(Board myBoard) {
		System.out.println("Displaying board");
		myBoard.displayBoard();
		int row = 0;
		int column = 0;
		int input = 0;
		System.out.print("Row: ");
		while (!in.hasNextInt() ) {
			in.nextLine();
			System.out.println("Please enter an integer");
			System.out.print("Row: ");
		}
		input = in.nextInt() -1;
		row = input;
		// checking if input is withing bounds
		while (input < 0 || input > myBoard.getDimensions() - 1){
			
			System.out.println("Row not on board, please enter in a valid row");
			input = 0;
			act(myBoard);
			input = in.nextInt() -1;
			row = input;
			if(row >=0 && row <=  myBoard.getDimensions() - 1){
				break;
			}
		 } 
			System.out.print("Column: ");
		while (!in.hasNextInt()) {
			in.nextLine();
			System.out.println("Please enter an integer");
			System.out.print("Column: ");
		}
		input = in.nextInt() - 1;
		column = input;
		// checking if input is within bounds
		while (input < 0 || input > myBoard.getDimensions() - 1) {
			input = 0;
			System.out.println("Column not on board, please enter in a valid row");
			act(myBoard);
			column = input;
			if(column >=0 && column <=  myBoard.getDimensions() - 1){
				break;
				
			}
		}
		System.out.println("You Guessed  (" + (row + 1) + "," + (column + 1) + ")");
		myBoard.guess(row, column);
		numGuesses++;
		if (myBoard.getHitCount() < 12) {
			act(myBoard);
		}
	}

	/**
	 * main - A method that runs the entire program and simulates the game of
	 * battleship
	 * 
	 * @param args
	 *            all the methods and variables
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Play myPlay = new Play();
		System.out.println("Please enter your level of difficulty beginning at 0");
		try {
			difficulty = sc.nextInt();
			if (difficulty < 0) {
				System.out.println(
						"Your difficulty level has been set to 10, please remember to follow instructions next time");
				difficulty = 10;
			}
		} catch (Exception e) {
			System.out.println(
					"Your difficulty level has been set to 10, please remember to follow instructions next time");
		}
		Board myBoard = new Board(difficulty);
		myBoard.printShipList();
		myBoard.fillComputerBoard();
		while (myBoard.invalidBoard()) {
			myBoard.emptyBoard();
			myBoard.fillComputerBoard();
		}
		while (myBoard.getHitCount() < 12) {
			myPlay.act(myBoard);
			for (int i = 0; i < myBoard.getDimensions()*3 + 5; i++) {
				System.out.print("*");

			}
			System.out.println();
		}
		myBoard.displayBoard();
		System.out.println("Congrats! You took " + myPlay.getNumGuesses()
				+ " tries to sink all the ships on a difficulty of " + myPlay.getDifficulty());
		System.out.print("Your hit percentage was: ");
		System.out.printf("%2.2f", (((double) (12) / (double) myPlay.getNumGuesses()))*100);
		System.out.println("%");
	}

}
