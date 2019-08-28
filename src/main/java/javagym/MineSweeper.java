package javagym;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

abstract class MineSweeper {

    // Do NOT use 'field'
    private String[][] field;

    // Use 'display' to check the visible board state
    public String[][] display;

    public int fieldWidth;
    public int fieldHeight;

    private Boolean isDone = false;
    private Boolean isWin = false;

    public String unknown = " ? ";
    public String mine = " * ";
    public String empty = "   ";
    public String[] neighbourCount = new String[]{
            " 0 ",
            " 1 ",
            " 2 ",
            " 3 ",
            " 4 ",
            " 5 ",
            " 6 ",
            " 7 ",
            " 8 ",
    };
    private Random rand;

    //Constructor places empty spaces in tiles.
    MineSweeper(int width, int height) {
        fieldWidth = width;
        fieldHeight = height;
        field = new String[fieldWidth+2][fieldHeight+2];
        display = new String[fieldWidth+2][fieldHeight+2];
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[0].length; y++) {
                //Places blank spaces in the outer lines of the map
                if ((x == 0 || x == field.length - 1) || (y == 0 || y == field[0].length - 1)) {
                    field[x][y] = empty;
                    display[x][y] = empty;
                }
                //Places ? in game field.
                else {
                    field[x][y] = unknown;
                    display[x][y] = unknown;
                }
            }
        }
    }

    abstract int[] pickSquare();

    private void autoReveal(int x, int y) {
        turn(x, y);
        detect();
    }

    void revealNeighboursOfZeros() {

        final int X = field.length;
        final int Y = field[0].length;
        final String zero = " 0 ";

        int changeCount;
        do {
            changeCount = 0;
            for (int x = 0; x < X; x++) {
                for (int y = 0; y < Y; y++) {
                    if (unknown.equals(display[x][y])) {
                        boolean changed = true;
                        if (x < X - 1 && y < Y - 1 && zero.equals(display[x + 1][y + 1])) autoReveal(x, y);
                        else if (x < X - 1 && zero.equals(display[x + 1][y])) autoReveal(x, y);
                        else if (x < X - 1 && y > 0 && zero.equals(display[x + 1][y - 1])) autoReveal(x, y);

                        else if (y < Y - 1 && zero.equals(display[x][y + 1])) autoReveal(x, y);
                        else if (y > 0 && zero.equals(display[x][y - 1])) autoReveal(x, y);

                        else if (x > 0 && y < Y - 1 && zero.equals(display[x - 1][y + 1])) autoReveal(x, y);
                        else if (x > 0 && zero.equals(display[x - 1][y])) autoReveal(x, y);
                        else if (x > 0 && y > 0 && zero.equals(display[x - 1][y - 1])) autoReveal(x, y);

                        else changed = false;

                        if (changed) changeCount++;
                    }
                }
            }
        } while (changeCount > 0);
    }

    private void printGame(String[][] str) {
        for (int y = 1; y < str.length - 1; y++) {
            System.out.println("");
            for (int x = 1; x < str[y].length - 1; x++) {
                System.out.print("|");
                // Prints out content of each tile.
                System.out.print(str[x][y]);
            }
            System.out.print("|");
        }
        System.out.println("");
    }

    // Shows the field after every square selection.
    void print() {
        printGame(display);
    }

    // Places n mines at random on the field.
    void generateMinesRandom(int mineCount, int seed) {
        if (rand == null) {
            if (seed <= 0) {
                seed = (int) (1e9 * Math.random());
            }
            rand = new Random(seed);
        }
        for (int m = 0; m < mineCount; m++) {
            //Loops until a mine is placed.
            while (true) {
                int x = rand.nextInt(fieldWidth) + 1;
                int y = rand.nextInt(fieldHeight) + 1;

                // So that a mine is placed in a tile visible to the player.
                if (x >= 1 && x <= fieldWidth) {
                    if (y >= 1 && y <= fieldHeight){
                        // Checks if a mine is present in a spot.
                        if (!field[x][y].equals(mine)) {
                            field[x][y] = mine;
                            break;
                        }
                    }
                }
            }
        }
    }

    void loadFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            String line = br.readLine();

            int index = 0;
            while (line != null) {
                index ++;
                String[] squares = line.split("|");

                for (int i = 0; i < squares.length; i++ ) {
                    String square = squares[i];
                    if (square.equals("*")) {
                        field[((int)(i/2)+1)][index] = mine;
                    }
                }

                line = br.readLine();
            }
        } finally {
            br.close();
        }
    }

    // On first move, this clears the area around the selected tile.
    void clear(
            int x,
            int y
    ) {
        for (int i = (x - 1); i <= (x + 1); i++) {
            for (int j = (y - 1); j <= (y + 1); j++) {
                if (field[i][j].equals(unknown)){
                    display[i][j] = empty;
                    field[i][j] = empty;
                }
            }
        }
    }

    // Gets the value of a tile.
    String getTile(
            int x,
            int y
    ) {
        return field[x][y];
    }

    // Detects number of mines around a selected tile.
    void detect() {
        for (int x = 1; x < display.length - 1; x++) {
            for (int y = 1; y < display.length - 1; y++) {
                if (field[x][y].equals(empty)) {
                    int nums = 0;
                    for (int i = (x - 1); i <= (x + 1); i++) {
                        for (int j = (y - 1); j <= (y + 1); j++) {
                            if(field[i][j].equals(mine)) {
                                nums++;
                            }
                        }
                    }
                    // Set the number which indicates how many mines are near
                    display[x][y] = " " + nums + " ";
                }
            }
        }
    }

    // Takes user's selected coordinates and adjusts the board.
    void turn(int x, int y) {
        if (field[x][y].equals(unknown)) {
            isDone = false;
            display[x][y] = empty;
            field[x][y] = empty;
        } else if (field[x][y].equals(mine)) {
            // The player has selected a mine.
            isDone = true;
            isWin = false;
            System.out.println("You've lost!");
        } else if (display[x][y].equals(empty) && field[x][y].equals(empty)) {
            isDone = false;
            System.out.println("This tile has already been cleared!");
        }
    }

    // Determines if a player has cleared all safe tiles.
    void isVictory() {
        int tile = 0;
        for (String[] strings : field) {
            for (int j = 0; j < field[0].length; j++) {
                if (strings[j].equals(unknown))
                    tile++;
            }
        }

        if (tile != 0) {
            isWin = false;
        } else {
            // If the player has selected all the non-mine tiles. The player has won.
            isWin = true;
            isDone = true;
        }
    }

    // Determines if the game is finished.
    Boolean getDone() {
        return isDone;
    }

    // Determines if a player won.
    Boolean getWin() {
        return isWin;
    }

    // Displays location of mines at end of game.
    void onEnd() {
        printGame(field);
    }

    // Do not call this method in your solution.
    void doNotCall_RemoveMine(int x, int y) {
        field[x][y] = " ? ";
    }
}