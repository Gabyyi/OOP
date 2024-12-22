import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
 
public class ex4_singlethread{
 
    public static void main(String[] args){
        Map<String, Integer> wordCount=new HashMap<>();
 
        try{
            for(int i = 0; i < 100; i++){
                Path filePath=Paths.get("file"+i+".txt");
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

        wordCount.forEach((word, count) -> System.out.println(word + " -> " + count));
 
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))){
            wordCount.forEach((word, count) -> {
            try{
                writer.write(word + " -> " + count);
                writer.newLine();
            }catch (IOException e){
                e.printStackTrace();
            }
            });
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
