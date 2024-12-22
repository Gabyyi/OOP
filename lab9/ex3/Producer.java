import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer extends Thread {
    private static final AtomicInteger idCounter=new AtomicInteger(0);
    private final Shared kitchen;
    private final String name;
    private static final Random random=new Random();

    public Producer(String name, Shared kitchen){
        this.name=name;
        this.kitchen=kitchen;
    }

    @Override
    public void run(){
        while(true){
            int id=idCounter.incrementAndGet();
            int ingredients=3+random.nextInt(5);
            Pizza pizza=new Pizza(id, ingredients);
            kitchen.add(pizza);
            try{
                Thread.sleep(5+ingredients*2);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
