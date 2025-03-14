package Main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Piece {
	private int collumn, row;
	private int xPos, yPos; // to know where to paint the piece on the chess board
	private Boolean isWhite;
	private Image sprite;
	private int spriteWidht;
	private int spriteHeight;

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

	public void draw(Graphics2D g2) {
		g2.drawImage(sprite, xPos, yPos, spriteWidht, spriteHeight, null);
	}
}
