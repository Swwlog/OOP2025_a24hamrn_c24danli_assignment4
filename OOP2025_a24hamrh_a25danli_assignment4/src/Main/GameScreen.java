package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GameScreen extends JPanel {

	private int width = 800;
	private int hight = 800;

	public GameScreen() {
		setPreferredSize(new Dimension(width, hight));
		setBackground(Color.darkGray);
	}
	@Override
public void paintComponent(Graphics g) {
	g.drawRect(hight, hight, width, hight);
}

}
