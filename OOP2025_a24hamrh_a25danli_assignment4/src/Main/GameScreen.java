package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class GameScreen extends JPanel implements Runnable, MouseInputListener {
	private static ChessBoard chessBoard;
	private int width = 800;
	private int height = 800;
	private int heightScale;
	private int widthScale;
	private Thread refresh;
	private ArrayList<Piece> pieceList = new ArrayList<>();
	private ArrayList<Piece> pieceListCopy = new ArrayList<>();
	private Piece activePiece = null;
	private boolean whitesTurn = true;
	private Piece activePieceCopy = null;
	private JLabel turnText;
	private JLabel winStatus;

	public GameScreen() {

		heightScale = this.height / 8;
		widthScale = this.width / 8;
		chessBoard = new ChessBoard(this.width, this.height);
		setPreferredSize(new Dimension(width + 200, height));
		setBackground(Color.darkGray);
		initializePieces();
		addMouseListener(this);
		addMouseMotionListener(this);

		this.setLayout(null);
		turnText = new JLabel();
		turnText.setForeground(Color.WHITE);
		this.add(turnText);
		turnText.setBounds(this.width + 50, 100, 200, 200);
		turnText.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		winStatus = new JLabel();
		winStatus.setForeground(Color.WHITE);
		this.add(winStatus);
		winStatus.setBounds(this.width + 50, turnText.getLocation().y + 100, 200, 200);
		winStatus.setFont(new Font("Times New Roman", Font.PLAIN, 20));
	}

// paints board and pieces to JPanel along with massages.
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (whitesTurn) {
			turnText.setText("Whites turn");
		} else {
			turnText.setText("Blacks turn");
		}
		Graphics2D g2 = (Graphics2D) g;
		chessBoard.draw(g2);
		if (activePiece != null) {
			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					if (activePiece.validMove(x, y, pieceList) == true) {
						if (simulateMoveLegal(x, y)) {
							g2.setColor(Color.decode("#85A785"));
							g2.fillRect(x * widthScale, y * heightScale, widthScale, heightScale);
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

	// create start pieces with initial board positions.
	private void initializePieces() {
		// White Pieces
		pieceList.add(new Rook(7, 7, true, this.width, this.height));
		pieceList.add(new Rook(0, 7, true, this.width, this.height));
		pieceList.add(new Knight(1, 7, true, this.width, this.height));
		pieceList.add(new Knight(6, 7, true, this.width, this.height));
		pieceList.add(new Bishop(2, 7, true, this.width, this.height));
		pieceList.add(new Bishop(5, 7, true, this.width, this.height));
		pieceList.add(new King(4, 7, true, this.width, this.height));
		pieceList.add(new Queen(3, 7, true, this.width, this.height));
		for (int i = 0; i < 8; i++) {
			pieceList.add(new Pawn(i, 6, true, this.width, this.height));
		}

		// Black Pieces
		pieceList.add(new Rook(7, 0, false, this.width, this.height));
		pieceList.add(new Rook(0, 0, false, this.width, this.height));
		pieceList.add(new Knight(1, 0, false, this.width, this.height));
		pieceList.add(new Knight(6, 0, false, this.width, this.height));
		pieceList.add(new Bishop(2, 0, false, this.width, this.height));
		pieceList.add(new Bishop(5, 0, false, this.width, this.height));
		pieceList.add(new King(4, 0, false, this.width, this.height));
		pieceList.add(new Queen(3, 0, false, this.width, this.height));
		for (int i = 0; i < 8; i++) {
			pieceList.add(new Pawn(i, 1, false, this.width, this.height));
		}
	}

	private void copyList(ArrayList<Piece> copyFrom, ArrayList<Piece> copyTo) {
		copyTo.clear();
		for (Piece piece : copyFrom) {
			switch (piece.getName()) {
			case "Rook":
				copyTo.add(new Rook(piece.getCollumn(), piece.getRow(), piece.getIsWhite(), this.width, this.height));
				break;
			case "Pawn":
				copyTo.add(new Pawn(piece.getCollumn(), piece.getRow(), piece.getIsWhite(), this.width, this.height));
				break;
			case "Knight":
				copyTo.add(new Knight(piece.getCollumn(), piece.getRow(), piece.getIsWhite(), this.width, this.height));
				break;
			case "Queen":
				copyTo.add(new Queen(piece.getCollumn(), piece.getRow(), piece.getIsWhite(), this.width, this.height));
				break;
			case "King":
				copyTo.add(new King(piece.getCollumn(), piece.getRow(), piece.getIsWhite(), this.width, this.height));
				break;
			case "Bishop":
				copyTo.add(new Bishop(piece.getCollumn(), piece.getRow(), piece.getIsWhite(), this.width, this.height));
				break;
			}
		}
		for (Piece piece : copyTo) {
			if (piece.getCollumn() == activePiece.getCollumn() && piece.getRow() == activePiece.getRow()) {
				activePieceCopy = piece;
			}
		}
	}

	private void winMessage() {
		// Remi could be expanded to include all instances of insufficient material and
		// a option if players agree too a draw.
		if (!isChecked(pieceList) || pieceList.size() == 2) {
			winStatus.setText("REMI");
			return;
		}
		// checkmate
		if (!whitesTurn) {
			winStatus.setText("<html> Checkmate <br> WHITE WON! </html>");
		} else if (whitesTurn) {
			winStatus.setText("<html> Checkmate <br> BLACK WON! </html>");
		}
		refresh.interrupt();

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	private boolean checkmate() {
		for (Piece piece : pieceList) {
			activePiece = piece;
			if (piece.getIsWhite() == whitesTurn) {
				for (int y = 0; y < 8; y++) {
					for (int x = 0; x < 8; x++) {
						if (piece.validMove(x, y, pieceList) == true) {
							if (simulateMoveLegal(x, y)) {
								return false;
							}
						}
					}
				}
			}
		}
		winMessage();
		return true;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Move the  chosen piece
		if (activePiece != null && activePiece.validMove(e.getX() / widthScale, e.getY() / heightScale, pieceList)) {

			if (simulateMoveLegal(e.getX() / widthScale, e.getY() / heightScale)) {

				activePiece.movePiece(e.getX() / widthScale, e.getY() / heightScale, pieceList);
				activePiece.capturePiece(e.getX() / widthScale, e.getY() / heightScale, pieceList);
				if (whitesTurn == true) {
					whitesTurn = false;
				} else {
					whitesTurn = true;
				}
				checkmate();
				activePiece = null;
			}

		}
		// choose a Piece to move
		else if (activePiece == null) {
			for (Piece piece : pieceList) {
				if (((piece.getCollumn() == e.getX() / widthScale) && piece.getRow() == e.getY() / heightScale)
						&& piece.getIsWhite() == whitesTurn) {
					activePiece = piece;

				}
			}
		}
		// Change the chosen piece
		else {
			for (Piece piece : pieceList) {
				if ((piece.getCollumn() == e.getX() / widthScale) && piece.getRow() == e.getY() / heightScale) {
					if (piece.getIsWhite() == activePiece.getIsWhite()) {
						activePiece = piece;
					}

				}
			}
		}
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

	private boolean simulateMoveLegal(int col, int row) {
		copyList(pieceList, pieceListCopy);
		activePieceCopy.movePiece(col, row, pieceListCopy);
		activePieceCopy.capturePiece(col, row, pieceListCopy);

		if (!isChecked(pieceListCopy)) {
			return true;
		}
		return false;
	}

	private boolean isChecked(ArrayList<Piece> list) {
		// Find the King
		Piece king = null;
		for (Piece piece : list) {
			if (piece.getName() == "King" && piece.getIsWhite() == whitesTurn) {
				king = piece;
				break;
			}
		}
		// see if the King is attacked
		for (Piece piece : list) {
			if (piece.getIsWhite() != whitesTurn && piece.validMove(king.getCollumn(), king.getRow(), list) == true) {
				this.winStatus.setText("CHECKED");
				return true;
			}

		}
		this.winStatus.setText("");
		return false;
	}

}
