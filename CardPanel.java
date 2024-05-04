
//package Gui;
//
///**
// *
// * @author Mervan
// */
package Gui;

import Bingo.MultiLinkedList;
import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {
    private MultiLinkedList card;
    private final JLabel[][] numberLabels;
    

    public CardPanel(MultiLinkedList card) {
        this.card = card;
        setLayout(new GridLayout(3, 9)); // Tombola card layout
        numberLabels = new JLabel[3][9];
        initializeCardDisplay();
//        updateCardDisplay();
    }

    private void initializeCardDisplay() {
        int[][] cardNumbers = card.getCardNumbers(); // Fetch the 2D array representation
        for (int row = 0; row < cardNumbers.length; row++) {
            for (int col = 0; col < cardNumbers[row].length; col++) {
                String text = cardNumbers[row][col] > 0 ? String.valueOf(cardNumbers[row][col]) : "";
                numberLabels[row][col] = new JLabel(text, SwingConstants.CENTER);
                numberLabels[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                add(numberLabels[row][col]);
            }
        }
    }
    
        // Method to associate a new card with the panel
public void setCard(MultiLinkedList newCard) {
    System.out.println("Setting new card in CardPanel...");
    this.card = newCard;
    clearCardDisplay();
    updateCardDisplay();
}
    
        // Method to update the display to reflect the current state of the card
    public void updateCardDisplay() {
    System.out.println("Updating CardPanel display...");
    // Assuming you're using a grid layout of labels to display the card's numbers
    int[][] cardNumbers = card.getCardNumbers();

        for (int row = 0; row < cardNumbers.length; row++) {
            for (int col = 0; col < cardNumbers[row].length; col++) {
                // Update each label with the new number or clear it if the number is not valid (e.g., -1 for an empty space)
                String numberText = cardNumbers[row][col] > 0 ? Integer.toString(cardNumbers[row][col]) : "";
                numberLabels[row][col].setText(numberText);
                // Reset any specific styling for marked numbers here as well
                numberLabels[row][col].setForeground(Color.BLACK); // Default text color
                numberLabels[row][col].setBackground(Color.WHITE); // Default background
            }
        }
        revalidate(); // Recalculate the layout
    repaint(); // Redraw the panel    
    }
    
        // Method to clear the card display
    public void clearCardDisplay() {
        System.out.println("Clearing card display...");
        for (int row = 0; row < numberLabels.length; row++) {
            for (int col = 0; col < numberLabels[row].length; col++) {
                if (numberLabels[row][col] != null) {
                    numberLabels[row][col].setText(""); // Clear the text
                    numberLabels[row][col].setBackground(Color.white); // Reset the background color
                    numberLabels[row][col].setOpaque(false); // Make it non-opaque
                    numberLabels[row][col].setBorder(BorderFactory.createLineBorder(Color.lightGray)); // Reset the border to default
                }
            }
        }
    }

    // Update the card's display to reflect the drawn number and current state
    public void updateCard(int drawnNumber) {
        // Mark the drawn number if present
        boolean isNumberPresent = card.markNumber(drawnNumber);

        // Refresh the entire card display to show any changes
        int[][] cardNumbers = card.getCardNumbers();
        for (int row = 0; row < cardNumbers.length; row++) {
            for (int col = 0; col < cardNumbers[row].length; col++) {
                if (cardNumbers[row][col] > 0) {
                    numberLabels[row][col].setText(String.valueOf(cardNumbers[row][col]));
                    if (card.isNumberMarked(cardNumbers[row][col])) {
                        // Mark the number as red to indicate it's been drawn
                        numberLabels[row][col].setForeground(Color.RED);
                    } else {
                        // Reset to default color if not marked
                        numberLabels[row][col].setForeground(Color.BLACK);
                    }
                } else {
                    // Handle blank cells if necessary
                    numberLabels[row][col].setText("");
                }
            }
        }

        // Optionally, handle isNumberPresent for additional logic
    }
        public void updateCard() {
        int[][] cardNumbers = card.getCardNumbers(); // Re-fetch the card numbers to reflect current state
        for (int row = 0; row < cardNumbers.length; row++) {
            for (int col = 0; col < cardNumbers[row].length; col++) {
                if (cardNumbers[row][col] > 0) {
                    numberLabels[row][col].setText(String.valueOf(cardNumbers[row][col]));
                    if (card.isNumberMarked(cardNumbers[row][col])) { // Check if the number is marked
                        numberLabels[row][col].setForeground(Color.RED); // Mark the number as red
                    } else {
                        numberLabels[row][col].setForeground(Color.BLACK); // Otherwise, reset to default color
                    }
                } else {
                    numberLabels[row][col].setText("");
                }
            }
        }
    }
}

