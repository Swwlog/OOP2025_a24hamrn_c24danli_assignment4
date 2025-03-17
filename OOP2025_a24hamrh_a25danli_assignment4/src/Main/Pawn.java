package Main;

import java.util.ArrayList;

public class Pawn extends Piece {
	private String name;

	// Constructor.
	public Pawn(int collumn, int row, Boolean isWhite, int widht, int hight) {
		super(collumn, row, isWhite, widht, hight);
		name = "Pawn";

		// check what color of sprite to add Sprite
		if (isWhite) {
			setSprite(getSprite("/piece/white-Pawn"));
		} else {
			setSprite(getSprite("/piece/black-Pawn"));
		}
	}

	// Get name from piece
	public String getName() {
		return name;
	}

	@Override
	public boolean validMove(int newCol, int newRow, ArrayList<Piece> pieceList) {
		if (squareOccupiedSameColor(newCol, newRow, pieceList)) {
			return false;
		} 
		else if (getIsWhite() && ((this.getRow()-newRow)==1)&& this.getCollumn()==newCol) {
			return true;
		}
		else if (getIsWhite()==false && ((this.getRow()-newRow)==-1)&& this.getCollumn()==newCol) {
			return true;
		}
		return false;
	}

}

