import java.util.*;
import java.time.LocalDate;

class Employee implements Comparable<Employee>{
    private String name;
    private String CNP;
    private LocalDate hiringDate;
    private String specialization;
    private int salary;

    public Employee(String name, String CNP, LocalDate hiringDate, String specialization, int salary){
        this.name=name;
        this.CNP=CNP;
        this.hiringDate=hiringDate;
        this.specialization=specialization;
        this.salary=salary;
    }

    public String toString(){
        return name+" "+CNP+" "+hiringDate+" "+specialization+" "+salary;
    }
    @Override
    public int hashCode(){
        return Objects.hash(name, CNP, hiringDate, specialization, salary);
    }
    @Override
    public boolean equals(Object o){
    if(this==o) return true;
    if(o==null || getClass()!=o.getClass()) return false;
    Employee employee=(Employee) o;
    return Double.compare(employee.salary, salary)==0 &&
           Objects.equals(name, employee.name) &&
           Objects.equals(CNP, employee.CNP) &&
           Objects.equals(hiringDate, employee.hiringDate) &&
           Objects.equals(specialization, employee.specialization);
    }
    @Override
    public int compareTo(Employee other){
        return this.name.compareTo(other.name);
    }
}

public class problem1{
    public static void main(String[] args){
        ArrayList<Employee> employees=new ArrayList<>();
        employees.add(new Employee("Alice", "1234567890123", LocalDate.of(2020, 1, 1), "Engineer", 50000));
        employees.add(new Employee("Alice", "1234567890123", LocalDate.of(2020, 1, 1), "Engineer", 50000)); // Duplicate
        employees.add(new Employee("Bob", "1234567890124", LocalDate.of(2020, 2, 1), "Engineer", 52000));
        employees.add(new Employee("Alice", "1234567890123", LocalDate.of(2020, 1, 1), "Engineer", 50000)); // Duplicate
        employees.add(new Employee("David", "1234567890125", LocalDate.of(2021, 3, 1), "Manager", 60000));
        employees.add(new Employee("Eve", "1234567890126", LocalDate.of(2022, 4, 1), "Technician", 45000));
        employees.add(new Employee("Frank", "1234567890127", LocalDate.of(2023, 5, 1), "HR", 48000));
        employees.add(new Employee("Alice", "1234567890123", LocalDate.of(2020, 1, 1), "Engineer", 50000)); // Duplicate
        employees.add(new Employee("Grace", "1234567890128", LocalDate.of(2022, 6, 1), "Technician", 47000));
        employees.add(new Employee("Heidi", "1234567890129", LocalDate.of(2021, 7, 1), "Manager", 61000));
        employees.add(new Employee("Ivan", "1234567890130", LocalDate.of(2020, 7, 1), "Engineer", 53000));
        
        System.out.println("List of Employees (using Iterator):");
        Iterator<Employee> iterator=employees.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
        
        ListIterator<Employee> listIterator=employees.listIterator(employees.size()/2);
        listIterator.add(new Employee("John", "1234567890131", LocalDate.of(2020, 8, 1), "Technician", 46000));

        System.out.println("\nUpdated List of Employees (after inserting in the middle):");
        for(Employee emp : employees){
            System.out.println(emp);
        }

        Set<Employee> empSet=new HashSet<>(employees);
        System.out.println("\nEmployees in HashSet:");
        Iterator<Employee> setIterator=empSet.iterator();
        while(setIterator.hasNext()){
            System.out.println(setIterator.next());
        }

        Set<Employee> empTreeSet=new TreeSet<>(employees);
        System.out.println("\nEmployees in TreeSet (sorted by name):");
        for (Employee emp : empTreeSet){
            System.out.println(emp);
        }
    }
}
