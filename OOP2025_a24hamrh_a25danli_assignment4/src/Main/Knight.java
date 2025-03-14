package Main;

public class Knight extends Piece {
	private String name;

	// Constructor.
	public Knight(int collumn, int row, Boolean isWhite, int widht, int hight) {
		super(collumn, row, isWhite, widht, hight);
		name = "Knight";

		// check what color of sprite to add Sprite
		if (isWhite) {
			setSprite(getSprite("/piece/white-knight"));
		} else {
			setSprite(getSprite("/piece/black-knight"));
		}
	}
	// Get name
	public String getName() {
		return name;
	}

}
