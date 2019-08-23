import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StaticField {

	@Test
	void testStaticField() {
		GameRunner gameRunner = new GameRunner();
        // Initialize the game with the number of mines and the game size
        gameRunner.initInput("mineField.txt", 10, 10);
        // Run the game. The boolean indicates whether you want to play manually. Set this to false to have it use your algorithm.
		boolean isWin = gameRunner.run(false);
		assertTrue(isWin);
	}
}
