
package Gui;

/**
 *
 * @author Mervan
 */
import Bingo.MultiLinkedList;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private CardPanel[] cardPanels;
    private JLabel drawnNumberLabel;

    public GameFrame(MultiLinkedList[] playerCards) {
        setTitle("Tombola Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize the panels for displaying cards
        JPanel cardsPanel = new JPanel(new GridLayout(1, playerCards.length));
        cardPanels = new CardPanel[playerCards.length];
        for (int i = 0; i < playerCards.length; i++) {
            cardPanels[i] = new CardPanel(playerCards[i]);
            cardsPanel.add(cardPanels[i]);
        }
        add(cardsPanel, BorderLayout.CENTER);

        // Label for showing the current drawn number
        drawnNumberLabel = new JLabel("Drawn Number: ", SwingConstants.CENTER);
        add(drawnNumberLabel, BorderLayout.NORTH);

        setVisible(true);
    }

    // Method to update the UI with the latest drawn number
public void setDrawnNumber(int number) {
    drawnNumberLabel.setText("Drawn Number: " + number);
    for (CardPanel cardPanel : cardPanels) {
        cardPanel.updateCard(number); // This now matches the updated CardPanel method
    }
}
}

