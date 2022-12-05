public class App {
    public static void main(String[] args) throws Exception {
        start("Sudoku1.txt");
    }

    /**
     * Start AC-3 using the sudoku from the given filepath, and reports whether the sudoku could be solved or not, and how many steps the algorithm performed
     * 
     * @param filePath
     */
    public static void start(String filePath){
        Game game1 = new Game(new Sudoku(filePath));
        game1.showSudoku();

        if (game1.solve() && game1.validSolution()){
            System.out.println("Solved!");
            System.out.println('g');
        }
        else{
            System.out.println("Could not solve this sudoku :(");
        }
        game1.showSudoku();
    }
}
