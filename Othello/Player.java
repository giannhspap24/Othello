package Reversi;

import java.util.ArrayList;
import java.util.Random;

import Reversi.Tile.States;

public class Player {

	private int maxDepth;
	private Tile.States playerColor;
	private Player opponent;
	
	public Player() {
		this(2, States.WHITE);
	}
	
	public Player (States color) {
		playerColor = color;
	}
	
	public Player(int maxDepth, Tile.States playerColor) {
		this.maxDepth = maxDepth;
		this.playerColor = playerColor;
		opponent = new Player(States.valueOf(Main.opponentsColor));
	}
	
	public int[] MiniMax (Board board) {
		
		int[] move = max(new Board(board), 0);
		return move;
	}
	
	public int[] max (Board board, int depth) {
		//System.out.println("max, depth = " + depth);
		Random random = new Random();
		if ((board.isTerminal() || depth == maxDepth)) {
			// C H E C K ! ! ! ! ! 
			int[] lastMove = board.getLastMove();	
			return lastMove;
		}
		ArrayList<Board> children = new ArrayList<Board>(board.getChildren(playerColor));
		int[] maxMove = new int[] {-1, -1, -1, -1, -1, (Integer.MIN_VALUE)};
		for (Board child: children) {
			int[] move = min(child, depth + 1);
			if(move[5] >= maxMove[5]) {
				if (move[5] == maxMove[5]) {
					if (random.nextInt(2) == 0) {
						for (int i = 0; i < maxMove.length; i++) {
                    		maxMove[i] = child.getLastMove()[i];
                    	}
					}
				}else {
					for (int i = 0; i < maxMove.length; i++) {
                		maxMove[i] = child.getLastMove()[i];
                	}
				}
			}
		}
		return maxMove;
	}
	
	public int[] min (Board board, int depth) {
		//System.out.println("min, depth = " + depth);

		Random random = new Random();
		if((board.isTerminal()) || (depth == maxDepth))
		{
			int[] lastMove = board.getLastMove();
			return lastMove;
		}
		ArrayList<Board> children = new ArrayList<Board>(board.getChildren(opponent.playerColor));
		int[] minMove = new int[] {-1, -1, -1, -1, -1, Integer.MAX_VALUE};
		for (Board child: children) {
			int[] move = max(child, depth + 1);
			if(move[5] <= minMove[5]){
                if ((move[5] == minMove[5])){
                    if (random.nextInt(2) == 0) {
                    	for (int i = 0; i < minMove.length; i++) {
                    		minMove[i] = child.getLastMove()[i];
                    	}
                    }
                }else{
                	for (int i = 0; i < minMove.length; i++) {
                		minMove[i] = child.getLastMove()[i];
                	}
                }
            }
        }
        return minMove;
	}
	
}
