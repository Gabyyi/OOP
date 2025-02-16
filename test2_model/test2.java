/*
 * Simulate a dispatcher for emergency services. People in distress (threads) continuously submit emergencies to the 
   dispatching services. Emergencies receive a timestamp and are put in a waiting queue with a maximum size of 10.  
 * Emergencies contain identification for the caller, location, description and urgency (high, medium, low). 
 * Dispatchers (threads) take emergencies from the waiting queue and process them. High urgency emergencies are always processed first.

 * Implement the required classes and in Main start 10 DistressedPeople. Solve the concurrent access 
   to shared resources using synchronization, wait and notify or explicit locks.
 */

import java.util.concurrent.*;
import java.util.*;
import java.util.concurrent.locks.*;

class Emergency implements Comparable<Emergency> {
    private final String callerId;
    private final String location;
    private final String description;
    private final String urgency;
    private final long timestamp;

    public Emergency(String callerId, String location, String description, String urgency) {
        this.callerId = callerId;
        this.location = location;
        this.description = description;
        this.urgency = urgency;
        this.timestamp = System.currentTimeMillis();
    }

    public String getUrgency() {
        return urgency;
    }

    @Override
    public int compareTo(Emergency other) {
        if (this.urgency.equals(other.urgency)) {
            return Long.compare(this.timestamp, other.timestamp);
        }
        return urgencyPriority(this.urgency) - urgencyPriority(other.urgency);
    }

    private int urgencyPriority(String urgency) {
        switch (urgency) {
            case "high":
                return 1;
            case "medium":
                return 2;
            case "low":
                return 3;
            default:
                return 4;
        }
    }

    @Override
    public String toString() {
        return "Emergency{" +
                "callerId='" + callerId + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", urgency='" + urgency + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

class EmergencyQueue {
    private final PriorityBlockingQueue<Emergency> queue = new PriorityBlockingQueue<>(10);
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public void submitEmergency(Emergency emergency) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == 10) {
                notFull.await();
            }
            queue.put(emergency);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Emergency takeEmergency() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();
            }
            Emergency emergency = queue.take();
            notFull.signal();
            return emergency;
        } finally {
            lock.unlock();
        }
    }
}

class Dispatcher implements Runnable {
    private final EmergencyQueue emergencyQueue;

    public Dispatcher(EmergencyQueue emergencyQueue) {
        this.emergencyQueue = emergencyQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Emergency emergency = emergencyQueue.takeEmergency();
                System.out.println("Processing: " + emergency);
                Thread.sleep(1000); // Simulate time taken to process the emergency
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class DistressedPerson implements Runnable {
    private final EmergencyQueue emergencyQueue;
    private final String callerId;

    public DistressedPerson(EmergencyQueue emergencyQueue, String callerId) {
        this.emergencyQueue = emergencyQueue;
        this.callerId = callerId;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String[] urgencies = {"high", "medium", "low"};
                String urgency = urgencies[new Random().nextInt(urgencies.length)];
                Emergency emergency = new Emergency(callerId, "Location-" + callerId, "Description-" + callerId, urgency);
                emergencyQueue.submitEmergency(emergency);
                System.out.println("Submitted: " + emergency);
                Thread.sleep(new Random().nextInt(5000)); // Simulate time between emergencies
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class test2 {
    public static void main(String[] args) {
        EmergencyQueue emergencyQueue = new EmergencyQueue();

        for (int i = 0; i < 3; i++) {
            new Thread(new Dispatcher(emergencyQueue)).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(new DistressedPerson(emergencyQueue, "Caller-" + i)).start();
        }
    }
}




