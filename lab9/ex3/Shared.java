import java.util.LinkedList;
import java.util.Queue;

public class Shared{
    private Queue<Pizza> queue=new LinkedList<>();
    public static final int MAX_SIZE=5;

    public synchronized void add(Pizza pizza){
        while(queue.size()>=MAX_SIZE){
            try{
                System.out.println("Kitchen full. Cook is waiting...");
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        queue.add(pizza);
        System.out.println("Pizza nr "+pizza.getId()+" was added with "+pizza.getNumberOfIngredients()+" ingredients.");
        notify();
    }

    public synchronized Pizza remove(){
        while(queue.isEmpty()){
            try{
                System.out.println("Kitchen empty. Waiter is waiting...");
                wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        Pizza pizza=queue.remove();
        notify();
        return pizza;
    }
}

class Pizza{
    private final int id;
    private final int numberOfIngredients;

    public Pizza(int id, int numberOfIngredients){
        this.id=id;
        this.numberOfIngredients=numberOfIngredients;
    }

    public int getId(){
        return id;
    }

    public int getNumberOfIngredients(){
        return numberOfIngredients;
    }
}
