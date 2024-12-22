package lab4;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeTest{
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testEmpty() {
        @SuppressWarnings("unused")
        BinaryTree<Integer> bst = new BinaryTree<>();
        assertEquals("", outContent.toString());
    }

    @Test
    void testBSTInteger() {
        BinaryTree<Integer> bst = new BinaryTree<>();
        bst.add(1);
        bst.add(3);
        bst.add(2);
        bst.add(5);
        bst.inOrder();
        assertEquals("1 2 3 5", outContent.toString().trim());
    }

    @Test
    void testBSTString() {
        BinaryTree<String> bst = new BinaryTree<>();
        bst.add("a");
        bst.add("d");
        bst.add("b");
        bst.add("c");
        bst.inOrder();
        assertEquals("a b c d", outContent.toString().trim());
    }

    @Test
    void testBSTDuplicates() {
        BinaryTree<Integer> bst = new BinaryTree<>();
        bst.add(1);
        bst.add(2);
        bst.add(1);
        bst.add(2);
        bst.inOrder();
        assertEquals("1 2", outContent.toString().trim());
    }

}