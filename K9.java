import java.util.*;

public class K9 {
	Square[] squares = new Square[9];
	int[] squareindexes;
	int typeofgroup;
	int numofgroup;

	public K9 (int inputtypeofgroup, int inputnumofgroup, int[] inputsquares, final Square[] puzzle){
		squareindexes = inputsquares;
		typeofgroup = inputtypeofgroup;
		numofgroup = inputnumofgroup;
		for (int x = 0; x < 9; x++) {
			squares[x] = puzzle[inputsquares[x]];
			squares[x].group[typeofgroup] = numofgroup;
		}
	}

	public boolean removePoss (int possibility){
		boolean notworked = true;
		for (int x = 0; x < 9; x++) {
			if (squares[x].value != possibility){
				squares[x].poss.remove(new Integer(possibility));
			}
			else {
				notworked = false;
			}
		}
		return notworked;
	}
	public boolean[] checkIfOnlyOneSquareCanPossiblyBeAValue (final K9[] rows, final K9[] cols, final K9[] boxes){ // For the boolean array returned, validity is [0], changed is [1]
		boolean valid = true;
		boolean changed = false;
		for (int x = 0; x < 9; x++) {
			int posscount = 0;
			int goodsquare = 10;
			for (int y = 0; y < 9; y++) {
				if (squares[y].poss.contains(x + 1) && posscount < 2){
					posscount++;
					goodsquare = y; 
				}
			}
			if (goodsquare == 10){
				valid = false; // An error hath occured! This means that the puzzle is invalid
			}
			if (posscount < 2 && valid){
				if (squares[goodsquare].value == 0){
					squares[goodsquare].value = x + 1;
					squares[goodsquare].poss.clear();
					squares[goodsquare].poss.add(x + 1);
					squares[goodsquare].removeValueFromGroups(rows, cols, boxes);
					changed = true;
				}
			}
			
		}
		return new boolean[]{valid, changed};
	}

	public boolean isValid(){
		boolean isvalid = true;
		Set<Integer> numberspresent = new HashSet<Integer>();
		int x = 0;
		while (x < 9 && isvalid) {
			Integer value = squares[x].value;
			if (value > 0){
				if (numberspresent.contains(value)){
					isvalid = false;
				}
				else {
					numberspresent.add(value);
				}
			}
			x++;
		}
		return isvalid;
	}
} 