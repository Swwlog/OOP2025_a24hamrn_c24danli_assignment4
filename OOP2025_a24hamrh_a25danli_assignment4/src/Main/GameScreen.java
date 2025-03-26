package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class GameScreen extends JPanel implements Runnable, MouseInputListener {
	private static ChessBoard chessBoard;
	private int width = 800;
	private int hight = 800;
	private Thread refresh;
	private ArrayList<Piece> pieceList = new ArrayList<>();
	private ArrayList<Piece> pieceListCopy = new ArrayList<>();
	private Piece activePiece = null;
	private boolean whitesTurn = true;
	private Piece activePieceCopy = null;

	public GameScreen() {
		chessBoard = new ChessBoard(this.width, this.hight);
		setPreferredSize(new Dimension(width, hight));
		setBackground(Color.darkGray);
		initializePieces();
		addMouseListener(this);
		addMouseMotionListener(this);
		// create and start thread for refresh

	}

// paints board and pieces to JPanel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		chessBoard.draw(g2);
		if (activePiece != null) {
			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					if (activePiece.validMove(x, y, pieceList) == true) {
						if (simulateMoveLegal(x, y)) {
							g2.setColor(Color.GREEN);
							g2.fillRect(x * 100, y * 100, 100, 100);
						}
					}

				}
			}
		}
		for (Piece piece : pieceList) {
			piece.draw(g2);
		}
	}

	public void startRefresh() {
		refresh = new Thread(this);
		refresh.run();
	}

	// repeats paintComponent to update screen !! remake or remove
	@Override
	public void run() {
		while (true) {
			repaint();
		}

	}

	// test all pieces should be here add to try pieces as they are made.
	// Add widht and hight as argument for scaling
	public void initializePieces() {
		// White Pieces
		pieceList.add(new Rook(7, 7, true, this.width, this.hight));
		pieceList.add(new Rook(0, 7, true, this.width, this.hight));
		pieceList.add(new Knight(1, 7, true, this.width, this.hight));
		pieceList.add(new Knight(6, 7, true, this.width, this.hight));
		pieceList.add(new Bishop(2, 7, true, this.width, this.hight));
		pieceList.add(new Bishop(5, 7, true, this.width, this.hight));
		pieceList.add(new King(4, 7, true, this.width, this.hight));
		pieceList.add(new Queen(3, 7, true, this.width, this.hight));
		for (int i = 0; i < 8; i++) {
			pieceList.add(new Pawn(i, 6, true, this.width, this.hight));
		}

		// Black Pieces
		pieceList.add(new Rook(7, 0, false, this.width, this.hight));
		pieceList.add(new Rook(0, 0, false, this.width, this.hight));
		pieceList.add(new Knight(1, 0, false, this.width, this.hight));
		pieceList.add(new Knight(6, 0, false, this.width, this.hight));
		pieceList.add(new Bishop(2, 0, false, this.width, this.hight));
		pieceList.add(new Bishop(5, 0, false, this.width, this.hight));
		pieceList.add(new King(4, 0, false, this.width, this.hight));
		pieceList.add(new Queen(3, 0, false, this.width, this.hight));
		for (int i = 0; i < 8; i++) {
			pieceList.add(new Pawn(i, 1, false, this.width, this.hight));
		}
	}

	public void copyList(ArrayList<Piece> copyFrom, ArrayList<Piece> copyTo) {
		copyTo.clear();
		for (Piece piece : copyFrom) {
			switch (piece.getName()) {
			case "Rook":
				copyTo.add(new Rook(piece.getCollumn(), piece.getRow(), piece.getIsWhite(), this.width, this.hight));
				break;
			case "Pawn":
				copyTo.add(new Pawn(piece.getCollumn(), piece.getRow(), piece.getIsWhite(), this.width, this.hight));
				break;
			case "Knight":
				copyTo.add(new Knight(piece.getCollumn(), piece.getRow(), piece.getIsWhite(), this.width, this.hight));
				break;
			case "Queen":
				copyTo.add(new Queen(piece.getCollumn(), piece.getRow(), piece.getIsWhite(), this.width, this.hight));
				break;
			case "King":
				copyTo.add(new King(piece.getCollumn(), piece.getRow(), piece.getIsWhite(), this.width, this.hight));
				break;
			case "Bishop":
				copyTo.add(new Bishop(piece.getCollumn(), piece.getRow(), piece.getIsWhite(), this.width, this.hight));
				break;
			}
		}
		for (Piece piece : copyTo) {
			if (piece.getCollumn() == activePiece.getCollumn() && piece.getRow() == activePiece.getRow()) {
				activePieceCopy = piece;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getX() / 100);
		System.out.println(e.getY() / 100);
		if (activePiece != null && activePiece.validMove(e.getX() / 100, e.getY() / 100, pieceList)) {

			if (simulateMoveLegal(e.getX() / 100, e.getY() / 100)) {

				activePiece.movePiece(e.getX() / 100, e.getY() / 100, pieceList);
				activePiece.capturePiece(e.getX() / 100, e.getY() / 100, pieceList);
				if (whitesTurn == true) {
					whitesTurn = false;
				} else {
					whitesTurn = true;
				}
				checkmate();
				activePiece = null;
			}

		}

		else if (activePiece == null) {
			for (Piece piece : pieceList) {
				if (((piece.getCollumn() == e.getX() / 100) && piece.getRow() == e.getY() / 100)
						&& piece.getIsWhite() == whitesTurn) {
					activePiece = piece;
					System.out.println("piece choosen");

				}
			}
		} else {
			for (Piece piece : pieceList) {
				if ((piece.getCollumn() == e.getX() / 100) && piece.getRow() == e.getY() / 100) {
					if (piece.getIsWhite() == activePiece.getIsWhite()) {
						activePiece = piece;
					}

				}
			}
		}
	}

	public boolean checkmate() {
		if (!isChecked(pieceList)) {
			System.out.println("Not in check stage 1");
			return false;
		}

		for (Piece piece : pieceList) {
			activePiece = piece;
			if (piece.getIsWhite() == whitesTurn) {
				for (int y = 0; y < 8; y++) {
					for (int x = 0; x < 8; x++) {
						if (piece.validMove(x, y, pieceList) == true) {
							if (simulateMoveLegal(x, y)) {

								System.out.println(activePieceCopy.getName() + activePieceCopy.getIsWhite());
								System.out.println("" + x + y);
								System.out.println("can move");
								return false;
							}

						}
					}
				}
			}
		}
		System.out.println("YOU WON");
		return true;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean simulateMoveLegal(int col, int row) {
		copyList(pieceList, pieceListCopy);
		activePieceCopy.movePiece(col, row, pieceListCopy);
		activePieceCopy.capturePiece(col, row, pieceListCopy);

		if (!isChecked(pieceListCopy)) {
			return true;
		}
		return false;
	}

	public boolean isChecked(ArrayList<Piece> list) {
		Piece king = null;
		for (Piece piece : list) {
			if (piece.getName() == "King" && piece.getIsWhite() == whitesTurn) {
				king = piece;
				break;
			}
		}
		for (Piece piece : list) {
			if (piece.getIsWhite() != whitesTurn && piece.validMove(king.getCollumn(), king.getRow(), list) == true) {
				System.out.println("King Checked");
				return true;
			}

		}
		return false;
	}

}
