package Main;

public class Knight extends Piece {
	private String name;
	

	public Knight(int collumn, int row, Boolean isWhite) {
		super(collumn, row, isWhite);
		name = "Knight";

		// Sprite
		if (isWhite) {
			setSprite(getSprite("/piece/white-knight"));
		}
		else {
			setSprite(getSprite("/piece/black-knight"));
		}
	}

	public String getName() {
		return name;
	}
	
	}

