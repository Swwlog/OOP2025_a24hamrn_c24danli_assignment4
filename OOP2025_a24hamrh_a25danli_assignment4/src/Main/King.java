package Main;

import java.util.ArrayList;

public class King extends Piece {
	private String name;

	// Constructor.
	public King(int collumn, int row, Boolean isWhite, int widht, int hight) {
		super(collumn, row, isWhite, widht, hight);
		name = "King";

		// check what color of sprite to add Sprite
		if (isWhite) {
			setSprite(getSprite("/piece/white-king"));
		} else {
			setSprite(getSprite("/piece/black-king"));
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
		} else if ((Math.abs((getCollumn() - newCol) * (getRow() - newRow)) == 1)
				|| ((Math.abs((getCollumn() - newCol)) + (Math.abs(getRow() - newRow))) == 1)) {
			return true;
		}
		return false;
	}

}
