package Main;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class ChessBoard extends JPanel {
	private int numRows = 8;
	private int numColumns = 8;
	private int squareSize = 100;

	public void draw(Graphics2D g2) {

		for (int y = 0; y < numColumns; y++) {
			for (int x = 0; x < numRows; x++) {
				if ((x + y) % 2 == 0) {
					g2.setColor(Color.WHITE);
				} else {
					g2.setColor(Color.BLACK);
				}

				g2.fillRect(x *squareSize, y *squareSize , 100, 100);

			}

		}
	}
}
