package Reversi;

import Reversi.Tile;

public class Tile {

	public enum States {
		EMPTY, WHITE, BLACK, LEGALMOVE;
		
		public String toString(){
	        switch(this){
	        case EMPTY :
	            return "Empty";
	        case WHITE :
	            return "White";
	        case LEGALMOVE :
	            return "LegalMove";
	        case BLACK :
	        	return "Black";
	        }
	        return null;
		}
	}
	
	States state;
	
	Tile () {
		this(States.EMPTY);
	}
	
	Tile (States st){
		this.state = st;
	}
	
	void setState (States st) {
		this.state = st;
	}
	
	States getState() {
		return this.state;
	}
	@Override
	public boolean equals (Object obj) {
		if ((this.state) == ((Tile)obj).state) {
			
		}
		return false;
	}
}
