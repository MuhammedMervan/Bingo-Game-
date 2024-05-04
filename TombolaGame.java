/**
 *
 * @author Mervan
 */
package Bingo;

import Gui.CardPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

public class TombolaGame {
    private MultiLinkedList[] playerCards;
    private LinkedList<Integer> drawNumbers;
    private JFrame frame;
    private JButton drawButton;
    private CardPanel[] cardPanels; // Array to hold CardPanel references
    private JLabel statusLabel; // Optional: Display game status messages
    private JPanel cardsPanel;

    private static final int TOTAL_NUMBERS = 90;
    private int numberOfPlayers;

    public TombolaGame(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        playerCards = new MultiLinkedList[numberOfPlayers];
        drawNumbers = RandomPermutationGenerator.generatePermutation(TOTAL_NUMBERS);
        cardPanels = new CardPanel[numberOfPlayers]; // Initialize the array
        initializeGame();
        initializeGUI();
    }

    private void initializeGame() {
        for (int i = 0; i < numberOfPlayers; i++) {
            playerCards[i] = new MultiLinkedList(); // Each player gets a randomly generated card
        }
    }

private void initializeGUI() {
    frame = new JFrame("Tombola Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    
    frame.setSize(1000, 1800);

    // Configuration for the draw button
    drawButton = new JButton("Draw Number");
    drawButton.setForeground(Color.darkGray);
    drawButton.setFont(new Font("Arial", Font.BOLD, 14));
    drawButton.addActionListener(this::drawNumberAction);
    frame.add(drawButton, BorderLayout.SOUTH);

    // Creating and configuring the cards panel
    cardsPanel = new JPanel(new GridLayout(1, numberOfPlayers, 10, 10)); // Add horizontal and vertical gaps
    cardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the cards panel
    cardsPanel.setBackground(Color.lightGray); // Set a background color for the container of the cards

    for (int i = 0; i < numberOfPlayers; i++) {
        CardPanel cardPanel = new CardPanel(playerCards[i]);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5), // Outer padding
            BorderFactory.createLineBorder(Color.darkGray) // Inner line border
        ));
        cardPanel.setBackground(Color.white); // Set a background color for the card itself
        cardsPanel.add(cardPanel);
        cardPanels[i] = cardPanel;
    }

    frame.add(cardsPanel, BorderLayout.CENTER);

    // Status label at the top of the window
    statusLabel = new JLabel("Ready to start the game", SwingConstants.CENTER);
    statusLabel.setFont(new Font("Serif", Font.BOLD, 16));
    frame.add(statusLabel, BorderLayout.PAGE_START);

    frame.pack();
    frame.setVisible(true);
}

private void markNumberAndCheckWin(int drawnNumber) {
    int winState = 0;
    int winningPlayerIndex = -1; // -1 indicates no winner
    
    // Mark the drawn number on each player's card and check for win conditions
    for (int i = 0; i < playerCards.length; i++) {
        MultiLinkedList card = playerCards[i];
        boolean marked = card.markNumber(drawnNumber);
        if (marked) {
            System.out.println("Player " + (i + 1) + " marked number " + drawnNumber);
        }
        
        // Check if the card is still valid after marking the number
        boolean isValid = card.isCardValid();
        if (!isValid) {
            System.err.println("Error: Player " + (i + 1) + "'s card has exceeded the maximum allowed marked numbers.");
            continue; // You might want to handle this differently, depending on game rules
        }

        // Check for any win condition on the card
        int checkWin = card.checkWinCondition();
        if (checkWin > winState) {
            winState = checkWin;
            winningPlayerIndex = i;
        }
    }

    // Update the UI with the current game state
    updateGUIAfterDraw(drawnNumber, winState, winningPlayerIndex);

    // If a winning condition is detected, handle the win
    if (winState > 0) {
        handleWin(winningPlayerIndex, winState);
        endGame();
    }
}    
    // Inside TombolaGame.java, modify drawNumberAction
private void drawNumberAction(ActionEvent event) {
    if (!drawNumbers.isEmpty()) {
        int drawnNumber = drawNumbers.poll();

        // Variables to track win state and winning player index
        int winState = 0;
        int winningPlayerIndex = -1;

        // Iterate over all player cards to mark the drawn number
        for (int i = 0; i < numberOfPlayers; i++) {
            boolean marked = playerCards[i].markNumber(drawnNumber);
            if (marked) {
                System.out.println("Player " + (i + 1) + " has the number " + drawnNumber);
                // If a number is marked, ensure the corresponding card panel is updated
                cardPanels[i].updateCard();
            }

            // After marking, check if this action led to a win condition
            int checkWin = playerCards[i].checkWinCondition();
            if (checkWin > winState) {
                winState = checkWin;
                winningPlayerIndex = i;
            }
        }

        // Update GUI based on the current drawn number, win state, and winning player index
        updateGUIAfterDraw(drawnNumber, winState, winningPlayerIndex);

        // If there is a win state, disable the draw button and declare the game over
        if (winState != 0) {
            drawButton.setEnabled(false);
            drawButton.setText("Game Over - We have a winner!");
        } else {
            // Otherwise, update the button text with the last drawn number
            drawButton.setText("Draw Number (Last: " + drawnNumber + ")");
        }
    } else {
        // If no numbers are left, disable the draw button and declare the game over
        drawButton.setEnabled(false);
        drawButton.setText("Game Over");
    }
}


    private void checkForWin(int drawnNumber) {
        for (int i = 0; i < numberOfPlayers; i++) {
            int winCondition = playerCards[i].checkWinCondition();
            if (winCondition > 0) {
                handleWin(i, winCondition);
                return; // Stops the game if there's a winner
            }
        }
    }

private void handleWin(int playerIndex, int winState) {
    // Construct a win message based on the winState
    String winMessage = "";
    switch (winState) {
        case 1: // Assuming 1 is for Birinci Çinko
            winMessage = "Player " + (playerIndex + 1) + " says Birinci Çinko!";
            break;
        case 2: // Assuming 2 is for İkinci Çinko
            winMessage = "Player " + (playerIndex + 1) + " says İkinci Çinko!";
            break;
        case 3: // Assuming 3 is for Tombala
            winMessage = "Player " + (playerIndex + 1) + " says Tombala!";
            break;
        // Add other cases if there are more win states
    }

    // Show a dialog with the win message
    JOptionPane.showMessageDialog(frame, winMessage, "We have a winner!", JOptionPane.INFORMATION_MESSAGE);

    // Update the status label with the win message
    if (statusLabel != null) {
        statusLabel.setText(winMessage);
    }

    // Disable the draw button as the game is now over
    if (drawButton != null) {
        drawButton.setEnabled(false);
        drawButton.setText("Game Over - We have a winner!");
    }

    // Here you could also add any additional logic needed to reset the game or prepare for a new round
}

private void endGame() {
    // Disable the draw button to prevent any more numbers from being drawn
    drawButton.setEnabled(false);
    drawButton.setText("Game Over");

    // Update the status label to indicate that the game is over
    if (statusLabel != null) {
        statusLabel.setText("Game Over - Thanks for playing!");
    }

    // Clear the card panels
    for (CardPanel cardPanel : cardPanels) {
        if (cardPanel != null) {
            cardPanel.clearCardDisplay();
        }
    }

    // Ask the user if they want to play again
    int response = JOptionPane.showConfirmDialog(frame, "Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

    if (response == JOptionPane.YES_OPTION) {
        // Reset the game for a new round
        resetGame();
    } else {
        // Close the application window
        frame.dispose();
    }
}


// Inside TombolaGame.java
private void resetGame() {
    System.out.println("Resetting game...");

    // Reset the draw numbers list for a new game
    drawNumbers = RandomPermutationGenerator.generatePermutation(TOTAL_NUMBERS);
    
    System.out.println("Draw numbers reset.");

    // Recreate the cards for a new game
    for (int i = 0; i < numberOfPlayers; i++) {
        playerCards[i] = new MultiLinkedList();
        System.out.println("New card created for player " + (i + 1));

        cardPanels[i].setCard(playerCards[i]); // Update the card panel with the new card
        System.out.println("Card panel updated for player " + (i + 1));
    }

    // Reset the status label for the new game
    if (statusLabel != null) {
        statusLabel.setText("New game ready to start!");
        System.out.println("Status label updated.");
    }

    // Re-enable the draw button for the new game
    drawButton.setEnabled(true);
    drawButton.setText("Draw Number");
    System.out.println("Draw button re-enabled.");
}



    
    public void drawAndMarkNumber() {
        if (!drawNumbers.isEmpty()) {
            int drawnNumber = drawNumbers.poll();
            // Mark the drawn number on each player's card
            for (MultiLinkedList card : playerCards) {
                card.markNumber(drawnNumber);
            }

            // Update the card panels to reflect the new state
            for (CardPanel panel : cardPanels) {
                panel.updateCard(drawnNumber); // Update GUI
            }

            // Optionally, update a status label with the drawn number
            if (statusLabel != null) {
                statusLabel.setText("Last drawn number: " + drawnNumber);
            }
        } else {
            // Handle end of game
            if (drawButton != null) {
                drawButton.setEnabled(false);
                drawButton.setText("Game Over");
            }
            if (statusLabel != null) {
                statusLabel.setText("All numbers have been drawn. Game Over.");
            }
        }
    }

    public void setCardPanels(CardPanel[] panels) {
        this.cardPanels = panels;
    }

private void updateGUIAfterDraw(int drawnNumber, int winState, int playerIndex) {
        SwingUtilities.invokeLater(() -> {
        for (CardPanel panel : cardPanels) {
            if (panel != null) {
                panel.updateCard(drawnNumber); // This ensures that you don't call updateCard on a null object
            } else {
                System.err.println("A CardPanel in the array is null.");
            }
        }
            // Check and display win conditions
            String winMessage = "";
            switch (winState) {
                case 1:
                    winMessage = "Player " + (playerIndex + 1) + " says Birinci Çinko!";
                    break;
                case 2:
                    winMessage = "Player " + (playerIndex + 1) + " says Ikinci Çinko!";
                    break;
                case 3:
                    winMessage = "Player " + (playerIndex + 1) + " says Tombala! Game Over.";
                    JOptionPane.showMessageDialog(frame, "Congratulations, Player " + (playerIndex + 1) + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    drawButton.setEnabled(false);
                    drawButton.setText("Game Over - We have a winner!");
                    break;
                default:
                    // No win condition, just update the drawn number
                    winMessage = "Drawn Number: " + drawnNumber;
                    break;
            }

            // Assuming 'statusLabel' exists to show the game status or win message
            if (statusLabel != null) {
                statusLabel.setText(winMessage);
            }

            // Refresh card panels for visual updates
            for (CardPanel panel : cardPanels) {
                panel.updateCard(drawnNumber);
            }
        
    }
        );
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TombolaGame(2));
    }
}
