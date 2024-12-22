/*
 Simulate a sensor system monitoring a house. Sensor and Dispatcher are Threads. 
 Sensor produces a SensorEvent at random times and adds the event to the EventQueue which is implemented as a queue. 
 The type of event depends on the type of the sensor that produced it. Dispatcher consumes events by removing them from the EventQueue. 
 Consuming an event takes 50 ms and involves outputting on the screen the event information: source (id of the sensor), time and type.


    1. Implement the above classes and in Main instantiate 4 sensors, 1 dispatcher and start all of them.
    2. Use ReentrantLock for synchronization.  
    3. Use Executors for implementing the producers and consumers.
    4. Create a GUI in Swing with a TextArea that displays notifications and a button for each sensor. The button starts or pauses the sensor.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class SensorEvent{
    private final int sensorId;
    private final long timestamp;
    private final String eventType;
    private final Date date;

    public SensorEvent(int sensorId, String eventType){
        this.sensorId=sensorId;
        this.timestamp=System.currentTimeMillis();
        this.eventType=eventType;
        this.date=new Date(this.timestamp);
    }

    public Date getDate(){
        return date;
    }

    public int getSensorId(){
        return sensorId;
    }

    public long getTimestamp(){
        return timestamp;
    }

    public String getEventType(){
        return eventType;
    }

    @Override
    public String toString(){
        return "SensorEvent{"+"sensorID="+(sensorId+1)+", timestamp="+timestamp+", date="+date+", eventType='"+eventType+'\''+'}';
    }
}

class EventQueue{
    private final Queue<SensorEvent> queue=new LinkedList<>();
    private final ReentrantLock lock=new ReentrantLock();

    public void addEvent(SensorEvent event){
        lock.lock();
        try{
            queue.add(event);
        }finally{
            lock.unlock();
        }
    }

    public SensorEvent getEvent(){
        lock.lock();
        try{
            return queue.poll();
        }finally{
            lock.unlock();
        }
    }
}

class Sensor implements Runnable{
    private final int id;
    private final EventQueue eventQueue;
    private final Random random=new Random();
    private volatile boolean running=true;

    public Sensor(int id, EventQueue eventQueue){
        this.id=id;
        this.eventQueue=eventQueue;
    }

    public void run(){
        while(running){
            try{
                Thread.sleep(random.nextInt(1000));
                SensorEvent event=new SensorEvent(id, "EventType" + random.nextInt(5));
                eventQueue.addEvent(event);
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop(){
        running=false;
    }
}

class Dispatcher implements Runnable{
    private final EventQueue eventQueue;
    private final JTextArea textArea;

    public Dispatcher(EventQueue eventQueue, JTextArea textArea){
        this.eventQueue=eventQueue;
        this.textArea=textArea;
    }

    public void run(){
        while(true){
            SensorEvent event=eventQueue.getEvent();
            if(event!=null){
                SwingUtilities.invokeLater(() -> textArea.append(event.toString() + "\n"));
                System.out.println(event);
                try{
                    Thread.sleep(50);
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}

// public class lab10{
//     public static void main(String[] args){
//         EventQueue eventQueue=new EventQueue();
//         ExecutorService executor=Executors.newFixedThreadPool(5);
//         SwingUtilities.invokeLater(() -> new SensorSystemGUI(eventQueue, executor).setVisible(true));
//     }
// }


public class lab10{
    public static void main(String[] args) {
        EventQueue eventQueue=new EventQueue();
        ExecutorService executor=Executors.newFixedThreadPool(5);
        ScheduledExecutorService scheduler=Executors.newScheduledThreadPool(1);

        SensorSystemGUI gui=new SensorSystemGUI(eventQueue, executor);
        SwingUtilities.invokeLater(() -> gui.setVisible(true));

        scheduler.schedule(() -> {
            for(int i=0;i<4;i++){
                if (gui.sensors[i]==null){
                    gui.sensors[i]=new Sensor(i, eventQueue);
                    executor.execute(gui.sensors[i]);
                    gui.sensorButtons[i].setText("Pause Sensor "+(i+1));
                }
            }
        }, 2, TimeUnit.SECONDS);
    }
}
                                                    
                                                    
class SensorSystemGUI extends JFrame{
    private final JTextArea textArea=new JTextArea();
    final JButton[] sensorButtons=new JButton[4];
    final Sensor[] sensors=new Sensor[4];
    private final EventQueue eventQueue;
    private final ExecutorService executor;

    public SensorSystemGUI(EventQueue eventQueue, ExecutorService executor){
        this.eventQueue=eventQueue;
        this.executor=executor;

        setTitle("Sensor System");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel buttonPanel=new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));
        for(int i=0;i<4;i++) {
            final int sensorId=i;
            sensorButtons[i]=new JButton("Sensor "+(i+1));
            sensorButtons[i].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    if(sensors[sensorId]==null){
                        sensors[sensorId]=new Sensor(sensorId, eventQueue);
                        executor.execute(sensors[sensorId]);
                        sensorButtons[sensorId].setText("Pause Sensor "+(sensorId+1));
                    }else{
                        sensors[sensorId].stop();
                        sensors[sensorId]=null;
                        sensorButtons[sensorId].setText("Start Sensor "+(sensorId+1));
                    }
                }
            });
            buttonPanel.add(sensorButtons[i]);
        }
        add(buttonPanel, BorderLayout.SOUTH);

        executor.execute(new Dispatcher(eventQueue, textArea));
    }
}
