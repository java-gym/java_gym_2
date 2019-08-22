public class Main {
    public static void main(String[] args) {
        // Example of starting a random game of minesweeper
        GameRunner gameRunner = new GameRunner();
        // Initialize the game with the number of mines and the game size
        gameRunner.initRandom(1, 10, 10);
        // Run the game. The boolean indicates whether you want to play manually. Set this to false to have it use your algorithm.
        gameRunner.run(true);
    }
}