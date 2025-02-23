package com.radboud;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Folds stamps using indices
 *
 * Contains all the helper functions used to determine the minimum number of folds needed to fold the stamps
 *
 * Attributes
 * foldList: input array containing Folds
 * memory: 2d array where previously computed results are stored for efficiency
 *
 * Outputs
 * prints foldCount: Minimum number of folds needed to fold the stamps as an integer
 */
public class Folding {

    // Works with indexes.
    // Size remains same, elements are not deleted.
    private final List<Fold> foldList;
    private final int[][] memory;

    /**
     * Constructor for the class, initializes foldList and memory
     * Fills memory with zeros and foldList with Folds based on characters in input
     * @param input input string containing M's and V's. Assumes everything not an 'M' is a 'V', throws no exception for
     *              incorrect inputs
     */
    public Folding(String input) {
        foldList = new ArrayList<>();
        memory = new int[input.length()][];

        for(int i = 0; i < input.length(); i++) {
            foldList.add( input.charAt(i) == 'M' ? Fold.MOUNTAIN : Fold.VALLEY );
            memory[i] = new int[input.length() - i];

            Arrays.fill(memory[i], 0);
        }
    }

    /**
     * Checks if values in foldVector from leftIndex to rightIndex are not mirrored. Indices are moving towards each
     * other; if they meet, the Folds fit and can be folded on top of each other.
     * Visualization of the index movement:
     *
     *          .....v.....    .....v.....    .....v.....    .....v.....    .....v.....
     *          ^         ^     ^       ^       ^     ^         ^   ^           ^ ^
     *
     * @param leftIndex left border for which symmetry is checked
     * @param rightIndex right border for which symmetry is checked
     * @throws IllegalArgumentException - When sum of arguments is odd
     * @return false if the folds at the indices are the same anywhere, true otherwise
     */
    private boolean isFoldPossible(int leftIndex, int rightIndex) throws IllegalArgumentException {
        if(leftIndex+rightIndex % 2 == 1) {
            throw new IllegalArgumentException("Range has to have ODD SIZE");
        }

        while(leftIndex + 1 < rightIndex) {
            // For successful fold, fold types on both indexes has to be opposite value
            if( foldList.get(leftIndex) == foldList.get(rightIndex) ){
                return false;
            }
            leftIndex++;
            rightIndex--;
        }
        return true;
    }

    /**
     * Recursive method to fold the stamps. Contains two base cases: either lastIndex and firstIndex are the same, in
     * which case a single fold connects them. If they are only a single position apart, two folds are required.
     * If the interval is larger, firstly the memory is checked whether the result is already computed.
     * If it isn't, the largest fold on the left-hand and right-hand side is computed, and for each side the recursive
     * call is made to find which side results in a smaller number of folds. The smallest result is stored and returned.
     * @param firstIndex Start of the interval over which the smallest number of folds is determined
     * @param lastIndex End of the interval over which the smallest number of folds is determined
     * @return minimum required number of folds for this interval as integer.
     */
    private int foldMethod(final int firstIndex, final int lastIndex) {

        // BASE CASES
        //      1 Fold
        if(lastIndex - firstIndex == 0) {
            return 1;
        }
        //      2 Folds
        if(lastIndex - firstIndex == 1) {
            return 2;
        }

        // Look into memory, Result might be already computed
        if( memory[firstIndex][lastIndex-firstIndex] != 0 ) {
            return memory[firstIndex][lastIndex-firstIndex];
        }

        int maxRFold = maxRightFold(lastIndex, getMaxFoldIndexOfSide(firstIndex, lastIndex,true));
        int maxLFold = maxLeftFold(firstIndex, getMaxFoldIndexOfSide(firstIndex, lastIndex, false));
        int leftFoldResult = foldMethod(firstIndex + maxLFold, lastIndex);
        int rightFoldResult = foldMethod(firstIndex, lastIndex - maxRFold);

        int result = Math.min(leftFoldResult, rightFoldResult) + 1;

        // Save result
        memory[firstIndex][lastIndex-firstIndex] = result;

        return result;
    }

    /**
     * Determines the size of the largest possible fold on the right-hand side as integer.
     * @param lastIndex The current right-most index
     * @param maxRightFoldIndex Left-hand side of the largest theoretical possible fold
     * @return size of the largest possible valid fold on the right-hand side as integer
     */
    private int maxRightFold(int lastIndex, int maxRightFoldIndex) {
        // Number of edges (folds) which will reduce foldVector
        int foldSize = lastIndex - maxRightFoldIndex + 1;
        int leftIndex = maxRightFoldIndex - foldSize + 1;

        while( 0 < foldSize ) {
            if( isFoldPossible(leftIndex, lastIndex) ) {
                return foldSize;
            }
            // Move index to the right
            leftIndex += 2;
            foldSize--;
        }
        return 0;
    }

    /**
     * Determines the size of the largest possible fold on the left-hand side as integer.
     * @param firstIndex The current left-most index
     * @param maxLeftFoldIndex Right-hand side of the largest theoretical possible fold
     * @return size of the largest possible valid fold on the left-hand side as integer
     */
    private int maxLeftFold(int firstIndex, int maxLeftFoldIndex) {
        // Number of edges (folds) which will reduce foldVector
        int foldSize = maxLeftFoldIndex - firstIndex + 1;
        int rightIndex = maxLeftFoldIndex + foldSize - 1;

        while( 0 < foldSize ) {
            if( isFoldPossible(firstIndex, rightIndex) ) {
                return foldSize;
            }
            // Move index to the left
            rightIndex -= 2;
            foldSize--;
        }
        return 0;
    }

    /**
     * Makes the first recursive call and prints the result.
     */
    public int run() {
        int foldCount = foldMethod(0, foldList.size() - 1);
        System.out.println(foldCount);
        return foldCount;
    }

    /**
     * Determines the largest theoretical fold from firstIndex to lastIndex; see visualization below
     * @param firstIndex Left-most border
     * @param lastIndex Right-most border
     * @param isRightSide True if going right to left, false otherwise
     * @return largest theoretical fold from firstIndex to lastIndex
     *
     *         isRightSide == true
     *         _ _ _ _ _ or _ _ _ _
     *             ^            ^
     *
     *         isRightSide == false
     *         _ _ _ _ _ or _ _ _ _
     *             ^          ^
     *
     */
    public static int getMaxFoldIndexOfSide(int firstIndex, int lastIndex, boolean isRightSide) {
        int size = lastIndex - firstIndex + 1;
        return firstIndex + ((isRightSide ? size : size - 2) / 2);
    }

}
