package javagym;

import java.util.Scanner;

class GameRunner {

    private MineSweeper mineSweeper;

    // Displays rules at beginning of game.
    void initRandom(int numberOfMines, int width, int height, int seed) {
        mineSweeper = new YourStrategy(width, height);
        mineSweeper.generateMinesRandom(numberOfMines, seed);
        mineSweeper.print();
    }

    void initInput(String fileName, int width, int height) {
        // Load from a file. You need to give the correct width and height yourself.
        mineSweeper = new YourStrategy(width, height);
        try {
            mineSweeper.loadFile(fileName);
        } catch (Exception e) {
            System.out.println("error reading file: " + e);
            System.exit(0);
        }
        mineSweeper.print();
    }

    boolean run(boolean playManually) {
        Scanner scan = new Scanner(System.in);
        int x, y;
        if (playManually) {
            System.out.print("Enter an x coordinate.");
            x = scan.nextInt();
            System.out.print("Enter a y coordinate.");
            y = scan.nextInt();
        } else {
            // this is for automatic play
            int[] pick = mineSweeper.pickSquare();
            x = pick[0];
            y = pick[1];
        }

        // If the first tile that is selected is a mine. We remove the mine and place it somewhere else.
        if (mineSweeper.getTile(x, y).equals(" * ")) {
            mineSweeper.generateMinesRandom(1, -1);
            mineSweeper.doNotCall_RemoveMine(x, y);
        }
        // The first pick will clear the adjacent tiles that are safe.
        mineSweeper.clear(x, y);
        // The mineSweeper will detect the numbers that need to be shown in the field.
        mineSweeper.detect();
        mineSweeper.revealNeighboursOfZeros();
        mineSweeper.isVictory();
        mineSweeper.print();

        //After first move, loops until the mineSweeper ends.
        while (true) {
            if(mineSweeper.getDone() && mineSweeper.getWin()) {
                // The player has won
                System.out.println("You win!");
                mineSweeper.onEnd();
                return true;
            } else if(mineSweeper.getDone()) {
                // The player has lost
                mineSweeper.onEnd();
                return false;
            } else if(!mineSweeper.getDone()) {
                // The player has to select another square
                x = -1;
                y = -1;
                if (playManually) {
                    System.out.print("Enter an x coordinate.");
                    y = scan.nextInt();
                    System.out.print("Enter a y coordinate.");
                    x = scan.nextInt();
                } else {
                    // this is for automatic play
                    int[] pick = mineSweeper.pickSquare();
                    x = pick[0];
                    y = pick[1];
                }
                mineSweeper.turn(x, y);
                mineSweeper.detect();
                mineSweeper.revealNeighboursOfZeros();
                mineSweeper.isVictory();
                mineSweeper.print();
            }
        }
    }
}