
package Bingo;

/**
 *
 * @author Mervan
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiLinkedList {
    Node head;

    // Assuming a fixed card size for simplicity
    private static final int ROWS = 3;
    private static final int COLUMNS = 9;
    private static final int NUMBERS_PER_ROW = 5;

    public MultiLinkedList() {
        generateCard();
    }

        private void generateCard() {
        List<Integer>[] columnNumbers = new ArrayList[COLUMNS];
        // Adjusting ranges for each column
        for (int i = 0; i < COLUMNS; i++) {
            columnNumbers[i] = new ArrayList<>();
            int start = i * 10 + 1;
            int end = start + 10; // Adjust for last column to include 90
            if (i == 8) end--; // Ensuring we include 90 in the last column
            
            for (int j = start; j < end; j++) {
                columnNumbers[i].add(j);
            }
            Collections.shuffle(columnNumbers[i]);
        }

        // Generating card with correct distribution and blocking logic
        Node[][] grid = new Node[ROWS][COLUMNS];
        for (int row = 0; row < ROWS; row++) {
            List<Node> filledPositions = new ArrayList<>();
            while (filledPositions.size() < NUMBERS_PER_ROW) {
                int col = (int) (Math.random() * COLUMNS);
                if (grid[row][col] == null) {
                    Node newNode = new Node(columnNumbers[col].remove(0));
                    grid[row][col] = newNode;
                    filledPositions.add(newNode);
                }
            }
            // Fill remaining spots with blocked (-1) nodes
            for (int col = 0; col < COLUMNS; col++) {
                if (grid[row][col] == null) {
                    grid[row][col] = new Node(-1); // Indicating blocked box
                }
            }
        }
        
        linkNodes(grid);
        head = grid[0][0];
    }
    
    
    private void linkNodes(Node[][] grid) {
        // Linking logic remains the same
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS - 1; col++) {
                grid[row][col].right = grid[row][col + 1];
            }
        }
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS - 1; row++) {
                grid[row][col].down = grid[row + 1][col];
            }
        }
    }
        
    
    // Mark a number as drawn. Return true if the number was found and marked, false otherwise.
    public boolean markNumber(int number) {
        Node current = head;
        while (current != null) {
            Node temp = current;
            while (temp != null) {
                if (temp.number == number) {
                    temp.isMarked = true;
                    return true;
                }
                temp = temp.right;
            }
            current = current.down;
        }
        return false;
    }

    // Hypothetical implementation inside MultiLinkedList class
public int[][] getCardNumbers() {
    int[][] cardNumbers = new int[3][9]; // Assuming a fixed card size for simplicity
    // Populate this array based on the actual card structure
    // For demonstration purposes, let's fill it with sequential numbers
    int number = 1;
    for (int row = 0; row < 3; row++) {
        for (int col = 0; col < 9; col++) {
            // Just as an example, assign numbers sequentially
            // Your actual logic will vary depending on how you store and retrieve card data
            cardNumbers[row][col] = number++;
        }
    }
    return cardNumbers;
}
    // Method to get the node at a specific position, if not already implemented
    public Node getNodeAt(int row, int col) {
        Node currentRow = head;
        // Navigate to the correct row
        for (int i = 0; i < row && currentRow != null; i++) {
            currentRow = currentRow.getDown();
        }
        if (currentRow == null) {
            return null;
        }
        Node currentCol = currentRow;
        // Navigate to the correct column
        for (int i = 0; i < col && currentCol != null; i++) {
            currentCol = currentCol.getRight();
        }
        return currentCol;
    }

    // Checks if the card has a valid number of marked nodes
    public boolean isCardValid() {
        int markedCount = 0;
        Node currentRow = head;
        for (int row = 0; row < 3; row++) {
            Node currentCol = currentRow;
            for (int col = 0; col < 9 && currentCol != null; col++) {
                if (currentCol.getNumber() > 0 && currentCol.isMarked()) {
                    markedCount++;
                }
                currentCol = currentCol.getRight();
            }
            if (currentRow != null) {
                currentRow = currentRow.getDown();
            }
        }
        return markedCount <= 15;
    }

    // Print the current state of the card
    public void printCard() {
        Node currentRow = head;
        while (currentRow != null) {
            Node currentCol = currentRow;
            while (currentCol != null) {
                System.out.print(currentCol.number > 0 ? (currentCol.isMarked ? "[" + currentCol.number + "]" : currentCol.number) : "XX");
                System.out.print("\t");
                currentCol = currentCol.right;
            }
            System.out.println();
            currentRow = currentRow.down;
        }
    }
        // Method to check if a number is marked
    public boolean isNumberMarked(int number) {
        Node currentRow = head;
        while (currentRow != null) {
            Node currentCol = currentRow;
            while (currentCol != null) {
                if (currentCol.number == number && currentCol.isMarked) {
                    return true; // The number is found and marked
                }
                currentCol = currentCol.right; // Move to the next column in the row
            }
            currentRow = currentRow.down; // Move to the next row
        }
        return false; // The number is not found or not marked
    }
    
    // Inside MultiLinkedList.java
public int checkWinCondition() {
    int[] rowMarks = new int[3]; // Track marked numbers in each row
    int totalMarks = 0; // Track total marked numbers

    Node currentRow = head;
    for (int i = 0; i < 3 && currentRow != null; i++) {
        Node currentCol = currentRow;
        while (currentCol != null) {
            if (currentCol.isMarked && currentCol.number > 0) {
                rowMarks[i]++;
                totalMarks++;
            }
            currentCol = currentCol.right;
        }
        currentRow = currentRow.down;
    }

    // Check win conditions
    for (int rowMark : rowMarks) {
        if (rowMark == 5) { // Birinci Çinko
            return 1;
        }
    }
    if (totalMarks >= 10) { // Ikinci Çinko (simplified check)
        return 2;
    }
    if (totalMarks == 15) { // Tombala
        return 3;
    }
    return 0; // No win
}

}

