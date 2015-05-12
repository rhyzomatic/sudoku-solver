import java.util.*;

public class Square {
	Set<Integer> poss = new HashSet<Integer>();
	Integer value = 0;
	int[] group = new int[3];

	Square() {
		for (int x = 1; x < 10; x++) {
			poss.add(x);
		}
	}

	public boolean checkIfSolved(){
		if (value == 0 && poss.size() == 1){
			value = poss.iterator().next();
			return true;
		}
		else {
			return false;
		}
	}
	public boolean removeValueFromGroups(final K9[] rows, final K9[] cols, final K9[] boxes){
		if (rows[group[0]].removePoss(value) || cols[group[1]].removePoss(value) || boxes[group[2]].removePoss(value)){
			return false;
		}
		else {
			return true;
		}
	}
}