package Reversi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Reversi.Tile.States;

public class Board {

	public static final int dim = 8;
	public static int hasMoves = 0;
	private Tile.States lastColorPlayed;
	private int[] lastMove;
	Tile[][] matrix;
	final int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
	final int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};
	List <int[]> myTiles = new ArrayList<>(); 
	List <int[]> opponentTiles = new ArrayList<>();
	List <int[]> moves = new ArrayList<>();			/***
													 * Every array on the List moves, must contain 6 elements.
													 *[0]::original x dimension
													 *[1]::original y dimension
													 *[2]::number of direction (0:N, 1:NE, 2:E, 3:SE, 4:S, 5:SW, 6:W, 7:NW)
													 *[3]::final x dimension
													 *[4]::final y dimension
													 *[5]::score
													 *[6]::checked (0:false, 1:true)
													 */
	
	public static final int[][] MY = {
			{200, 0, 100, 100, 100,100,0,200},
			{0,0,10,10,10,10,0,0},
			{100,10,30,30,30,30,10,100},
			{100,10,30,30,30,30,10,100},
			{100,10,30,30,30,30,10,100},
			{100,10,30,30,30,30,10,100},
			{0,0,10,10,10,10,0,0},
			{200, 0, 100, 100, 100,100,0,200}
	};
	
	public Board () {
		lastMove = new int[] {-1, -1, -1, -1, -1, 0, 0};
		lastColorPlayed = States.EMPTY;
		matrix = new Tile [dim][dim];
		initBoard(matrix);	
	}
	
	public Board (Board board) {
		this.lastMove = board.lastMove;
		this.lastColorPlayed = board.lastColorPlayed;
		this.matrix = new Tile [dim][dim];
		copyBoard(matrix, board.matrix);
		this.myTiles.addAll(board.myTiles);
		this.opponentTiles.addAll(board.opponentTiles);
	}
	
	public void initBoard (Tile[][] board) {
		
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				Tile tl = new Tile();
				board [i][j] = tl;
			}
		}
		updateTiles(States.WHITE, dim/2 - 1, dim/2 - 1);
		updateTiles(States.BLACK, dim/2 - 1, dim/2);
		updateTiles(States.BLACK, dim/2, dim/2 - 1);
		updateTiles(States.WHITE, dim/2, dim/2);
		
	}
	
	public void copyBoard (Tile[][] target, Tile [][] source) {
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				target[i][j] = new Tile(source[i][j].getState());
			}
		}
	}
	
	private void updateTiles (States st, int x, int y) {
		if(this.matrix[x][y].getState() == States.EMPTY || this.matrix[x][y].getState() == States.LEGALMOVE) {
			if(Main.myColor.equalsIgnoreCase(st.name())) {
				myTiles.add(new int[] {x, y});
			}else if (Main.opponentsColor.equalsIgnoreCase(st.name())) {
				opponentTiles.add(new int[] {x, y});
			}
		}else if (Main.myColor.equalsIgnoreCase(this.matrix[x][y].getState().name())) {
			if(findPairInList(myTiles, x, y) < myTiles.size())
				opponentTiles.add(myTiles.remove(findPairInList(myTiles, x, y)));
		}else if (Main.opponentsColor.equalsIgnoreCase(this.matrix[x][y].getState().name())) {
			if (findPairInList(opponentTiles, x, y) < opponentTiles.size())
				myTiles.add(opponentTiles.remove(findPairInList(opponentTiles, x, y)));
		}
		this.matrix[x][y].setState(st);

	}
	
	public void printBoard() {

		System.out.println("------------------------------");
		System.out.print("  ");
		for(int k = 0; k < dim; k++) {
			System.out.print(k+" ");
		}
		System.out.println();

		for(int i = 0; i < dim; i++) {
			System.out.print(i+"|");
			for(int j = 0; j < dim; j++) {

				if(matrix[i][j].getState() == States.EMPTY) {
					System.out.print("_");
				}else if(matrix[i][j].getState() == States.WHITE) {
					System.out.print("W");
				}else if(matrix[i][j].getState() == States.BLACK) {
					System.out.print("B");
				}else if(matrix[i][j].getState() == States.LEGALMOVE) {
					System.out.print("*");
				}
				System.out.print("|");
			}
			System.out.println();
		}
		System.out.println();
		printScore();
	}

	public int [] getLastMove()
	{
		return lastMove;
	}
	
	public Tile.States getLastColorPlayed()
	{
		return lastColorPlayed;
	}

	public void setLastMove(int[] lastMove)
	{
		for (int i = 0; i < lastMove.length; i++) {
			this.lastMove[i] = lastMove [i];		
		}
	}

	public void setLastColorPlayed(Tile.States lastColorPlayed)
	{
		this.lastColorPlayed = lastColorPlayed;
	}
	
	public void setBoard (Tile[][] board) {
		copyBoard(matrix, board);
	}

	public int findPairInList(List<int[]> list, int x, int y) {
 		int i = 0;
		for (int[] arr: list) {
			if (arr[0] == x && arr[1] == y) {
				break;
			}
			i++;
		}
		return i;
 	 }
 	 
 	public int findPairInList(List<int[]> list, int x, int y, int k, int l) {
 		int i = 0;
		for (int[] arr: list) {
			if (arr[k] == x && arr[l] == y) {
				break;
			}
			i++;
		}
		return i;
 	 }
 	
 	public List<Integer> findPairInList(List<int[]> list, int x, int y, int start) {
 		int i = 0;
 		List <Integer> instances = new ArrayList<>();
		for (i = start; i < list.size(); i ++) {
			if (list.get(i)[3] == x && list.get(i)[4] == y) {
				instances.add(i);
			}
			//i++;
		}
		return instances;
 	 }

 	public List<int[]> readMove() {
		
		int[] opponentsAnswer = new int [2];
		List<int[]> opponentsMove = new ArrayList<>();
		int i;
		int moveIndex;
		String answer = "";
		System.out.println("It's your turn. Type the coordinates of the tile you choose with a space between them. E.g.: x y \nN O T E:: x value is the number of the row while y is the number of the column.");
		do {
			try {	
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));		
				answer = br.readLine();
				Scanner sc = new Scanner(answer);
				i = 0;
				while (sc.hasNext()) {
					if(sc.hasNextInt()) {
						opponentsAnswer[i] = sc.nextInt();
						i++;
					}
				}
				sc.close();
				
				moveIndex = findPairInList(moves, opponentsAnswer[0], opponentsAnswer[1], 3, 4);
				if(moveIndex < moves.size()) {
					System.out.println("Your move [" + opponentsAnswer[0] + ", " + opponentsAnswer[1] + "] "  + "is valid!");
					opponentsMove.add(moves.get(moveIndex));
					for (int j: findPairInList(moves, moves.get(moveIndex)[3], moves.get(moveIndex)[4], moveIndex+1)) {
						opponentsMove.add(moves.get(j));
					}
					break;
				}else {
					throw new IOException();
				}
				
			}catch (IOException e){
				System.err.println("The given coordinates [" + opponentsAnswer[0] + ", " + opponentsAnswer[1] + "] "  + "don't respond to a legal move. You can see your legal moves as asterisks (*) on the board. Please try again.");
			}catch (ArrayIndexOutOfBoundsException e) {
				System.err.println("You entered more than two values. Try again.");
			}
		} while (true);	
		return opponentsMove;
		
	}
 	
 	public void makeMove (Tile.States color, int[] move) {
	 		int offset = 0;
			int x = 0;
			int y = 0;
			int k = 0;
			updateTiles(color, move[3], move[4]);
			if (move[2] > 3) {
				offset = -4;
			}else {
				offset = 4;
			}
			x = move[3];
			y = move[4];
			k = move[2] + offset;
			while (x != move[0] || y != move[1]) {		
				x += dx[k];
				y += dy[k];
				if(x == move[0] && y == move[1])
					break;
				updateTiles(color, x, y);
			}
			lastMove = move;
	}
 	
 	public void makeMove (Tile.States color, List<int[]> moveList) {
 		
 		for (int[] move: moveList) {
	 		int offset = 0;
			int x = 0;
			int y = 0;
			int k = 0;
			if(move.equals(moveList.get(0))) {
				updateTiles(color, move[3], move[4]);
 			}
			if (move[2] > 3) {
				offset = -4;
			}else {
				offset = 4;
			}
			x = move[3];
			y = move[4];
			k = move[2] + offset;
			while (x != move[0] || y != move[1]) {		
				x += dx[k];
				y += dy[k];
				if(x == move[0] && y == move[1])
					break;
				updateTiles(color, x, y);
			}
			lastMove = move;		}
	}


 // @param color is the color of the player for any given instance
  	public void findLegalMoves (States color) {

 		clearMoves(); //clears List moves before filling it with current instant's legal moves.
 		States othercolor;
 		if (color.equals(States.WHITE)){
 			othercolor = States.BLACK;
 		} else {
 			othercolor = States.WHITE;
 		}
 		if (Main.myColor.equalsIgnoreCase(color.name())) {
 			findLegalMoves(myTiles, color, othercolor);
 		}else if (Main.opponentsColor.equalsIgnoreCase(color.name())) {
 			findLegalMoves(opponentTiles, color, othercolor);
 		}else {
 			System.err.println("Something is wrong. check method findlegalmoves.");
 		}
 		
  	}
  	
 	public void findLegalMoves (List<int[]> list, States color, States othercolor){
 		for (int[] tile: list) {
	 		int i = tile[0];
	 		int j = tile[1];
			for (int k = 0; k < 8; k++) {	//8 neighbors 
				int sx = dx[k];
				int sy = dy[k];
				// if you are not in the edge and if you have a neighbor of opposite color
				if((i+sx)<8 && (i+sx)>-1 && (j+sy)<8 && (j+sy)>-1) {
					while (matrix[i+sx][j+sy].getState().equals(othercolor)) {
						if ((i+sx+dx[k])<8 && (i+sx+dx[k])>-1 && (j+sy+dy[k])<8 && (j+sy+dy[k])>-1) { 	//go to next tile
							sx = sx + dx[k];
							sy = sy + dy[k];
						}else {		//out of bounds
							break;
						}
					}
					if (((i+sx)<8 && (i+sx)>-1 && (j+sy)<8 && (j+sy)>-1) && matrix[i+sx-dx[k]][j+sy-dy[k]].getState().equals(othercolor)) {
						if (matrix[i+sx][j+sy].getState().equals(States.EMPTY)){
							matrix[i+sx][j+sy].setState(States.LEGALMOVE);
							moves.add(new int[] {i, j, k, i+sx, j+sy, appreciateMove(i+sx, j+sy, othercolor), 0});
						}else if (matrix[i+sx][j+sy].getState().equals(States.LEGALMOVE)) {
							moves.add(new int[] {i, j, k, i+sx, j+sy, appreciateMove(i+sx, j+sy, othercolor), 0});
						}
					}
				}
			}
		}
 		if (moves.size() <= 0) {
 			hasMoves--;
 		}else {
 			if (hasMoves == -1) {
 				hasMoves ++;
 			}
 		}
 	}	
 	
 	public void clearMoves() {
 		/*for(int[] mv: moves) {
 			if (matrix[mv[0]][mv[1]].getState().equals(States.LEGALMOVE))
 				matrix[mv[0]][mv[1]].setState(States.EMPTY);
 		}*/
 		for (int i = 0; i < dim; i++) {
 			for(int j = 0; j < dim; j++){
 				if (this.matrix[i][j].getState().equals(States.LEGALMOVE))
 	 				this.matrix[i][j].setState(States.EMPTY);
 			}
 		}
 		moves.clear();
 	}
 	
 	public int appreciateMove (int i,int j, States othercolor) {
         int count=0;
         if(i>0 && j>0 && i<7 && j<7) {
        	if (matrix[i-1][j-1].getState().equals(othercolor)) {
        		 count++;
 	        }
 	        if (matrix[i-1][j].getState().equals(othercolor)) {
 	            count++;
 	        }
 	        if (matrix[i-1][j+1].getState().equals(othercolor)) {
 	            count++;
 	        }
 	        if (matrix[i][j-1].getState().equals(othercolor)) {
 	            count++;
 	        }
 	        if (matrix[i][j+1].getState().equals(othercolor)) {
 	            count++;
 	        }
 	        if (matrix[i+1][j-1].getState().equals(othercolor)) {
 	            count++;
 	        }
 	        if (matrix[i+1][j].getState().equals(othercolor)) {
 	            count++;
 	        }
 	        if (matrix[i+1][j+1].getState().equals(othercolor)) {
 	            count++;
 	        }
 	        //counting the opponents color moves in the matrix-that means we have more possible moves so more moves better score
 	      	}else {
 	           //gwnies
	 	      	if((i==0 && j==0) || (i==7 && j==7) ||(i==0 && j==7) ||(i==7 && j==0)) {
	 	      		if(i==0 && j==0) {
		        		if (matrix[i+1][j].getState().equals(othercolor)) {
		        			count++;
		        		}
		        		if (matrix[i+1][j+1].getState().equals(othercolor)) {
		        			count++;
		 	            }
		 	            if (matrix[i][j+1].getState().equals(othercolor)) {
		 	            	count++;
		 	            }
	 	      		}	
	 	      		if(i==7 && j==0) {
	 	      			if (matrix[i-1][j].getState().equals(othercolor)) {
	 	      				count++;
	 	      			}
	 	                if (matrix[i-1][j+1].getState().equals(othercolor)) {
	 	                	count++;
	 	                }
	 	                if (matrix[i][j+1].getState().equals(othercolor)) {
	 	                    count++;
	 	                }
	 	      		}
	 	            if(i==1 && j==7) {
	 	            	if (matrix[i][j-1].getState().equals(othercolor)) {
	 	            		count++;
	 	                }
	 	            	if (matrix[i+1][j-1].getState().equals(othercolor)) {
	 	            		count++;
	 	            	}
	 	            	if (matrix[i-1][j].getState().equals(othercolor)) {
	 	            		count++;
	 	            	}                  
	 	            }
	 	            if(i==7 && j==7) {
	 	            	if (matrix[i-1][j-1].getState().equals(othercolor)) {
	 	            		count++;
	 	            	}
	 	            	if (matrix[i][j-1].getState().equals(othercolor)) {
	 	            		count++;
	 	            	}
	 	            	if (matrix[i-1][j].getState().equals(othercolor)) {
	 	            		count++;
	 	                }                  
	 	            }
	 	                               
	 	      	}else {
	 	      		//akraies theseis
	 	      		if(i==0) {
	 	      			if (matrix[i][j-1].getState().equals(othercolor)) {
	 	      				count++;
	 	      			}
	 	      			if (matrix[i+1][j].getState().equals(othercolor)) {
	 	      				count++;
	 	      			}
	 	      			if (matrix[i][j+1].getState().equals(othercolor)) {
	 	      				count++;
	 	                }                                      
	 	      		}
	 	      		if(j==0) {
	 	      			if (matrix[i-1][j].getState().equals(othercolor)) {
	 	      				count++;
	 	                }
	 	      			if (matrix[i][j+1].getState().equals(othercolor)) {
	 	      				count++;
	 	      			}
	 	      			if (matrix[i+1][j].getState().equals(othercolor)) {
	 	      				count++;
	 	                }                                  
	 	      		}
	 	      		if(i==7) {
	 	      			if (matrix[i-1][j].getState().equals(othercolor)) {
	 	      				count++;
	 	      			}
	 	      			if (matrix[i-1][j+1].getState().equals(othercolor)) {
	 	      				count++;
	 	      			}
	 	      			if (matrix[i][j+1].getState().equals(othercolor)) {
	 	      				count++;
	 	      			}                  
	 	      		}
	 	      		if(j==7) {
	 	      			if (matrix[i-1][j].getState().equals(othercolor)) {
	 	      				count++;
	 	      			}
	 	      			if (matrix[i][j-1].getState().equals(othercolor)) {
	 	      				count++;
	 	      			}
	 	      			if (matrix[i+1][j].getState().equals(othercolor)) {
	 	      				count++;
	 	      			}
	 	            }
	 	        }
 	      	}
 	       
 	       
 	         return MY[i][j]/2+count*5;
 	        //set score
 	    }
	
 	public ArrayList<Board> getChildren (States color){
 		List<int[]> overlappingMoves = new ArrayList<>();
 		List<Integer> temp = new ArrayList<>();
 		this.findLegalMoves(color);
 		ArrayList<Board> children = new ArrayList<Board>();
 		int i = 0;
 		for (int[] move: moves) {
 			if(move[6] == 0) {
 				overlappingMoves.add(move);
 				temp = findPairInList(moves, moves.get(i)[3], moves.get(i)[4], i+1);
				for (int j: temp) {
					if(moves.get(j)[6] == 0) {
						overlappingMoves.add(moves.get(j));
						moves.get(j)[6] = 1;
					}
				}
				Board child = new Board(this);
				child.makeMove(color, overlappingMoves);
				//child.printBoard();
				children.add(child);
 	 			move[6] = 1;
 			}
 			i++;
 			overlappingMoves.clear();
 		}
 		overlappingMoves.clear();
 		return children;
 	}

 	public boolean isTerminal () {
 		boolean term = false;
 		
 		if ((myTiles.size() + opponentTiles.size()) >= 6 || hasMoves <= -2) {
 			term = true;
 		}
 		
 		return term;
 	}
 	
 	public void printScore () {
 		System.out.println("--SCORE--\nYOU: " + opponentTiles.size() + "   CPU: " + myTiles.size() + "\n");
 	}
 	
 	//utility method
 		public void printList (List <int[]> list) {
 			System.out.println();
 			for (int[] arr: list) {
 				for (int i = 0; i < arr.length; i++) {
 					System.out.print(arr[i] + ", ");
 				}
 				System.out.print("	");
 			}
 			System.out.print('\n');
 		}	
}
