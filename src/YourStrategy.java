
public class YourStrategy extends MineSweeper {

	YourStrategy(int width, int height) {
		super(width, height);
	}

	@Override
	int[] pickSquare() {
		// implement your algorithm to select an x and an y. The first square will have coordinates (1, 1)
		// The x and y will define which square you have selected.
		// The minesweeper board you're playing with is the array "display". It is a 2d String array
		// It is not allowed to use the 2d array "field" since this is what the game uses to find the mines.
		// '?' is undiscovered and otherwise it will have numbers indicating the standard minesweeper gameplay.

		// First turn
		if (unknown.equals(display[2][2])) {
			return new int[]{2, 2};
		}

		throw new UnsupportedOperationException("todo: implement your strategy!");
	}

}
