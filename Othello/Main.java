package Reversi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Reversi.Tile.States;

public class Main {
	
	public static int depth;
	public static boolean first;
	public static String opponentsColor;
	public static String myColor;
	
	public static void main (String[] args) {
		
		turn();
		setDepth();
		Board gameBoard = new Board();
		gameBoard.setLastColorPlayed(States.WHITE);
		Player me = new Player (depth, States.valueOf(myColor));
		
		gameBoard.printBoard();
		
		while (!gameBoard.isTerminal()) {
			System.out.println();
			if (first) {		//Opponent goes first. He/She is BLACK, we are WHITE
				switch (gameBoard.getLastColorPlayed()){
					case BLACK:	//Since BLACK played last, it's WHITE's turn.
						System.out.println("WHITE moves");
						int[] move = me.MiniMax(gameBoard);
						for (int i = 0; i < move.length; i++) {
							System.out.print(move[i] + " ");
						}
						System.out.println();
						gameBoard.makeMove(States.WHITE, move);
                    	System.out.println("Opponent");
                    	gameBoard.printList(gameBoard.opponentTiles);
                    	System.out.println("My");
                    	gameBoard.printList(gameBoard.myTiles);
						gameBoard.clearMoves();
						gameBoard.setLastColorPlayed(States.WHITE);
						break;
					case WHITE:	//Since WHITE played last, it's BLACK's turn.
						System.out.println("BLACK moves");
	                    gameBoard.findLegalMoves(States.BLACK);
	                    if (gameBoard.moves.size() > 0) {
	                    	gameBoard.printBoard();
	                    	gameBoard.makeMove(States.BLACK, gameBoard.readMove());
	                    	System.out.println("Opponent");
	                    	gameBoard.printList(gameBoard.opponentTiles);
	                    	System.out.println("My");
	                    	gameBoard.printList(gameBoard.myTiles);
							gameBoard.clearMoves();
							gameBoard.printBoard();
	                    }else {
	                    	System.out.println("You have no moves."); 
	                    	gameBoard.printBoard();
	                    }
						gameBoard.setLastColorPlayed(States.BLACK);	                    	
						break;
					default:
						break;
				}
				
			}else {				//We go first, so we are BLACK and the opponent is WHITE
				switch (gameBoard.getLastColorPlayed()){
				case BLACK:
					System.out.println("WHITE moves");
                    gameBoard.findLegalMoves(States.WHITE);
                    if (gameBoard.moves.size() > 0) {
	                    gameBoard.printBoard();
						gameBoard.makeMove(States.WHITE, gameBoard.readMove());
						gameBoard.printBoard();
                    }else {
                    	System.out.println("You have no moves."); 
                    }
					gameBoard.setLastColorPlayed(States.WHITE);
					break;
				case WHITE:
					System.out.println("BLACK moves");
					int[] move = me.MiniMax(gameBoard);
					for (int i = 0; i < move.length; i++) {
						System.out.print(move[i] + " ");
					}
					System.out.println();
					gameBoard.makeMove(States.BLACK, move);
					gameBoard.clearMoves();
					gameBoard.setLastColorPlayed(States.BLACK);
					break;
				default:
					System.out.println(gameBoard.getLastColorPlayed());
					break;
				}
			}
		}
		if(gameBoard.opponentTiles.size() > gameBoard.myTiles.size()) {
			System.out.println("\n\n\nY O U    W I N !!!!");	
		}else {
			System.out.println("\n\n\nY O U    L O S T !!!!");
		}
		
		System.out.println("G A M E   O V E R");
		
	}
	
	static void setDepth () {
		System.out.println("Select difficulty level (1 to 5).\nType a number.");
		int answer = 0;
		do {
			try {
				Scanner scanner = new Scanner(System.in);
				answer = scanner.nextInt();
				//scanner.close();
				if (answer >= 1 && answer <= 5) {
					depth = answer + 1;
					break;
				}else {	
					throw new NoSuchElementException();
				}	
			}catch (NoSuchElementException e) {
				System.err.println("Wrong input. Please type a number from 1 to 5.");
			}
		} while (true);	
	}
	
	// sets variable "first" true if the opponent wants to play first,
	// and false if the opponent wants to play second.
	static void turn () {
		System.out.println("Do you wish to play first?\nType Y or N.");
		String answer = "";		
		do {
			try {	
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));		
				answer = br.readLine();	
				//br.close();
				if (answer.equals("Y") | answer.equals("y")) {
					first = true;
					opponentsColor = "BLACK";
					myColor= "WHITE";
					//board.setLastColorPlayed(States.WHITE);
					break;
				}else if (answer.equals("N") | answer.equals("n")){
					first = false;
					opponentsColor = "WHITE";
					myColor = "BLACK";
					//board.setLastColorPlayed(States.WHITE);
					break;
				}else {	
					throw new IOException();
				}	
			}catch (IOException e){
				System.err.println("Wrong input. Please type the letter Y for yes and N for no.");
			}
		} while (true);	
	}
}
