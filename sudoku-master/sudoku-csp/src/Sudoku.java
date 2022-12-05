import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sudoku {
    private Field[][] board;

    Sudoku(String filename) {
        this.board = readsudoku(filename);
    }

    @Override
    public String toString() {
        String output = "╔═══════╦═══════╦═══════╗\n";
        for(int i=0;i<9;i++){
            if(i == 3 || i == 6) {
                output += "╠═══════╬═══════╬═══════╣\n";
            }
            output += "║ ";
            for(int j=0;j<9;j++){
                if(j == 3 || j == 6) {
                    output += "║ ";
                }
                output += board[i][j] + " ";
            }

            output += "║\n";
        }
        output += "╚═══════╩═══════╩═══════╝\n";
        return output;
    }

    /**
     * Reads sudoku from file
     * @param filename
     * @return 2d int array of the sudoku
     */
    public static Field[][] readsudoku(String filename) {
        assert filename != null && filename != "" : "Invalid filename";
        String line = "";
        Field[][] grid = new Field[9][9];
        try {
            FileInputStream inputStream = new FileInputStream(filename);
            Scanner scanner = new Scanner(inputStream);
            for(int i = 0; i < 9; i++) {
                if(scanner.hasNext()) {
                    line = scanner.nextLine();
                    for(int j = 0; j < 9; j++) {
                        int numValue = Character.getNumericValue(line.charAt(j));
                        if(numValue == 0) {
                            grid[i][j] = new Field();
                        } else if (numValue != -1) {
                            grid[i][j] = new Field(numValue);
                        }
                    }
                }
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("error opening file: "+filename);
        }
        addNeighbours(grid);
        return grid;
    }

    /**
     * Adds a list of neighbours to each field, i.e., arcs to be satisfied
     * @param grid
     */
    private static void addNeighbours(Field[][] grid) {
        // TODO: for each field, add its neighbours

//      for (Field[] row: grid) {
//          for (Field column: row) {
//              neighbours.add(getRow(row))
//          }
//      }

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                setRowNeighbours(grid, row, column);
            }
        }
    }

    private static boolean setRowNeighbours(Field[][] grid, int row, int column) {
        List<Field> neighbours = new ArrayList<>();

        for (int i = 0; i < grid[row].length; i++) {
            if (i != column)
                neighbours.add(grid[row][i]);
        }

        return setColumnNeighbours(grid, row, column, neighbours);
    }

    private static boolean setColumnNeighbours(Field[][] grid, int row, int column, List<Field> neighbours) {
        for (int i = 0; i < grid.length; i++) {
            if (i != row)
                neighbours.add(grid[i][column]);
        }

        return setLocalSquareNeighbours(grid, row, column, neighbours);
    }

    private static boolean setLocalSquareNeighbours(Field[][] grid, int row, int column, List<Field> neighbours) {
        // I could create a subarray of small square and overwrite it
        if (row < 2) {
            if (column < 2) {
                List<Field> squareFields = new ArrayList<>(9);
//                squareFields = getSubgridFields(+)
            }
        }

        grid[row][column].setNeighbours(neighbours);

        return true;
    }

    /**
     * Generates fileformat output
     */
    public String toFileString(){
        String output = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                output += board[i][j].getValue();
            }
            output += "\n";
        }
        return output;
    }

    public Field[][] getBoard(){
        return board;
    }
}
