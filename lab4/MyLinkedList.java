package lab4;

import java.util.Collection;
import java.util.ListIterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unused")
public class MyLinkedList<T>{
   private Node<T> first;
    private int size=0;

    public void add(T val){
        Node<T> newNode=new Node<>(val);
        if(first==null){
            first=newNode;
        }else{
            Node<T> current=first;
            while(current.next!=null){
                current=current.next;
            }
            current.next=newNode;
        }
        size++;
    }

    public void add(T val, int index){
        if(index<0 || index>size){
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> newNode=new Node<>(val);
        if(index==0){
            newNode.next=first;
            first=newNode;
        }else{
            Node<T> prev=getNode(index-1);
            newNode.next=prev.next;
            prev.next=newNode;
        }
        size++;
    }

    public T get(int index){
        if(index<0 || index>=size){
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return getNode(index).value;
    }

    public T remove(){
        if(first==null){
            throw new NoSuchElementException("List is empty");
        }
        T value=first.value;
        first=first.next;
        size--;
        return value;
    }

    public T remove(int index){
        if(index<0 || index>=size){
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if(index==0){
            return remove();
        }else{
            Node<T> prev=getNode(index-1);
            T value=prev.next.value;
            prev.next=prev.next.next;
            size--;
            return value;
        }
    }

    private Node<T> getNode(int index){
        Node<T> current=first;
        for(int i=0;i<index;i++){
            current=current.next;
        }
        return current;
    }

    private static class Node<T>{
        Node<T> next;
        T value;

        public Node(T value){
            this.value=value;
        }
    }
}
