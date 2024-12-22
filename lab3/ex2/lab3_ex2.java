import java.util.ArrayList;
import java.io.IOException;
import java.io.FileWriter;

interface Storable{
    void store(String file);
}
final class Course{
    private String name;
    public enum Type{
        Foundamental, Specialization, Discipline
    }
    public enum Stream{
        English, French, German
    }
    private Type type;
    private Stream stream;
    private int creditPoints;
    public Course(String name, Type type, Stream stream, int creditPoints){
        this.name=name;
        this.type=type;
        this.stream=stream;
        this.creditPoints=creditPoints;
    }
    public String getName(){
        return name;
    }
    public Type getType(){
        return type;
    }
    public Stream getStream(){
        return stream;
    }
    public int getCreditPoints(){
        return creditPoints;
    }
    public String toString(){
        return "Name: "+name+" "+"Type: "+type+" "+"Stream: "+stream+" "+"CreditPoints: "+creditPoints;
    }
}
class DuplicateCourseException extends Exception{
    public DuplicateCourseException(String message){
        super(message);
    }
}
class Contract implements Storable{
    private ArrayList<Course> courses;
    public Contract(){
        this.courses=new ArrayList<>();
    }
    public void addCourse(Course c){
        try{
            if(courses.contains(c)){
                throw new DuplicateCourseException("Course "+c+" is already chosen.");
            }
            else courses.add(c);
        }catch (DuplicateCourseException e){
            System.out.println(e.getMessage());
        }
    }
    public void deleteCourse(Course.Type type, Course.Stream stream, String name) {
        courses.removeIf(course -> course.getType()==type && course.getStream()==stream && course.getName().equals(name));
    }
    public void sort() throws DuplicateCourseException{
        courses.sort((course1, course2) -> {
            int streamComparison=course1.getStream().compareTo(course2.getStream());
            if(streamComparison!=0) return streamComparison;

            int typeComparison=course1.getType().compareTo(course2.getType());
            if(typeComparison!=0) return typeComparison;

            int nameComparison=course1.getName().compareTo(course2.getName());
            try{
                if(nameComparison==0) throw new DuplicateCourseException("Duplicate course found: " + course1.getName());
                // courses.deleteCourse(course1.getType(), course1.getStream(), course1.getName());
                return nameComparison;
            }catch(DuplicateCourseException e){
                System.out.println(e.getMessage());
                return 0;
            }
        });
    }
    public void display(){
        for(Course course : courses){
            System.out.println("Course Name: "+course.getName()+", Type: "+course.getType()+", Stream: "+course.getStream()+", Credit Points: "+course.getCreditPoints());
        }
    }
    public void store(String file){
        try(FileWriter writer=new FileWriter(file)){
            for(Course course : courses){
                writer.write(course.toString()+"\n");
            }
        }catch(IOException e){
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}

public class lab3_ex2 {
    public static void main(String[] args){
        Course c1=new Course("Math", Course.Type.Foundamental, Course.Stream.English, 5);
        Course c2=new Course("Math", Course.Type.Foundamental, Course.Stream.English, 5);
        Course c3=new Course("Physics", Course.Type.Specialization, Course.Stream.French, 6);
        Course c4=new Course("Physics", Course.Type.Specialization, Course.Stream.French, 6);
        Contract contract=new Contract();
        contract.addCourse(c1);
        contract.addCourse(c2);
        contract.addCourse(c3);
        contract.addCourse(c4);
        contract.store("unsortedcontracts.txt");
        try{
            contract.sort();
        }catch(DuplicateCourseException e){
            System.out.println(e.getMessage());
        }
        contract.store("sortedcontracts.txt");
    }
}