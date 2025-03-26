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
		else if ((getIsWhite() && ((this.getRow()-newRow)==1)&& this.getCollumn()==newCol)&&squareOccupiedOppositeColor(newCol, newRow, pieceList)==false
		|| (getIsWhite() &&squareOccupiedOppositeColor(newCol, newRow, pieceList) && ((this.getRow()-newRow)==1)&& (Math.abs(getCollumn()-newCol)==1))
		|| getHasMoved()==false && ((getIsWhite() && ((this.getRow()-newRow)==2)&& this.getCollumn()==newCol))&&squareOccupiedOppositeColor(newCol, newRow, pieceList)==false
			&&!targetBlockedStraitLines(newCol,newRow,pieceList))
		{
			return true;
		}
		else if ((getIsWhite()==false && ((this.getRow()-newRow)==-1)&& this.getCollumn()==newCol)&&squareOccupiedOppositeColor(newCol, newRow, pieceList)==false
			|| (getIsWhite()==false && squareOccupiedOppositeColor(newCol, newRow, pieceList) && ((this.getRow()-newRow)==-1)&& (Math.abs(getCollumn()-newCol)==1))
			||getHasMoved()==false && ((getIsWhite()==false && ((this.getRow()-newRow)==-2)&& this.getCollumn()==newCol))&&squareOccupiedOppositeColor(newCol, newRow, pieceList)==false
			&&!targetBlockedStraitLines(newCol,newRow,pieceList)) // test
		{
			return true;
		}
		return false;
	}

}

