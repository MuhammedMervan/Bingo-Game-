
package Bingo;

/**
 *
 * @author Mervan
 */
import Gui.CardPanel;
import java.awt.GridLayout;
import javax.swing.*;

public class GameRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Initialize game components
            int numberOfPlayers = 2; // Example
            TombolaGame game = new TombolaGame(numberOfPlayers);
            
            JFrame frame = new JFrame("Tombola Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(1, numberOfPlayers)); // Layout based on number of players
            
            // Initialize and add card panels
            CardPanel[] cardPanels = new CardPanel[numberOfPlayers];
            for (int i = 0; i < numberOfPlayers; i++) {
                cardPanels[i] = new CardPanel(new MultiLinkedList()); // Example initialization
                frame.add(cardPanels[i]);
            }
            
            game.setCardPanels(cardPanels); // Link card panels to the game logic
            
            frame.pack();
            frame.setVisible(true);
            
            // Start the game (could be triggered by a button or other input in a complete implementation)
            game.drawAndMarkNumber(); // Example call - typically, you'll have more game control logic
        });
    }
}

