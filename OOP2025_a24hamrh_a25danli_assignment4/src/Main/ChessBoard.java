package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class ChessBoard extends JPanel {
	private int width;
	private int hight;
	private int squareWidth;
	private int squareHight;
	
	public ChessBoard(int width, int hight) {
		this.hight = hight;
		this.width = width;
		this.squareWidth = this.width / 8;
		this.squareHight = this.hight / 8;
		setPreferredSize(new Dimension(width, hight));
		
	}

	public void draw(Graphics2D g2) {

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if ((x + y) % 2 == 0) {
					g2.setColor(Color.WHITE);
				} else {
					g2.setColor(Color.BLACK);
				}

				g2.fillRect(x *squareWidth, y *squareHight , squareWidth, squareHight);

			}

		}
	}
}
