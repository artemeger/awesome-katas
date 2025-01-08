import org.example.KarateChopImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KarateChopImplTest {

    private final KarateChopImpl underTest = new KarateChopImpl();

    @Test
    public void testBinarySearch() {
        assertEquals(-1, underTest.binarySearch(3, new int[]{}));
        assertEquals(-1, underTest.binarySearch(3, new int[]{1}));
        assertEquals(0, underTest.binarySearch(1, new int[]{1}));
        assertEquals(0, underTest.binarySearch(1, new int[]{1, 3, 5}));
        assertEquals(1, underTest.binarySearch(3, new int[]{1, 3, 5}));
        assertEquals(2, underTest.binarySearch(5, new int[]{1, 3, 5}));
        assertEquals(-1, underTest.binarySearch(0, new int[]{1, 3, 5}));
        assertEquals(-1, underTest.binarySearch(2, new int[]{1, 3, 5}));
        assertEquals(-1, underTest.binarySearch(4, new int[]{1, 3, 5}));
        assertEquals(-1, underTest.binarySearch(6, new int[]{1, 3, 5}));
        assertEquals(0, underTest.binarySearch(1, new int[]{1, 3, 5, 7}));
        assertEquals(1, underTest.binarySearch(3, new int[]{1, 3, 5, 7}));
        assertEquals(2, underTest.binarySearch(5, new int[]{1, 3, 5, 7}));
        assertEquals(3, underTest.binarySearch(7, new int[]{1, 3, 5, 7}));
        assertEquals(-1, underTest.binarySearch(0, new int[]{1, 3, 5, 7}));
        assertEquals(-1, underTest.binarySearch(2, new int[]{1, 3, 5, 7}));
        assertEquals(-1, underTest.binarySearch(4, new int[]{1, 3, 5, 7}));
        assertEquals(-1, underTest.binarySearch(6, new int[]{1, 3, 5, 7}));
        assertEquals(-1, underTest.binarySearch(8, new int[]{1, 3, 5, 7}));
    }
}
