package student_player;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import boardgame.Move;

import pentago_swap.PentagoPlayer;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoMove;

/** A player file submitted by a student. */
public class StudentPlayer extends PentagoPlayer {
	public static int j=0;
	

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("----------");
    }
    

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(PentagoBoardState boardState) {
        // You probably will make separate functions in MyTools.
        // For example, maybe you'll need to load some pre-processed best opening
        // strategies...
       
       //Set the depth depending on how much of the game has been played. This is because as the board
        //gets filled the number of possible moves decreases so you can increase your depth without going over the time limit
       int depth=2;
       if (boardState.getTurnNumber()>7) {
    	   depth=3;
       }
       if (boardState.getTurnNumber()>15) {
    	   depth=4;
       }
        //Start the timer to make sure that we do not time out 
        MyTools.startTimer();
        // Call the algorithm from MyTools
        Move myMove2 = MyTools.minMaxAB(boardState, depth, -100000000 , 100000000, boardState.getTurnPlayer()==0).move;
       

        return myMove2;
    }
    
    
  
}