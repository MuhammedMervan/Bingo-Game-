
package Bingo;

/**
 *
 * @author Mervan
 */
public class Node {
    int number;      // The number on the tombola card
    boolean isMarked; // Indicates whether this number has been marked
    Node right;              // Pointer to the next node in the same row
    Node down;               // Pointer to the next node in the same column

    /**
     * Constructor to create a node with a specific number.
     * Initially, the node is not marked, and its neighbors are set to null.
     *
     * @param number The number to be stored in this node.
     */
    public Node(int number) {
        this.number = number;
        this.isMarked = false;
        this.right = null;
        this.down = null;
    }

    // Getters and Setters

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isMarked() {                   
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getDown() {
        return down;
    }

    public void setDown(Node down) {
        this.down = down;
    }
}
