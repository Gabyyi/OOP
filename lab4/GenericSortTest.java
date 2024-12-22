package lab4;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GenericSortTest {
    @Test
    void testIntegerSort() {
        Integer[] sorted = { 2, 5, 1, 7, 8, 10 };
        Integer[] in = Arrays.copyOf(sorted, sorted.length);
        Arrays.sort(sorted);
        GenericSort.sort(in);
        assertArrayEquals(sorted, in);
    }

    @Test
    void testStringSort() {
        String[] sorted = { "a", "d", "c", "z", "x", "e" };
        String[] in = Arrays.copyOf(sorted, sorted.length);
        Arrays.sort(sorted);
        GenericSort.sort(in);
        assertArrayEquals(sorted, in);
    }

    @Test
    void testIntegerSortWithDuplicates() {
        Integer[] sorted = { 2, 1, 1, 7, 8, 10 };
        Integer[] in = Arrays.copyOf(sorted, sorted.length);
        Arrays.sort(sorted);
        GenericSort.sort(in);
        assertArrayEquals(sorted, in);
    }

    @Test
    void testEmptyArray() {
        Integer[] sorted = {};
        Integer[] in = {};
        GenericSort.sort(in);
        assertArrayEquals(sorted, in);
    }
}