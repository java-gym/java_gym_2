package javagym;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Helper {

	static void testPseudoRandomGame(int length, int mineCount) {
		GameRunner gameRunner = new GameRunner();
		gameRunner.initRandom(mineCount, length, length, 123_456_789 + 1000 * length + mineCount);
		assertTrue(gameRunner.run(false));
	}
}
