import java.io.FileWriter;
import java.util.ArrayList;
class Bank{
    @SuppressWarnings("unused")
    private String name;
    private ArrayList<Loan> loans=new ArrayList<Loan>();
    public Bank(String name){
        this.name = name;
    }
    public void addLoan(Loan l){
        loans.add(l);
    }
    public boolean remove(Loan l){
        for(int i=0;i<loans.size();i++){
            if(loans.get(i).getId()==l.getId()){
                loans.remove(i);
                return true;
            }
        }
        return false;
    }
    public Loan[] find(String name){
        ArrayList<Loan> found = new ArrayList<Loan>();
        for(int i=0;i<loans.size();i++){
            if(loans.get(i).getPerson().getName().equals(name)){
                found.add(loans.get(i));
            }
        }
        Loan[] foundArray = new Loan[found.size()];
        for(int i=0;i<found.size();i++){
            foundArray[i] = found.get(i);
        }
        return foundArray;
    }
    public Loan[] find(String name, double minAmount){
        ArrayList<Loan> found = new ArrayList<Loan>();
        for(int i=0;i<loans.size();i++){
            if(loans.get(i).getPerson().getName().equals(name) && loans.get(i).getAmount()>=minAmount){
                found.add(loans.get(i));
            }
        }
        Loan[] foundArray = new Loan[found.size()];
        for(int i=0;i<found.size();i++){
            foundArray[i] = found.get(i);
        }
        return foundArray;
    }
    public void printlnFile(String filename){
        try{
            FileWriter writer = new FileWriter(filename);
            for(int i=0;i<loans.size();i++){
                writer.write("Loan ID: " + loans.get(i).getId() + " Amount: " + loans.get(i).getAmount() + " Person: " + loans.get(i).getPerson().getName() + " " + loans.get(i).getPerson().getSurname() + " " + loans.get(i).getPerson().getCNP() + "\n");
            }
            writer.close();
        }catch(Exception e){
            System.out.println("Error writing to file");
        }
    }
}
class Loan{
    private int id;
    private double amount;
    private Person person;
    public Loan(int id, double amount, Person person){
        this.id = id;
        this.amount = amount;
        this.person = person;
    }
    public int getId(){
        return id;
    }
    public double getAmount(){
        return amount;
    }
    public Person getPerson(){
        return person;
    }
    public void increaseAmount(double added){
        amount += added;
    }
}
class Person{
    private String name;
    private String surname;
    private String CNP;

    public Person(String name, String surname, String CNP){
        this.name = name;
        this.surname = surname;
        this.CNP = CNP;
    }
    public String getName(){
        return name;
    }
    public String getSurname(){
        return surname;
    }
    public String getCNP(){
        return CNP;
    }
    public String toString(){
        return ("Name: " + name + " Surname: " + surname + " CNP: " + CNP);
    }
}
class hw1{
    public static void main(String[] args){
        Bank b = new Bank("MyBank");
        Person p1 = new Person("John", "Marston", "12345");
        Person p2 = new Person("Arthur", "Morgan", "12346");
        Person p3 = new Person("Bill", "Williamson", "12347");
        Person p4 = new Person("Sadie", "Adler", "12348");
        Person p5 = new Person("John", "Smith", "12349");
        Loan l1 = new Loan(1, 1000, p1);
        Loan l2 = new Loan(2, 2000, p2);
        Loan l3 = new Loan(3, 1500, p3);
        Loan l4 = new Loan(4, 2500, p4);
        Loan l5 = new Loan(5, 3000, p5);
        b.addLoan(l1);
        b.addLoan(l2);
        b.addLoan(l3);
        b.addLoan(l4);
        b.addLoan(l5);
        b.remove(l2);
        Loan[] found = b.find("John", 2000);
        for(int i=0;i<found.length;i++){
            System.out.println("Loan ID: " + found[i].getId() + " Amount: " + found[i].getAmount() + " Person: " + found[i].getPerson().getName() + " " + found[i].getPerson().getSurname() + " " + found[i].getPerson().getCNP());
        }
        b.printlnFile("loans.txt");
    }
}