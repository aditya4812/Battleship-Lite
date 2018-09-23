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
	 * getNumGuesses - A method that returns the number of guesses it took to beat
	 * the game
	 * 
	 * @return numGuesses (the number of guesses by the user)
	 */
	public int getNumGuesses(AutoBoard a) {
		if (a.hitCount == 12) {
			return a.guessCount;
		} else {
			return numGuesses;
		}
	}

	/**
	 * setNumGuesses - A method to set the number of guesses by the user
	 * 
	 * @param numGuesses the number of guesses by the user
	 */
	public void setNumGuesses(int numGuesses) {
		this.numGuesses = numGuesses;
	}

	/**
	 * act - A method that simulates a single turn by the user
	 * 
	 * @param myBoard the two dimensional array upon which the user makes a move
	 */
	public void act(Board myBoard, AutoBoard auto) {
		System.out.println("Displaying CPU board");

		myBoard.displayBoard();
		int row = 0;
		int column = 0;
		int input = 0;
		System.out.print("Row: ");
		while (!in.hasNextInt()) {
			in.nextLine();
			System.out.println("Please enter an integer");
			System.out.print("Row: ");
		}
		input = in.nextInt() - 1;
		row = input;
		// checking if input is withing bounds
		while (input < 0 || input > myBoard.getDimensions() - 1) {

			System.out.println("Row not on board, please enter in a valid row");
			input = 0;
			act(myBoard, auto);
			input = in.nextInt() - 1;
			row = input;
			if (row >= 0 && row <= myBoard.getDimensions() - 1) {
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
			act(myBoard, auto);
			column = input;
			if (column >= 0 && column <= myBoard.getDimensions() - 1) {
				break;

			}
		}
		System.out.println("You Guessed  (" + (row + 1) + "," + (column + 1) + ")");
		myBoard.guess(row, column);
		numGuesses++;
		System.out.println("User Hit Count: " + myBoard.getHitCount());
		System.out.println("");
		System.out.println("");

//		else if (myBoard.getHitCount() < 12) {
//			cpuTurn(auto);
//			act(myBoard, auto);
//		}
	}

	// guess count doesn't match number of empty's + X's on board????
	public void cpuTurn(AutoBoard a) {
		System.out.println("Displaying your board");

		Random r = new Random();
		int rand1 = (r.nextInt(a.getDimensions()));
		int rand2 = (r.nextInt(a.getDimensions()));
		if (a.getGuessList()[rand1][rand2] != 0) {
			int cpux = rand1;
			int cpuy = rand2;
			a.guess(cpux, cpuy);
			a.displayBoard();
			System.out.println("CPU guessed: (" + (cpux + 1) + "," + (cpuy + 1) + ")");
			a.guessCount++;
			System.out.println("CPU Guess Count: " + a.guessCount);
			System.out.println("CPU Hit Count: " + a.getHitCount());
			System.out.println("");
			System.out.println("");
			a.guessList[rand1][rand2] = 0;
		} else {
			cpuTurn(a);
		}

//		System.out.println("Displaying your board");
//
//		Random r = new Random();
//		
//		int cpux = (r.nextInt((a.getDimensions() - 1) + 1) + 1);
//		int cpuy = (r.nextInt((a.getDimensions() - 1) + 1) + 1);
//		a.guess(cpux, cpuy);
//		a.displayBoard();
//		cpux = (r.nextInt((a.getDimensions() - 1) + 1) + 1);
//		cpuy = (r.nextInt((a.getDimensions() - 1) + 1) + 1);
//		System.out.println("CPU Hit Count: " + a.getHitCount());
//		System.out.println("");
//		System.out.println("");

	}

	/**
	 * main - A method that runs the entire program and simulates the game of
	 * battleship
	 * 
	 * @param args all the methods and variables
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
		AutoBoard auto = new AutoBoard(difficulty);

		auto.autoAct();
		Board myBoard = new Board(difficulty);
		myBoard.printShipList();
		System.out.println("");
		myBoard.fillComputerBoard();
		while (myBoard.invalidBoard()) {
			myBoard.emptyBoard();
			myBoard.fillComputerBoard();
		}
		while (myBoard.getHitCount() < 12 && auto.getHitCount() < 12) {
			myPlay.cpuTurn(auto);
			if (auto.getHitCount() != 12) {
				myPlay.act(myBoard, auto);
			}
			if (auto.getHitCount() == 12) {
				System.out.println("CPU won!");
			}
			if (myBoard.getHitCount() == 12) {
				System.out.println("User won!");
			}
			for (int i = 0; i < myBoard.getDimensions() * 3 + 5; i++) {
				System.out.print("*");

			}
			System.out.println();
		}

		System.out.println("Congrats! You took " + myPlay.getNumGuesses(auto)
				+ " tries to sink all the ships on a difficulty of " + myPlay.getDifficulty());
		System.out.print("Your hit percentage was: ");
		System.out.printf("%2.2f", (((double) (12) / (double) myPlay.getNumGuesses(auto))) * 100);
		System.out.println("%");
	}

}
