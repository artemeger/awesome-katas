package org.example;

import java.util.Arrays;

public class KarateChopImpl {

    public int binarySearch(final int target, final int[] range) {
        return recursiveBinarySearch(target, range, 0);
    }

    private int recursiveBinarySearch(int target, int[] range, int count) {
        if (range.length == 0) return -1;
        final boolean hasOneElement = range.length == 1;
        final boolean isFirstElement = range[0] == target;
        if (hasOneElement && isFirstElement) return count;
        if (hasOneElement) return -1;
        var midIndex = range.length / 2;
        var first = Arrays.copyOfRange(range, 0, midIndex);
        int maxIndexOfFirst = first.length - 1;
        var elmToCheck = first[maxIndexOfFirst];
        if (elmToCheck == target) return maxIndexOfFirst + count;
        var second = Arrays.copyOfRange(range, midIndex, range.length);
        if (target <= elmToCheck) return recursiveBinarySearch(target, first, count);
        else return recursiveBinarySearch(target, second, count + midIndex);
    }
}