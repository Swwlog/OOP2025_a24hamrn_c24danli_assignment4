package Main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class GameScreen extends JPanel {

	private int width = 800;
	private int hight = 800;

	public GameScreen() {
		setPreferredSize(new Dimension(width, hight));
		setBackground(Color.darkGray);
	}

}
