package javagym;

public class YourStrategy extends MineSweeper {

	YourStrategy(int width, int height) {
		super(width, height);
	}

	@Override
	int[] pickSquare() {
		// Implement your algorithm to select an x and an y. The first square will have coordinates (1, 1)
		// The x and y will define which square you have selected.
		// The minesweeper board you're playing with is the array "display". It is a 2d String array
		// It is not allowed to use the 2d array "field" since this is what the game uses to find the mines.
		// '?' is undiscovered and otherwise it will have numbers indicating the standard minesweeper gameplay.

		// First turn
		if (unknown.equals(display[fieldWidth / 2][fieldHeight / 2])) {
			return new int[]{fieldWidth / 2, fieldHeight / 2};
		}

		// Other turns
		for (int x = 0; x < fieldWidth; x++) {
			for (int y = 0; y < fieldWidth; y++) {
				if (empty.equals(display[x][y])) {
					throw new UnsupportedOperationException("todo: implement your strategy!");
//					if (x > 0 && neighbourCount[1].equals(display[x - 1][y])) {
//						// There is only one neighbour, do you feel lucky?
//						return new int[]{x, y};
//					}
				}
			}
		}

		throw new UnsupportedOperationException();
	}
}
