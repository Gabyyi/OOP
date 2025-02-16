/*
 Library Management System: Simulate a library where students (threads) borrow and return books. 
 Books have a limited quantity, and students must wait if the required book is not available. 
 Test with multiple students and books. Solve the concurrent access to shared resources 
 using method synchronization, wait and notify.
 */
import java.util.*;
class Book{
    private String title;
    private int quatity;
    public Book(String title, int quatity){
        this.title=title;
        this.quatity=quatity;
    }
    public String getTitle(){
        return title;
    }
    public int getQuantity(){
        return quatity;
    }
    public void setQuantity(int quatity){
        this.quatity=quatity;
    }   
}

class Library{
    private Map<String ,Book> books=new HashMap<>();
    public Library(Map<String, Book> books){
        this.books=books;
    }
    public synchronized void borrowBook(String title) throws InterruptedException{
        while(!books.containsKey(title) || books.get(title).getQuantity()==0){
            System.out.println(Thread.currentThread().getName()+" is waiting for "+title);
            wait();
        }
        Book book=books.get(title);
        book.setQuantity(book.getQuantity()-1);
        System.out.println(Thread.currentThread().getName()+" borrowed "+title);
    }
    public synchronized void returnBook(String title){
        if(books.containsKey(title)){
            Book book=books.get(title);
            book.setQuantity(book.getQuantity()+1);
            System.out.println(Thread.currentThread().getName()+" returned "+title);
            notifyAll();
        }
    }
}

class Student extends Thread{
    private Library library;
    private String bookTitle;
    public Student(Library library, String bookTitle){
        this.library=library;
        this.bookTitle=bookTitle;
    }

    @Override
    public void run(){
        try{
            library.borrowBook(bookTitle);
            Thread.sleep(1000);
            library.returnBook(bookTitle);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}

public class test2{
    public static void main(String args[]){
        Map<String, Book> books=new HashMap<>();
        books.put("book1", new Book("book1", 3));
        books.put("book2", new Book("book2", 2));
        books.put("book3", new Book("book3", 3));
        books.put("book4", new Book("book4", 2));
        books.put("book5", new Book("book5", 2));
        books.put("book6", new Book("book6", 3));
        books.put("book7", new Book("book7", 2));
        books.put("book8", new Book("book8", 3));
        books.put("book9", new Book("book9", 2));
        books.put("book10", new Book("book10", 3));
        Library library=new Library(books);

        while(true){
            for(int i=0;i<10;i++){
                new Student(library, "book"+(i%10+1)).start();
            }
            try{
                Thread.sleep(400);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}