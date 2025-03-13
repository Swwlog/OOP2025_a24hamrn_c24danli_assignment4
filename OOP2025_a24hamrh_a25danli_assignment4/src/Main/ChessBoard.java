package Main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ChessBoard extends JPanel{
	private int numRows=8;
	private int numCollumns=8;
	private int squareSize=100;
	boolean isWhite=true;
	
	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
	
		/*
		for(int i=0; i < numRows; i++) {
			System.out.println(i);
			for(int j=0; j<numCollumns; j++) {
				
				//g.drawRect(i,j ,squareSize , squareSize);
				super.paintComponent(g);
				g.setColor(Color.BLACK);
				g.fillRect(i, j, squareSize, squareSize);
			}
		}
		*/
		
	
	}
	
}
