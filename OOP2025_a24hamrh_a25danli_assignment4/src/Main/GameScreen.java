package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GameScreen extends JPanel {
	private static ChessBoard chessBoard;
	private int width = 800;
	private int hight = 800;

	public GameScreen() {
		setPreferredSize(new Dimension(width, hight));
		setBackground(Color.darkGray);
		// add(chessBoard = new ChessBoard());

		revalidate();
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if ((x + y) % 2 == 0) {
					g.setColor(Color.WHITE);
				} else {
					g.setColor(Color.BLACK);
				}

				g.fillRect(x * 100, y * 100, 100, 100);
			}
		}
	}

}
