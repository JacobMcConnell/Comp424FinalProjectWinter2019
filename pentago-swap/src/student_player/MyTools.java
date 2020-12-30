package student_player;

import java.io.IOException;
import java.util.ArrayList;

import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoMove;

/**
 * This class contains helper methods and the majority of the logic of the PentagoSwap agent. 
 * It is also where the majority of the coding for this final project was done. 
 * @author jacobmcconnell
 *
 */
public class MyTools {
	
	private static long timer;
	
	




	
    public static double getSomething() {


        return Math.random();
    }
    
    
    /**
     * this is the evaluation method used by alphabetaNEW 
     * it uses the howCloseToWinning Heuristic 
     * @param node
     * @return value of node including outright win 
     */
    public static int evaluationMethod(PentagoBoardState node) {
    		if (node.gameOver()) {
    			
    			if (node.getWinner()==0) {
    				//white one
    				return  10000000; 
    			} else {
    				return -10000000;
    			}
    		} else {
    			// calling hueristic below
    			return howCloseToWinning(node);
    		}
    	
    }  
    
    
    
   
    
    
    /**
     * 
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param boardState
     * @return an array with the number of black tiles and white tiles between the start and end points count={numWhites, numBlacks}
     */
    public static int[] countBlacksAndWhitesDiagonal(int startX, int startY, int endX, int endY, PentagoBoardState boardState) {
    	 int[] count = new int[2];
    	 
    	 int numWhites=0;
    	    int numBlacks=0;
    	    
    	    for (int i=0;i<5; i++){
    	    	if  (endY>startY){
    	    		PentagoBoardState.Piece	currentPiece = boardState.getPieceAt(startX+i,startY+i);
    	    		if(currentPiece==PentagoBoardState.Piece.WHITE) {
    	    			numWhites++;
    	    		}
    	    		if(currentPiece==PentagoBoardState.Piece.BLACK) {
    	    			numBlacks++;
    	    		} 
    	    	}else{
    	    	//going down 
    	    		PentagoBoardState.Piece	currentPiece = boardState.getPieceAt(startX+i,startY-i);
    	    	    if(currentPiece==PentagoBoardState.Piece.WHITE) {
    	    	    	numWhites++;
    	    	    }
    	    	    if(currentPiece==PentagoBoardState.Piece.BLACK) {
    	    	    	numBlacks++;
    	    	    }
    	    	}
    	  }
    	    count[0]=numWhites;
    	    count[1]=numBlacks;
    	 return count; 
    	 
    }
    
    /**
     * 
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param boardState
     * @return an array with the number of black tiles and white tiles between the start and end points count={numWhites, numBlacks}
     */
    public static int[] countBlacksWhitesVerticle(int startX, int startY, int endX, int endY, PentagoBoardState boardState) {
    	int [] counts = new int[2];
    	int numWhites=0;
 	int numBlacks=0;
    	
    	for (int i=0 ; i<(endY+1-startY); i++) {
    		PentagoBoardState.Piece	currentPiece = boardState.getPieceAt(startX,startY+i);
    		if(currentPiece==PentagoBoardState.Piece.WHITE) {
    			numWhites++;
    		}
    		if(currentPiece==PentagoBoardState.Piece.BLACK) {
    			numBlacks++;
    		} 
    		
    	}
    	counts[0]=numWhites;
    	counts[1]=numBlacks;
   
    	return counts;
    }
    
    /**
     * 
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param boardState
     * @return an array with the number of black tiles and white tiles between the start and end points count={numWhites, numBlacks}
     */
    public static int[] countBlacksWhitesHorizontal(int startX, int startY, int endX, int endY, PentagoBoardState boardState) {
    	int [] counts = new int[2];
    	int numWhites=0;
 	int numBlacks=0;
    	
    	for (int i=0 ; i<(endX+1-startX); i++) {
    		//boardState.getp
    		PentagoBoardState.Piece	currentPiece = boardState.getPieceAt(startX+i,startY);
    		if(currentPiece==PentagoBoardState.Piece.WHITE) {
    			numWhites++;
    		}
    		if(currentPiece==PentagoBoardState.Piece.BLACK) {
    			numBlacks++;
    		} 
    		
    	}
    	counts[0]=numWhites;
    	counts[1]=numBlacks;
   
    	return counts;
    }
    
    /**
     * This gives a value for a region of the board. If its mixed its zero. If it has one or the other the number of that 
     * kind increases or decreased the value respectively. 
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param boardState
     * @return value
     */
    public static int valueOfWayToWin(int startX, int startY, int endX, int endY, PentagoBoardState boardState) {
    int[] counts;
    if(startY==endY) {
    	counts= countBlacksWhitesHorizontal(startX, startY, endX, endY, boardState);
    	// horizontal 
    } else if(startX==endX) {
    	//vertical
    	counts= countBlacksWhitesVerticle(startX, startY, endX, endY, boardState);
    } else {
    	//diagonal 
    	counts= countBlacksAndWhitesDiagonal(startX, startY, endX, endY, boardState);
    	
    }
    
    int numWhites=counts[0];
    int numBlacks=counts[1];
    
    
    	
    	if ((numWhites>0)&&(numBlacks>0)) {
    		
    		return 0;
    	} else {
    		if(numWhites>0) {
    			return (int)Math.pow(2, numWhites); //used 2^num so that 4 is a lot more valuable than 2 since it is close to winning. 
    		} else if(numBlacks>0) {
    			return (-1*(int) Math.pow(2, numBlacks));
    		} else {
    			return 0; 
    		}
    	}

    	}
    /**
     * 
     * @return all the possible regions of the board that could be used to win 
     */
    public static int[][][] waysToWin(){
     	int[][][] waysToWin = new int[32][2][2];
    	
    	// vertical ways to win
    	for (int i=0;i<6;i++) {
    		int[][] wayToWin= {{i,0},{i,4}};
    		waysToWin[i]= wayToWin;
    	}
    	for(int i=6;i<12;i++) {
    		int[][] wayToWin= {{i-6,1},{i-6,5}};
    		waysToWin[i]= wayToWin;
    		
    	}
    	// horizontal ways to win 
    	for (int i=12; i<18;i++) {
    		int[][] wayToWin= {{0,i-12},{4,i-12}};
    		waysToWin[i]= wayToWin;
    		
    	}
    for (int i=18; i<24;i++) {
    		int[][] wayToWin= {{1,i-18},{5,i-18}};
    		waysToWin[i]= wayToWin;
    		
    	}
    
    // conventional diagonals
    int[][] wayToWin= {{0,0},{4,4}};
    waysToWin[24]= wayToWin;
    int[][] wayToWin1= {{1,1},{5,5}};
    waysToWin[25]= wayToWin1;
    int[][] wayToWin2= {{0,5},{4,1}};
    waysToWin[26]=wayToWin2;
    
    int[][] wayToWin3= {{1,4},{5,0}};
    waysToWin[27]=wayToWin3;
    
    
    // non conventional diagonals 
    int[][] wayToWin4= {{0,4},{4,0}};
    waysToWin[28]=wayToWin4;
    
    int[][] wayToWin5= {{1,5},{5,1}};
    waysToWin[29]=wayToWin5;
    
    
    int[][] wayToWin6= {{0,1},{4,5}};
    waysToWin[30]=wayToWin6;
    
    int[][] wayToWin7= {{1,0},{5,4}};
    waysToWin[31]=wayToWin7;
    
    
    
    
    
    return waysToWin;
    	
    }
    	
   
    /**
     *  this one checks all the different ways you can win (0,1 to 0,5; and 0,0 and 0,4; ect ) 
     *  if a way has mixed colors its value is zero
     *  if a way has only one color its value is 
     * @param boardState
     * @return a int value estimateing the utility of the board 
     */
    public static int howCloseToWinning(PentagoBoardState boardState) {
    	int[][][] waysToWin = waysToWin();
    	int value= 0; 
    	
    	for (int i=0;i<32;i++) { // for each way to win
    		int[][] wayToWin= waysToWin[i];
    		int[] start = wayToWin[0];
    		int[] end = wayToWin[1];
    		
    		value+= valueOfWayToWin(start[0], start[1], end[0],end[1], boardState); //evaluate its current value and sum it
    	}

    	
    	return value; //return the sum of the values of all the ways to win
    } 
    /// end of how close to winning heuristic 
    
    /**
     * returns the minimum of a and b
     * @param a
     * @param b
     * @return min of a nad b 
     */
    public static int min(int a, int b) {
    	int a0= a;
    	int b0 = b;
    	if (a0<b0) {
    		return a;
    	} else {
    		return b;
    	}

    }
    
    public static Object[] min(Object[] a, Object[] b) {
    	int a0= (int)a[0];
    	int b0 = (int)b[0];
    	if (a0<b0) {
    		return a;
    	} else {
    		return b;
    	}

    }
    
    
    
    
    public static int max(int a, int b) {
    	int a0= a;
    	int b0 = b;
    	if (a0>b0) {
    		return a;
    	} else {
    		return b;
    	}

    }
    
    public static Object[] max(Object[] a, Object[] b) {
    	int a0= (int)a[0];
    	int b0 = (int)b[0];
    	if (a0>b0) {
    		return a;
    	} else {
    		return b;
    	}

    }
    
    
   
    
    /**
     * This method takes the current pentago board state  for node, the desired depth at which the heuristic is called, alpha is a minimum value, 
     * beta is a maximum value, isMax represents whether we are trying to maximize or minimize the value of the board at this step. 
     * This method simply takes alpha and beta and turns them into MoveAndValues and then calls alphaBetaNEW with them 
     * since alphaBetaNew requires MoveAndValue objects as inputs. 
     * @param node
     * @param depth
     * @param alpha
     * @param beta
     * @param isMax
     * @return a MoveAndValue which has the minimized or maximized value for this board and the moved that achieves that. 
     */
    public static MoveAndValue minMaxAB(PentagoBoardState node, int depth, int alpha, int beta, boolean isMax) {
    		MoveAndValue A= new MoveAndValue();
    		A.value=alpha;
    		MoveAndValue B= new MoveAndValue();
    		B.value=beta;
    		return alphaBetaNEW(node, depth,A, B, isMax);
    	
    }
    
    /**
     * This method contains the "meat" of the PentagoSwap agent. It uses the alpha beta pruning method
     * with a heuristic. 
     * @param node
     * @param depth
     * @param alpha
     * @param beta
     * @param isMax
     * @return a the MoveAndValue object with the minimum or maximum value and the move to get that value
     */
    public static  MoveAndValue alphaBetaNEW(PentagoBoardState node, int depth, MoveAndValue alpha, MoveAndValue beta, boolean isMax) {
    		boolean gameOver= node.gameOver(); //Check if the game is over
    		boolean timeOut= ((System.currentTimeMillis()-timer)>1850); //check if we have run out of time to keep within time limit
    		if (((depth==0)||(gameOver))||timeOut) { //if we have reached the target depth, or if the game is over or if we have run out of time we halt evaluate the board and return the value (as a MoveAndValue object)
    			MoveAndValue mv = new MoveAndValue();
    			mv.value= evaluationMethod(node); //evaluate the current state
    			mv.move =null; //bottom level so the move does not matter
    			return mv;
    		}
    		//otherwise we continue with the alphabeta algorithm
    		
    		
    		if(isMax) {
    			//maximizing player
    			
    			for(PentagoMove move: node.getAllLegalMoves()) { //for each possible move
    				PentagoBoardState newNode= (PentagoBoardState) node.clone();// cloned to avoid modifying original node at this level 
    				newNode.processMove(move); //process the move on the new clone so that we have the board that would result from picking this move
    				MoveAndValue  result = alphaBetaNEW(newNode, depth-1, alpha.copy(), beta.copy(), false); //recursively call alphaBetaNEW on this to find the worst value the oposing player could force
    				result.move=move; //set the move for this result to be the current move we are investigating 
    				if (result.value>alpha.value) { // if we find a better move update
    					alpha.move=result.move;
    					alpha.value=result.value;
    				}
    				if (alpha.value>=beta.value) { // if alpha is greater than beta we prune because opposing player would not allow this branch to be chosen 
    					if (alpha.move==null) { //this may not be necessary but i included it to prevent crashes if i didn't return a move just in case
    						alpha.move =(PentagoMove) node.getRandomMove();
    						
    					}
    					return alpha; //return alpha if prunned
    				}	
    				
    			}
    			if (alpha.move==null) { //back up to insure we never return no move so that we never crash just in case
					alpha.move =(PentagoMove) node.getRandomMove();
			}
    			
    			return alpha; //return alpha after searching through possible moves 
    			
    			
    			
    		}else {
    			//minimizing player
    			//many of the notes above (for the maximizing player) apply here as well
    			for(PentagoMove move: node.getAllLegalMoves()) {
    				PentagoBoardState newNode= (PentagoBoardState) node.clone();
				newNode.processMove(move);
				MoveAndValue  result = alphaBetaNEW(newNode, depth-1, alpha.copy(), beta.copy(), true);
				result.move=move;
				if (result.value<beta.value) { // if value is lower update beta 
					beta.move=result.move;
					beta.value=result.value;
				}
				if (alpha.value>=beta.value) { //if alpha is greater than beta prune because opposing player would not allow this branch to be chosen 
					if (beta.move==null) {
						beta.move =(PentagoMove) node.getRandomMove();
						
					}
					return beta;
				}	
				
			}
			if (beta.move==null) {
				beta.move =(PentagoMove) node.getRandomMove();
			}
			
			return beta; //return beta after searching through all possible moves
    			
    		}
    		
    }
    
    
   
    
    /**
     *  this method sets the start time and is called before running the agent to insure the agent does not take too long. 
     */
	public static void startTimer() {
		timer =System.currentTimeMillis();
		// TODO Auto-generated method stub
		
	}

	 
	
}