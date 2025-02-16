//Airport Security Checkpoint Simulation: Simulate an airport security
// process where passengers (threads) go through a series
// of checkpoints. Implement queues for each checkpoint and ensure passengers are
// processed in the order they arrive. Test with a high volume of passengers and limited security lanes.
// Each passenger should output on the screen whenever she passes a checkpoint.
//
//Solve the concurrent access to shared resources using explicit locks.


import java.security.Security;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class PassengerThread extends Thread{
    private final SecurityCheckpoint checkpoint;
    private final int id;
    public PassengerThread(SecurityCheckpoint checkpoint, int id){
        this.checkpoint=checkpoint;
        this.id=id;
    }
    @Override
    public void run(){
        try{
            checkpoint.processPassenger(new Passenger(id, checkpoint));
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
class Passenger implements Runnable {
    private final int id;
    private final SecurityCheckpoint checkpoint;

    public Passenger(int id, SecurityCheckpoint checkpoint) {
        this.id = id;
        this.checkpoint = checkpoint;
    }

    @Override
    public void run() {
        try {
            checkpoint.processPassenger(this);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getId() {
        return id;
    }
}
class SecurityCheckpoint {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final Queue<Passenger> queue = new LinkedList<>();
    private final int lanes;
    private int availableLanes;
    private final String name;
    private final int maxPassengers;
    private int currentPassengers;

    public SecurityCheckpoint(String name, int lanes, int maxPassengers) {
        this.name = name;
        this.lanes = lanes;
        this.availableLanes = lanes;
        this.maxPassengers = maxPassengers;
        this.currentPassengers = 0;
    }

    public void processPassenger(Passenger passenger) throws InterruptedException {
        lock.lock();
        try {
            if (currentPassengers >= maxPassengers) {
                System.out.println("Checkpoint " + name + " has reached its limit of " + maxPassengers + " passengers.");
                return;
            }
            queue.add(passenger);
            System.out.println("Passenger " + passenger.getId() + " is waiting to be processed at checkpoint " + name + ".");
            while (queue.peek() != passenger || availableLanes <= 0) {
                if (availableLanes <= 0) {
                    System.out.println("All lanes are full at checkpoint " + name + ". Passenger " + passenger.getId() + " is waiting.");
                }
                condition.await();
            }
            queue.poll();
            availableLanes--;
            currentPassengers++;
            System.out.println("Passenger " + passenger.getId() + " is being processed at checkpoint " + name + ".");
            Thread.sleep(100); // Simulate time taken to process passenger
            availableLanes++;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
public class test2 {
    public static void main(String[] args) {
        int numberOfPassengers = 1;
        SecurityCheckpoint checkpoint1 = new SecurityCheckpoint("Checkpoint 1", 5, 10);
        SecurityCheckpoint checkpoint2 = new SecurityCheckpoint("Checkpoint 2", 5, 10);
        SecurityCheckpoint checkpoint3 = new SecurityCheckpoint("Checkpoint 3", 5, 10);
        SecurityCheckpoint checkpoint4 = new SecurityCheckpoint("Checkpoint 4", 5, 10);
        SecurityCheckpoint checkpoint5 = new SecurityCheckpoint("Checkpoint 5", 5, 10);

        while (true) {
            new Thread(new Passenger(numberOfPassengers++, checkpoint1)).start();
            new Thread(new Passenger(numberOfPassengers++, checkpoint2)).start();
            new Thread(new Passenger(numberOfPassengers++, checkpoint3)).start();
            new Thread(new Passenger(numberOfPassengers++, checkpoint4)).start();
            new Thread(new Passenger(numberOfPassengers++, checkpoint5)).start();

            try {
                Thread.sleep(1500); // Adjust the sleep time as needed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}