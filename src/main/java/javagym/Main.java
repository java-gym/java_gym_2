package javagym;

public class Main {
    public static void main(String[] args) {
        System.out.println("have fun playing minesweeper!");
        // Example of starting a random game of minesweeper
        GameRunner gameRunner = new GameRunner();
        // Initialize the game with the number of mines and the game size
//        gameRunner.initRandom(4, 10, 10);
        gameRunner.initInput("mineField.txt", 10, 10);
        // Run the game. The boolean indicates whether you want to play manually. Set this to false to have it use your algorithm.
        gameRunner.run(true);
    }
}