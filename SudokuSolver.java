import java.util.*;

public class SudokuSolver {
	public static void main(String[] args) {
		//Scanner scannerInput = new Scanner(System.in);
		long startTime = System.nanoTime();
		/*int[] input = new int[81];
		for (int x = 0; x < 81; x++) {
			input[x] = scannerInput.nextInt();
		}*/

		int[] input = new int[]{ // Richard's six star
			6,9,0,3,0,0,0,8,0,
			0,0,7,0,0,2,0,0,0,
			0,0,0,0,7,0,5,0,3,
			0,0,9,0,0,8,0,3,0,
			1,6,0,0,0,0,0,2,5,
			0,2,0,4,0,0,1,0,0,
			9,0,2,0,6,0,0,0,0,
			0,0,0,1,0,0,2,0,0,
			0,7,0,0,0,5,0,6,4
		};

		long realStartTime = System.nanoTime();

		IntelliGuessObj finalPuzzle = intelliGuess(input);

		long stopTime = (System.nanoTime() - realStartTime)/1000;

		int squaresSolved = 0;

		for (int x = 0; x < 81; x++) {
			if (x % 9 == 0){
				System.out.println("");
			}
			System.out.print(" " + finalPuzzle.outputIntArray[x]);
			if (finalPuzzle.outputIntArray[x] > 0){ // Counter to see how many squares are solved. However, this is completely unnecessary since
				squaresSolved++;					// the intelliGuess function only returns an output if all squares have been solved.
			}
		}

		System.out.println("\n");

		System.out.println("Number of squares solved: " + squaresSolved);
		System.out.println("Elapsed time is " + (System.nanoTime() - startTime)/1000 + " microseconds (actual is " + stopTime + ")");
		
		//scannerInput.close();
	}

	public static IntelliGuessObj intelliGuess (int[] input){ // Recursive method making intelligent functions
		Puzzle startpuzzle = new Puzzle(input);

		boolean[] statusarray = new boolean[]{true, true}; // Validity is [0], changed is [1]

		IntelliGuessObj newintelliguess = new IntelliGuessObj();

		while (statusarray[0] && statusarray[1]){ // Flush out puzzle using easy algorithms
			startpuzzle.update();
			boolean changed = true;
			while (changed){
				changed = startpuzzle.lookForSolved();
				startpuzzle.update();
			}
			statusarray = startpuzzle.smartSolve();
			if (!statusarray[0]){
				newintelliguess.worked = false;
				return newintelliguess;
			}
		}

		if (!startpuzzle.isValid()){
			newintelliguess.worked = false;
			return newintelliguess;
		}

		if (startpuzzle.solved()){
			newintelliguess.outputIntArray = startpuzzle.returnIntArray();
			newintelliguess.worked = true;
			return newintelliguess;
		}

		else {
			int guessindex = -1;
			int possthreshold = 2;

			while (guessindex == -1){ // Find the new guess
				int index = 0;
				while (index < 81) {
					if (startpuzzle.puzzle[index].value == 0 && startpuzzle.puzzle[index].poss.size() <= possthreshold){
						guessindex = index;
					}
					index++;
				}
				possthreshold++;
			}

			Set<Integer> guesses = startpuzzle.puzzle[guessindex].poss;

			int[] newinput = startpuzzle.returnIntArray();

			newintelliguess.worked = false;

			int x = 0;

			for (Integer guess : guesses){
				int temp = newinput[guessindex];
				newinput[guessindex] = guess;
				newintelliguess = intelliGuess(newinput); // Make guess, calling the intelliGuess method again
				if (newintelliguess.worked){
					break;
				}
				newinput[guessindex] = temp;
			}

			return newintelliguess;
		}
	}
}
