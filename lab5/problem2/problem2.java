import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDate;

class Employee {
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

    public String getName(){
        return name;
    }
}

public class problem2{
    public static void main(String[] args){
        ArrayList<Employee> employees=new ArrayList<>();
        employees.add(new Employee("Alice", "1234567890123", LocalDate.of(2020, 1, 1), "Engineer", 50000));
        employees.add(new Employee("Bob", "1234567890124", LocalDate.of(2020, 2, 1), "Engineer", 52000));
        employees.add(new Employee("John", "1234567890125", LocalDate.of(2021, 3, 1), "Manager", 60000));
        employees.add(new Employee("Eve", "1234567890126", LocalDate.of(2022, 4, 1), "Technician", 45000));
        employees.add(new Employee("Frank", "1234567890127", LocalDate.of(2023, 5, 1), "HR", 48000));
        employees.add(new Employee("Grace", "1234567890128", LocalDate.of(2022, 6, 1), "Technician", 47000));
        employees.add(new Employee("Linda", "1234567890129", LocalDate.of(2021, 7, 1), "Manager", 61000));
        employees.add(new Employee("Ivan", "1234567890130", LocalDate.of(2020, 7, 1), "Engineer", 53000));
        employees.add(new Employee("John", "1234567890131", LocalDate.of(2020, 8, 1), "Technician", 46000));
        employees.add(new Employee("Kathy", "1234567890132", LocalDate.of(2021, 9, 1), "HR", 49000));

        Map<String, Employee> employeeMap=new HashMap<>();
        for(Employee emp : employees){
            employeeMap.put(emp.getName(), emp);
        }
        Scanner scanner=new Scanner(System.in);
        System.out.print("Enter the employee name to search: ");
        String name=scanner.nextLine();

        Employee result=employeeMap.get(name);
        if(result!=null){
            System.out.println("Employee found: "+result);
        }else{
            System.out.println("Employee with name "+name+" not found.");
        }
        scanner.close();
    }
}
