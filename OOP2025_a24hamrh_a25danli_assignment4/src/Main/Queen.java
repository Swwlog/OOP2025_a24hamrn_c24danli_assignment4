package Main;

import java.util.ArrayList;

public class Queen extends Piece {
	private String name;

	// Constructor.
	public Queen(int collumn, int row, Boolean isWhite, int widht, int hight) {
		super(collumn, row, isWhite, widht, hight);
		name = "Queen";

		// check what color of sprite to add Sprite
		if (isWhite) {
			setSprite(getSprite("/piece/white-queen"));
		} else {
			setSprite(getSprite("/piece/black-queen"));
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
		
		else if ((Math.abs((getCollumn()-newCol)*(getRow()-newRow))==0)){
			if(targetBlockedStraitLines(newCol, newRow, pieceList)==false) {
				return true;
			}
		}
		else if (Math.abs(getCollumn()-newCol)==(Math.abs(getRow()-newRow))){
			if(targetBlockedDiagonalLines(newCol, newRow, pieceList)==false)
			return true;
		}
		return false;
	}
	

}

