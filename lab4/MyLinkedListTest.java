package lab4;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {
   @Test
   void testRemoveFromEmptyList() {
       MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();
       assertThrows(NoSuchElementException.class, () -> myLinkedList.remove());
   }

   @Test
   void addingIntegers() {
       MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();
       myLinkedList.add(1);
       myLinkedList.add(2);
       myLinkedList.add(3);
       assertEquals(1, myLinkedList.get(0));
       assertEquals(2, myLinkedList.get(1));
       assertEquals(3, myLinkedList.get(2));
   }

   @Test
   void getOutOfBounds() {
       MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();
       myLinkedList.add(1);
       myLinkedList.add(2);
       myLinkedList.add(3);
       assertThrows(IndexOutOfBoundsException.class, () -> myLinkedList.get(4));
   }
}