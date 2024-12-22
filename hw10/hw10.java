/*
 Simulate the visa processing for an embassy. Candidates (threads) can submit visa requests to a waiting queue with a maximum size of 10. 
 The visa request contains the name, age and education level of the candidate. 
 Embassy workers (threads) take requests from the waiting queue and process them. 
 If the education level is > 3 then a visa is created with the details of the candidate and stored in a file called visa_candidateName 
 (one file per visa). If not then the name of the candidate is added to a file called rejected_candidates ( a single file in which all threads save). 
 A candidate can submit multiple visa requests.

Implement the required classes and in Main start 100 Candidates. 
Solve the concurrent access to shared resources using objects lock, wait and notify.
 */


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class VisaRequest{
    private String name;
    private int age;
    private int educationLevel;

    public VisaRequest(String name, int age, int educationLevel){
        this.name=name;
        this.age=age;
        this.educationLevel=educationLevel;
    }

    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }

    public int getEducationLevel(){
        return educationLevel;
    }
}

class Candidate implements Runnable{
    private String name;
    private int age;
    private int educationLevel;
    private BlockingQueue<VisaRequest> queue;

    public Candidate(String name, int age, int educationLevel, BlockingQueue<VisaRequest> queue){
        this.name=name;
        this.age=age;
        this.educationLevel=educationLevel;
        this.queue=queue;
    }

    @Override
    public void run(){
        try{
            while(true){
                VisaRequest request=new VisaRequest(name, age, educationLevel);
                queue.put(request);
                Thread.sleep(100);
            }
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}

class EmbassyWorker implements Runnable{
    private BlockingQueue<VisaRequest> queue;
    private final Object lock=new Object();

    public EmbassyWorker(BlockingQueue<VisaRequest> queue){
        this.queue=queue;
    }

    @Override
    public void run(){
        try{
            while(true){
                VisaRequest request=queue.take();
                processRequest(request);
            }
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    private void processRequest(VisaRequest request){
        synchronized (lock){
            try{
                if(request.getEducationLevel() > 3){
                    try(FileWriter writer=new FileWriter("visa_" + request.getName() + ".txt")){
                        writer.write("Name: " + request.getName() + "\n");
                        writer.write("Age: " + request.getAge() + "\n");
                        writer.write("Education Level: " + request.getEducationLevel() + "\n");
                    }
                }else{
                    try (FileWriter writer=new FileWriter("rejected_candidates.txt", true)){
                        writer.write(request.getName() + "\n");
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}

public class hw10{
    public static void main(String[] args){
        clearOutputFiles();

        BlockingQueue<VisaRequest> queue=new ArrayBlockingQueue<>(10);
        Random random = new Random();

        for(int i=0;i<100;i++){
            int age=18+random.nextInt(53);
            int educationLevel=1+random.nextInt(5);
            new Thread(new Candidate("Candidate"+i,age,educationLevel,queue)).start();
        }

        for(int i=0;i<10;i++){
            new Thread(new EmbassyWorker(queue)).start();
        }
    }

    private static void clearOutputFiles(){
        for(int i=0;i<100;i++){
            File file=new File("visa_Candidate" + i + ".txt");
            if(file.exists()){
                file.delete();
            }
        }

        File rejectedFile=new File("rejected_candidates.txt");
        if(rejectedFile.exists()){
            rejectedFile.delete();
        }
    }   
}