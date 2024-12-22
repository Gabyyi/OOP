import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class ex4_multipleThreads{

    public static void main(String[] args){
        int numThreads=10;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Map<String, Integer>>> futures=new ArrayList<>();

        for (int i=0;i<numThreads;i++){
            int start=i*(100/numThreads);
            int end=(i+1)*(100/numThreads);
            futures.add(executor.submit(new WordCountTask(start, end)));
        }

        Map<String, Integer> aggregateWordCount=new HashMap<>();
        for(Future<Map<String, Integer>> future : futures){
            try{
                Map<String, Integer> wordCount=future.get();
                for (Map.Entry<String, Integer> entry : wordCount.entrySet()){
                    aggregateWordCount.put(entry.getKey(), aggregateWordCount.getOrDefault(entry.getKey(), 0) + entry.getValue());
                }
            }catch (InterruptedException | ExecutionException e){
                e.printStackTrace();
            }
        }

        executor.shutdown();

        aggregateWordCount.forEach((word, count) -> System.out.println(word + " -> " + count));
    }
}

class WordCountTask implements Callable<Map<String, Integer>>{
    private int start;
    private int end;

    public WordCountTask(int start, int end){
        this.start=start;
        this.end=end;
    }

    @Override
    public Map<String, Integer> call(){
        Map<String, Integer> wordCount=new HashMap<>();

        try{
            for(int i=start;i<end;i++){
                Path filePath=Paths.get("file" + i + ".txt");
                List<String> lines=Files.readAllLines(filePath);

                for(String line : lines){
                    String[] words=line.split("\\W+");
                    for(String word : words){
                        if(!word.isEmpty()){
                            word=word.toLowerCase();
                            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                        }
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return wordCount;
    }
}