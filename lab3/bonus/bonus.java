import java.util.ArrayList;
import java.io.IOException;
import java.io.FileWriter;
interface Sortable{
    void store(Shape[] shapes, String file);
}
abstract class Shape implements Sortable{
    public abstract int computeArea();
    private ArrayList<Shape> shapes;
    public int compareTo(Shape other){
        if(this.computeArea()<other.computeArea()){
            return -1;
        }
        else if(this.computeArea()>other.computeArea()){
            return 1;
        }
        return 0;
    }
    public void sort(){
        this.shapes=new ArrayList<>();
        for(Shape shape : shapes){
            this.shapes.add(shape);
        }
        this.shapes.sort((shape1, shape2) -> {
            return shape1.compareTo(shape2);
        });
    }
    public void display(Shape[] shapes){
        for(Shape shape : shapes){
            System.out.println(shape.computeArea());
        }
    }
    public void store(Shape[] shapes, String file){
        try(FileWriter writer=new FileWriter(file)){
            for(Shape shape : shapes){
                writer.write(shape.toString()+"\n");
            }
        }catch(IOException e){
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
class Square extends Shape{
    private int length;
    public Square(int length){
        this.length=length;
    }
    public int computeArea(){
        return length*length;
    }
    public String getArea(){
        return String.valueOf(computeArea());
    }
    public String toString(){
        return "Square area: "+computeArea();
    }
}
class Circle extends Shape{
    private int radius;
    public Circle(int radius){
        this.radius=radius;
    }
    public int computeArea(){
        return (int)(Math.PI*radius*radius);
    }
    public String getArea(){
        return String.valueOf(computeArea());
    }
    public String toString(){
        return "Circle area: "+computeArea();
    }
}
class Rectangle extends Shape{
    private int length;
    private int width;
    public Rectangle(int length, int width){
        this.length=length;
        this.width=width;
    }
    public int computeArea(){
        return length*width;
    }
    public String getArea(){
        return String.valueOf(computeArea());
    }
    public String toString(){
        return "Rectangle area: "+computeArea();
    }
}
public class bonus{
    public static void main(String[] args){
        Shape[] shapes=new Shape[3];
        shapes[0]=new Square(5);
        shapes[1]=new Circle(3);
        shapes[2]=new Rectangle(4, 6);
        shapes[0].sort();
        shapes[0].display(shapes);
        shapes[0].store(shapes, "shapes.txt");
    }
}