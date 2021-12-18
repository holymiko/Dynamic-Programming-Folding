package com.algoData;

/*
        Safely handles indexing of folding list
 */
public class Indexes {

    /**
     * @param firstIndex
     * @param lastIndex
     * @param isRightSide
     * @return
     *
     *         isRightSide == true
     *         _ _ _ _ _     _ _ _ _
     *             ^             ^
     *
     *         isRightSide == false
     *         _ _ _ _ _     _ _ _ _
     *           ^             ^
     *
     */
    public static int getMaxFoldIndexOfSide(int firstIndex, int lastIndex, boolean isRightSide) {
        int size = lastIndex - firstIndex + 1;
        return firstIndex + ((isRightSide ? size : size - 2) / 2);
    }


}
