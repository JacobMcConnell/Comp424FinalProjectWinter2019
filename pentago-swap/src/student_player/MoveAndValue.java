package student_player;

import pentago_swap.PentagoMove;
/**
 * This is a class used to pair a Pentago move with the value of taking that move. That way the optimal value of a state can be calculated 
 * and the next move to make to get that value. 
 * @author jacobmcconnell
 *
 */
public class MoveAndValue {
	PentagoMove move;
	int value;
	MoveAndValue(){
		
	
	}
	/**
	 * 
	 * @param a
	 * @param b
	 * @return returns the MoveAndValue object with the min value
	 */
	public static MoveAndValue min(MoveAndValue a, MoveAndValue b) {
		if (a.value<b.value) {
			return a;
		}else {
			return b;
		}
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return returns the MoveAndValue object with the min value
	 */
	public static MoveAndValue max(MoveAndValue a, MoveAndValue b) {
		if (a.value>b.value) {
			return a;
		}else {
			return b;
		}
	}
	/**
	 * 
	 * @return a copy of the MoveAndValue object it is called on
	 */
	public MoveAndValue copy() {
		MoveAndValue copy= new MoveAndValue();
		copy.move=this.move;
		copy.value= this.value;
		return copy;
	}
	
	

}
