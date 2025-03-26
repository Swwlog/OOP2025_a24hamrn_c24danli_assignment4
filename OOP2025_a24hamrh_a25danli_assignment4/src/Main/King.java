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
		// casle
		else if (!getHasMoved() && (getCollumn() - newCol) == -2 && (getRow() == newRow)
				&& !targetBlockedStraitLines(newCol, newRow, pieceList)) {
			for (Piece piece : pieceList) {
				if ((piece.getCollumn() == getCollumn() + 3) && piece.getRow() == getRow() && !piece.getHasMoved()) {
					for (Piece piece2 : pieceList) {
						if (piece2.getIsWhite() != getIsWhite())
							for (int i = 0; i < 4; i++) {
								if (piece2.validMove(getCollumn() + i, getRow(), pieceList) == true)
									return false;
							}
					}
					return true;
				}
			}
			return false;
		}
		else if (!getHasMoved() && (getCollumn() - newCol) == 2 && (getRow() == newRow)
				&& !targetBlockedStraitLines(newCol-1, newRow, pieceList)) {
			for (Piece piece : pieceList) {
				if ((piece.getCollumn() == getCollumn() + -4) && piece.getRow() == getRow() && !piece.getHasMoved()) {
					for (Piece piece2 : pieceList) {
						if (piece2.getIsWhite() != getIsWhite())
							for (int i = 0; i < 5; i++) {
								if (piece2.validMove(getCollumn() - i, getRow(), pieceList) == true)
									return false;
							}
					}
					return true;
				}
			}
			return false;
		}
		return false;

	}
}
