package Main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Piece {
	private int collumn, row;
	private int xPos, yPos; // to know where to paint the piece on the chess board
	private Boolean isWhite;
	private Image sprite;
	private int spriteWidht;
	private int spriteHeight;
	private boolean hasMoved;
	private String name;

	// Constructor. spriteWidht/height is divided by 8 to scale it.
	// It is also used in xPos/yPos to scale coordinates properly.
	public Piece(int collumn, int row, Boolean isWhite, int width, int height) {

		this.collumn = collumn;
		this.row = row;
		this.isWhite = isWhite;
		this.spriteWidht = width / 8;
		this.spriteHeight = height / 8;
		xPos = collumn * spriteWidht;
		yPos = row * spriteHeight;
		this.hasMoved = false;
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}

	public BufferedImage getSprite(String spritePath) {
		BufferedImage sprite = null;

		try {
			sprite = ImageIO.read(getClass().getResourceAsStream(spritePath + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sprite;

	}

	// Draw sprite
	public void draw(Graphics2D g2) {
		g2.drawImage(sprite, xPos, yPos, spriteWidht, spriteHeight, null);
	}

	public int getCollumn() {
		return collumn;
	}

	public int getRow() {
		return row;
	}

	public boolean getIsWhite() {
		return isWhite;

	}

	public boolean getHasMoved() {
		return hasMoved;
	}

	// for subclass to Override
	public boolean validMove(int newCol, int newRow, ArrayList<Piece> pieceList) {

		return false;
	}

	// for subclass to Override
	public String getName() {
		return name;
	}

	public boolean squareOccupiedSameColor(int newCol, int newRow, ArrayList<Piece> pieceList) {
		for (Piece piece : pieceList) {
			if (piece.getCollumn() == newCol && piece.getRow() == newRow && piece.getIsWhite() == this.isWhite) {
				return true;
			}
		}
		return false;
	}

	public boolean squareOccupiedOppositeColor(int newCol, int newRow, ArrayList<Piece> pieceList) {
		for (Piece piece : pieceList) {
			if (piece.getCollumn() == newCol && piece.getRow() == newRow && piece.getIsWhite() != this.isWhite) {
				return true;
			}
		}
		return false;
	}

	public void capturePiece(int newCol, int newRow, ArrayList<Piece> pieceList) {
		for (Piece piece : pieceList) {
			if (piece.getCollumn() == newCol && piece.getRow() == newRow && piece.getIsWhite() != this.isWhite) {
				pieceList.remove(piece);
				break;
			}
		}
	}

	public void movePiece(int collumn, int row, ArrayList<Piece> pieceList) {
		if (this.getName() == "King" && this.collumn - collumn == -2) {
			castle(pieceList, true);
		}
		if (this.getName() == "King" && this.collumn - collumn == 2) {
			castle(pieceList, false);
		}

		this.collumn = collumn;
		this.row = row;
		this.hasMoved = true;
		if (this.getName() == "Pawn" && (this.getRow() == 7 || this.getRow() == 0)) {
			promotePawn(pieceList);
		}
		uppdateXYPos();
	}

	public void uppdateXYPos() {
		xPos = collumn * spriteWidht;
		yPos = row * spriteHeight;
	}

	public void castle(ArrayList<Piece> pieceList, boolean movedRight) {
		if (movedRight) { // castling to the Right!!
			for (Piece piece : pieceList) {
				if (piece.getName() == "Rook" && piece.getCollumn() == 7 && piece.getIsWhite() == getIsWhite()) {
					pieceList.add(new Rook(5, piece.getRow(), piece.getIsWhite(), piece.spriteWidht * 8,
							piece.spriteHeight * 8));
					pieceList.remove(piece);
					break;
				}
			}
		}
		if (!movedRight) { // castling to the Left!!
			for (Piece piece : pieceList) {
				if (piece.getName() == "Rook" && piece.getCollumn() == 0 && piece.getIsWhite() == getIsWhite()) {
					pieceList.add(new Rook(3, piece.getRow(), piece.getIsWhite(), piece.spriteWidht * 8,
							piece.spriteHeight * 8));
					pieceList.remove(piece);
					break;
				}
			}
		}
	}
		// Promote Pawn only queen now, add choice for other pieces.
	public void promotePawn(ArrayList<Piece> pieceList) {
		pieceList.add(new Queen(this.getCollumn(), this.getRow(), this.getIsWhite(), this.spriteWidht*8, this.spriteHeight*8));
		pieceList.remove(this);
	}

	public boolean targetBlockedDiagonalLines(int newCol, int newRow, ArrayList<Piece> pieceList) {
		// if Diagonal up
		if (newRow < this.getRow()) {
			// looking diagonal up left
			for (int i = this.getCollumn() - 1; i > newCol; i--) {
				int ratio = Math.abs(i - this.getCollumn());
				for (Piece piece : pieceList) {
					if (i == piece.getCollumn() && this.getRow() - ratio == piece.getRow()) {
						return true;
					}
				}
			}
			// looking diagonal up right
			for (int i = this.getCollumn() + 1; i < newCol; i++) {
				int ratio = Math.abs(i - this.getCollumn());
				for (Piece piece : pieceList) {
					if (i == piece.getCollumn() && this.getRow() - ratio == piece.getRow()) {
						return true;
					}
				}
			}
		}
		// if Diagonal down
		if (newRow > this.getRow()) {
			// looking diagonal down rights
			for (int i = this.getCollumn() + 1; i < newCol; i++) {
				int ratio = Math.abs(i - this.getCollumn());
				for (Piece piece : pieceList) {
					if (i == piece.getCollumn() && this.getRow() + ratio == piece.getRow()) {
						return true;
					}
				}
			}

			// looking diagonal down left
			for (int i = this.getCollumn() - 1; i > newCol; i--) {
				int ratio = Math.abs(i - this.getCollumn());
				for (Piece piece : pieceList) {
					if (i == piece.getCollumn() && this.getRow() + ratio == piece.getRow()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean targetBlockedStraitLines(int newCol, int newRow, ArrayList<Piece> pieceList) {
		// looks if blocked strait right
		for (int i = this.getCollumn() + 1; i < newCol; i++) {
			for (Piece piece : pieceList) {
				if (i == piece.getCollumn() && this.getRow() == piece.getRow()) {
					return true;
				}
			}

		}
		// Looks if blocked strait left
		for (int i = this.getCollumn() - 1; i > newCol; i--) {
			for (Piece piece : pieceList) {
				if (i == piece.getCollumn() && this.getRow() == piece.getRow()) {
					return true;
				}
			}

		}
		// Looks if blocked strait up
		for (int i = this.getRow() - 1; i > newRow; i--) {
			for (Piece piece : pieceList) {
				if (this.getCollumn() == piece.getCollumn() && i == piece.getRow()) {
					return true;
				}
			}

		}
		// Looks if blocked strait down
		for (int i = this.getRow() + 1; i < newRow; i++) {
			for (Piece piece : pieceList) {
				if (this.getCollumn() == piece.getCollumn() && i == piece.getRow()) {
					return true;
				}
			}

		}
		return false;
	}

}
