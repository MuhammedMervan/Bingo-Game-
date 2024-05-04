
package Bingo;

/**
 *
 * @author Mervan
 */
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RandomPermutationGenerator {
    /**
     * Generates a random permutation of integers from 1 to maxNumber.
     * 
     * @param maxNumber The maximum number in the range.
     * @return A shuffled LinkedList<Integer> containing numbers from 1 to maxNumber.
     */
    public static LinkedList<Integer> generatePermutation(int maxNumber) {
        List<Integer> numbers = new LinkedList<>();
        // Populate the list with numbers from 1 to maxNumber
        for (int i = 1; i <= maxNumber; i++) {
            numbers.add(i);
        }
        // Shuffle the list to create a random permutation
        Collections.shuffle(numbers);
        // Return the shuffled list as a LinkedList for consistency with the method signature
        return new LinkedList<>(numbers);
    }
}

