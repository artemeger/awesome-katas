package org.example;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class KarateChopImplTest {

    private final KarateChopImpl underTest = new KarateChopImpl();

    @Test
    public void testBinarySearch() {
        assertThat(-1, equalTo(underTest.binarySearch(3, new int[]{})));
        assertThat(-1, equalTo(underTest.binarySearch(3, new int[]{1})));
        assertThat(0, equalTo(underTest.binarySearch(1, new int[]{1})));
        assertThat(0, equalTo(underTest.binarySearch(1, new int[]{1, 3, 5})));
        assertThat(1, equalTo(underTest.binarySearch(3, new int[]{1, 3, 5})));
        assertThat(2, equalTo(underTest.binarySearch(5, new int[]{1, 3, 5})));
        assertThat(-1, equalTo(underTest.binarySearch(0, new int[]{1, 3, 5})));
        assertThat(-1, equalTo(underTest.binarySearch(2, new int[]{1, 3, 5})));
        assertThat(-1, equalTo(underTest.binarySearch(4, new int[]{1, 3, 5})));
        assertThat(-1, equalTo(underTest.binarySearch(6, new int[]{1, 3, 5})));
        assertThat(0, equalTo(underTest.binarySearch(1, new int[]{1, 3, 5, 7})));
        assertThat(1, equalTo(underTest.binarySearch(3, new int[]{1, 3, 5, 7})));
        assertThat(2, equalTo(underTest.binarySearch(5, new int[]{1, 3, 5, 7})));
        assertThat(3, equalTo(underTest.binarySearch(7, new int[]{1, 3, 5, 7})));
        assertThat(-1, equalTo(underTest.binarySearch(0, new int[]{1, 3, 5, 7})));
        assertThat(-1, equalTo(underTest.binarySearch(2, new int[]{1, 3, 5, 7})));
        assertThat(-1, equalTo(underTest.binarySearch(4, new int[]{1, 3, 5, 7})));
        assertThat(-1, equalTo(underTest.binarySearch(6, new int[]{1, 3, 5, 7})));
        assertThat(-1, equalTo(underTest.binarySearch(8, new int[]{1, 3, 5, 7})));
    }
}
