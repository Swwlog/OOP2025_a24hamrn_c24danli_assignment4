package Main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame("Chess Game");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		// Adding GameScreen to frame
		GameScreen gamescreen = new GameScreen();
		window.add(gamescreen);
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);

	}

}
