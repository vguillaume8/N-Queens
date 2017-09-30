// Vinston Guillaume

import java.util.*;

public class Queens {

    // The size of the board
    private int boardSize;              
    private Scanner console;            
    
    // Empty columns
    private boolean[] colEmpty;
    // Empty up diagonal
    private boolean[] upDiagEmpty; 
    // Empty down diagonal
    private boolean[] downDiagEmpty;   
    // If theres a queen already there 
    private boolean[][] queenOnSquare;  
    
    // Target soulutions
    private int solutionTarget;
    // Solutions found    
    private int solutionsFound;
    // Target steps   
    private int stepsShown;
    // Steps           
    private int stepsTaken;            
 //-----------------------------------------------------------------------------------------------------   
    
    
    // Sets the size of the board
    public Queens(int boardSize, int solutionTarget, int stepsShown, Scanner console) {
        this.boardSize = boardSize;
        this.solutionTarget = solutionTarget;
        this.stepsShown = stepsShown;
        this.console = console;
        
        solutionsFound = 0;
        stepsTaken = 0;
        
        colEmpty = new boolean[boardSize];
        upDiagEmpty = new boolean[2 * boardSize - 1];
        downDiagEmpty = new boolean[2 * boardSize - 1];
        queenOnSquare = new boolean[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                colEmpty[column] = true;
                upDiagEmpty[row + column] = true;
                downDiagEmpty[(boardSize - 1) + row - column] = true;
                queenOnSquare[row][column] = false;
            }
        }
    }
//-----------------------------------------------------------------------------------------------------
    
    //Finds empty spaces to place the queens
    public void placeQueen(int row, int column) {
        queenOnSquare[row][column] = true;
        colEmpty[column] = false;
        upDiagEmpty[row + column] = false;
        downDiagEmpty[(boardSize - 1) + row - column] = false;
        
        if (stepsTaken < stepsShown)
            displayBoard();
        stepsTaken++;
    }
//-----------------------------------------------------------------------------------------------------
    
    //Removes Queen if theres no other spaces to place Queen to move forward 
    public void removeQueen(int row, int column) {
        queenOnSquare[row][column] = false;
        colEmpty[column] = true;
        upDiagEmpty[row + column] = true;
        downDiagEmpty[(boardSize - 1) + row - column] = true;
        
        if (stepsTaken < stepsShown)
            displayBoard();
        stepsTaken++;
    }
//-----------------------------------------------------------------------------------------------------
  
    // If Queen is safe in space and can not be attacked
    public boolean isSafe(int row, int column) {
        return (colEmpty[column] && 
                upDiagEmpty[row + column]&& 
                downDiagEmpty[(boardSize - 1) + row - column]); 
    }
//-----------------------------------------------------------------------------------------------------
    
    // Looks for next safe space
    public void findSafeColumn(int row) {
        if (row == boardSize) {  // base case: a solution!
            solutionsFound++;
            displayBoard();
            if (solutionsFound >= solutionTarget)
                System.exit(0);
            return;
        } 
//-----------------------------------------------------------------------------------------------------
        
        // Searches through the entire colum
        for (int column = 0; column < boardSize; column++) {
            if (isSafe(row, column)) {
                placeQueen(row, column);
                
                // Move onto the next row.
                findSafeColumn(row + 1);
                
                // If we get here, we've backtracked.
                removeQueen(row, column);
            }
        }
    }     
//-----------------------------------------------------------------------------------------------------    
    
    // Shows the current state of the board
    public void displayBoard() {
        System.out.println("Solutions Found: " + solutionsFound);
        System.out.println();
        
        for (int row = -1; row < boardSize; row++) {
            for (int column = -1; column < boardSize; column++) {
                if (row == -1 && column >= 0) {
                    if (column < 10)
                        System.out.print(" ");
                    System.out.print(column);
                } else if (column == -1 && row >= 0) {
                    if (row < 10)
                        System.out.print(" ");
                    System.out.print(row);
                } else if (row >= 0 && column >= 0 && queenOnSquare[row][column]) {
                    System.out.print(" Q");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        // Exit program
        System.out.println();
        System.out.println("Press Enter to continue or enter E to exit.");
        String input = console.nextLine();
        if (input.equalsIgnoreCase("E")) {
            System.exit(0);
        }
    }
//-----------------------------------------------------------------------------------------------------
    
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        
        System.out.print("How many Queens are on the board? ");
        int amount = console.nextInt();
        console.nextLine();
        
        
        int numberOfSolns = (800);
        int numberOfSteps = 100;
        
        
        Queens q = new Queens(amount, numberOfSolns, numberOfSteps, console);
        q.findSafeColumn(0);    // start with row 0
    }
}