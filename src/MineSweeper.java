class MineSweeper {
    String[][] field;
    private String[][] display;

    private int fieldWidth;
    private int fieldHeight;

    private Boolean isDone = false;
    private Boolean isWin = false;

    private String unknown = " ? ";
    private String mine = " * ";
    private String empty = "   ";

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

    int[] pickSquare() {
        // implement your algorithm to select an x and an y. The first square will have coordinates (1, 1)
        // The x and y will define which square you have selected.
        // The minesweeper board you're playing with is the array "display". It is a 2d String array
        // It is not allowed to use the 2d array "field" since this is what the game uses to find the mines.
        // '?' is undiscovered and otherwise it will have numbers indicating the standard minesweeper gameplay.

        //TODO: Implement your solution here
        throw new UnsupportedOperationException("solution not yet implemented");

//        int x = 1;
//        int y = 1;
//
//        int[] pick = new int[2];
//        pick[0] = x;
//        pick[1] = y;
//        return pick;
    }

    private static void printGame(String[][] str) {
        for(int x = 1; x < str.length - 1; x++) {
            System.out.println("");
            for(int y = 1; y < str[x].length ; y++) {
                System.out.print("|");
                // Prints out content of each tile.
                System.out.print(str[x][y]);
            }
        }
        System.out.println("");
    }

    // Shows the field after every square selection.
    void print() {
        printGame(display);
    }

    // Places n mines at random on the field.
    void generateMinesRandom(int n) {
        for (int m = 0; m < n; m++) {
            //Loops until a mine is placed.
            while (true) {
                int x, y = 0;
                x = (int)(fieldWidth * Math.random()) + 1;
                y = (int)(fieldHeight * Math.random()) + 1;

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

    // On first move, this clears the area around the selected tile.
    void clear(int x, int y) {
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
    String getTile(int x, int y) {
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
            System.out.println("This tile's been cleared!");
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
}