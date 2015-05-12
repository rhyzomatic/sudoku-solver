public class Puzzle {
	Square[] puzzle = new Square[81];
	K9[] rows = new K9[9];
	K9[] cols = new K9[9];
	K9[] boxes = new K9[9];

	public Puzzle (int[] input){
		for (int x = 0; x < 81; x++) { //Initialize all 81 squares, enter the input numbers
			puzzle[x] = new Square();
			if (0 < input[x]){
				puzzle[x].value = new Integer(input[x]);
				puzzle[x].poss.clear();
				puzzle[x].poss.add(new Integer(input[x]));
			}
		}

		int boxnum = 0;

		for (int x = 0; x < 9; x++) { //Initialize row, col and box objects, which contain different groupings of the 81 sqaures
			int[] rowarray = new int[9];
			int[] colarray = new int[9];
			int[] boxarray = new int[9];
			int rownum = x*9;
			int colnum = x;

			for (int y = 0; y < 9; y++){
				rowarray[y] = rownum;
				rownum++;

				colarray[y] = colnum;
				colnum = colnum + 9;

				boxarray[y] = boxnum;
				//System.out.println("Boxnum: " + boxnum);
				boxnum++;
				if (boxnum % 3 == 0){
					boxnum = boxnum + 6;
				}

			}
			rows[x] = new K9(0, x, rowarray, puzzle);
			cols[x] = new K9(1, x, colarray, puzzle);
			boxes[x] = new K9(2, x, boxarray, puzzle);

			boxnum = boxnum - 24;
			if (boxnum % 9 == 0){
				boxnum = boxnum + 18;
			}
		}
	}

	public boolean update (){
		boolean worked = false;
		for (int x = 0; x < 81; x++) {
			if (puzzle[x].value > 0){
				if (puzzle[x].removeValueFromGroups(rows, cols, boxes)){
					worked = true;
				}
			}
		}
		return worked;
	}
	public boolean lookForSolved (){
		boolean changed = false;
		for (int x = 0; x < 81; x++) {
			if(puzzle[x].checkIfSolved()){
				changed = true;
			}
		}
		return changed;
	}
	public boolean[] smartSolve (){ // For the boolean array returned, validity is [0], changed is [1]
		boolean valid = false;
		boolean changed = false;
		for (int x = 0; x < 9; x++) {
			boolean[] rowthing = rows[x].checkIfOnlyOneSquareCanPossiblyBeAValue(rows, cols, boxes);
			boolean[] colthing = cols[x].checkIfOnlyOneSquareCanPossiblyBeAValue(rows, cols, boxes);
			boolean[] boxthing = boxes[x].checkIfOnlyOneSquareCanPossiblyBeAValue(rows, cols, boxes);
			if (rowthing[0] || colthing[0] || boxthing[0]){
				valid = true;
				if (rowthing[1] || colthing[1] || boxthing[1]){
					changed = false;
				}
			}
		}
		return new boolean[]{valid, changed};
	}

	public boolean solved (){
		boolean solved = true;
		int x = 0;
		while (solved && x < 81) {
			if (puzzle[x].value < 1){
				solved = false;
			}
			x++;
		}
		return solved;
	}

	public int[] returnIntArray (){
		int[] thearray = new int[81];
		for (int x = 0; x < 81; x++) {
			thearray[x] = (int) puzzle[x].value;
		}
		return thearray;
	}

	public boolean isValid (){
		int x = 0;
		while (x < 9){
			if (!rows[x].isValid() || !cols[x].isValid() || !boxes[x].isValid()){
				return false;
			}
			x++;
		}
		return true;
	}
}