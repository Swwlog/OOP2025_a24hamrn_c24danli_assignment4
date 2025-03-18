package Main;

import java.util.ArrayList;

public class Rook extends Piece {
	private String name;

	// Constructor.
	public Rook(int collumn, int row, Boolean isWhite, int widht, int hight) {
		super(collumn, row, isWhite, widht, hight);
		name = "Rook";

		// check what color of sprite to add Sprite
		if (isWhite) {
			setSprite(getSprite("/piece/white-Rook"));
		} else {
			setSprite(getSprite("/piece/black-Rook"));
		}
	}
	// Get name from piece
	public String getName() {
		return name;
	}
	@Override
	public boolean validMove(int newCol, int newRow, ArrayList<Piece> pieceList) {
		if(squareOccupiedSameColor( newCol, newRow,pieceList)||targetBlockedStraitLines(newCol, newRow, pieceList)) {
			return false;
		}
		else if ((Math.abs((getCollumn()-newCol)*(getRow()-newRow))==0)){
			return true;
		}
		return false;
	}
	

}



