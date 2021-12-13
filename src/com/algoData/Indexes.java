package com.algoData;

/*
        Safely handles indexing of folding list
 */
public class Indexes {

    private int firstIndex;
    private int lastIndex;

    public Indexes(int lastIndex) {
        firstIndex = 0;
        this.lastIndex = lastIndex;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    /**
     * Left side is stable. Right side is in the move.
     * We are folding right side on the left one
     * Left side >= Right side
     * @return For odd length, returns middle character
     *         For even length, returns middle + 1 character
     */
    public static int getMaxFoldIndexOfSide(int endIndex) {
        return (endIndex + 1) / 2;
    }

    /**
     *
     *         isRightSide == true
     *         _ _ _ _ _     _ _ _ _
     *              ^           ^
     *
     *         isRightSide == false
     *         _ _ _ _ _     _ _ _ _
     *           ^             ^
     *
     * @param startIndex
     * @param endIndex
     * @param isRightSide
     * @return
     */
    public static int getMaxFoldIndexOfSide(int startIndex, int endIndex, boolean isRightSide) {
        int size = endIndex - startIndex + 1;

        return startIndex + ((isRightSide ? size + 1 : size - 2) / 2);
    }

    /**
     *  `   _ _ _ _ _
     *      ^    <- ^
     *
     * @param foldSize
     */
    public void decreaseLastIndex(int foldSize) {
        lastIndex -= foldSize;
    }

    /**
     *  `   _ _ _ _ _
     *      ^ ->    ^
     *
     * @param foldSize
     */
    public void increaseFirstIndex(int foldSize) {
        firstIndex += foldSize;
    }


}
