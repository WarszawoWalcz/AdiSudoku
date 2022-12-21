import java.util.*;

public class Game {
  private Sudoku sudoku;

  Game(Sudoku sudoku) {
    this.sudoku = sudoku;
  }

  public void showSudoku() {
    System.out.println(sudoku);
  }

  /**
   * Implementation of the AC-3 algorithm
   * 
   * @return true if the constraints can be satisfied, else false
   */
  public boolean solve() {
    AC3 solver = new AC3(sudoku.getBoard());

    if(solver.run()) {
      // The puzzle is solved successfully
      return true;
    }
    else {
      // The puzzle is impossible to solve
      return false;
    }
  }

  /**
   * Checks the validity of a sudoku solution
   * 
   * @return true if the sudoku solution is correct
   */
  public boolean validSolution() {
    // TODO: implement validSolution function
    for (int row = 0; row < sudoku.getBoard().length; row++) {
      for (int column = 0; column < sudoku.getBoard()[row].length; column++) {
        System.out.print(sudoku.getBoard()[row][column].getValue() + " ");
      }
      System.out.println();
    }
    return true;
  }

}
