package Main;

import java.util.ArrayList;

public class Knight extends Piece {
	private String name;

	// Constructor.
	public Knight(int collumn, int row, Boolean isWhite, int widht, int hight) {
		super(collumn, row, isWhite, widht, hight);
		name = "Knight";

		// check what color of sprite to add Sprite
		if (isWhite) {
			setSprite(getSprite("/piece/white-knight"));
		} else {
			setSprite(getSprite("/piece/black-knight"));
		}
	}
	// Get name from piece
	public String getName() {
		return name;
	}
	@Override
	public boolean validMove(int newCol, int newRow, ArrayList<Piece> pieceList) {
		if(squareOccupiedSameColor( newCol, newRow,pieceList)) {
			return false;
		}
		else if ((Math.abs((getCollumn()-newCol)*(getRow()-newRow))==2)){
			return true;
		}
		return false;
	}
	
	
}
