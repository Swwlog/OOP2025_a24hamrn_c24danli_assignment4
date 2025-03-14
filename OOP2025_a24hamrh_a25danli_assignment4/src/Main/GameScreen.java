package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GameScreen extends JPanel implements Runnable {
	private static ChessBoard chessBoard;
	private int width = 800;
	private int hight = 800;
	private Thread refresh;
	private ArrayList<Piece> piecesList = new ArrayList<>();

	public GameScreen() {
		chessBoard = new ChessBoard(this.width, this.hight);
		setPreferredSize(new Dimension(width, hight));
		setBackground(Color.darkGray);
		initializePieces();
		// create and start thread for refresh
		
	}
// paints board and pieces to JPanel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		chessBoard.draw(g2);
		for(Piece piece : piecesList) {
			piece.draw(g2);
		}
	}
	//Updates changes in pieces
	public void update() {
		
	}
	public void startRefresh() {
		refresh = new Thread(this);
		refresh.run();
	}
	// repeats Update and paintComponent to update screen !! remake or remove
	@Override
	public void run() {
		for(;;) {
			
			update();
			repaint(); 
			
		}
		
	}
	// test all pieces should be here add to try pieces as they are made
	public void initializePieces() {
		piecesList.add(new Knight(5,5,true, this.width, this.hight));
		piecesList.add(new Knight(0,0,false, this.width, this.hight));
	}

}
