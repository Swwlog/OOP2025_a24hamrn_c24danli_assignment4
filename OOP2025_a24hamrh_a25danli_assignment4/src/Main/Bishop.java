package Main;

import java.util.ArrayList;

public class Bishop extends Piece {
	private String name;

	// Constructor.
	public Bishop(int collumn, int row, Boolean isWhite, int widht, int hight) {
		super(collumn, row, isWhite, widht, hight);
		name = "Bishop";

		// check what color of sprite to add Sprite
		if (isWhite) {
			setSprite(getSprite("/piece/white-bishop"));
		} else {
			setSprite(getSprite("/piece/black-bishop"));
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
		else if (Math.abs(getCollumn()-newCol)==(Math.abs(getRow()-newRow))){
			return true;
		}
		return false;
	}
	

}

