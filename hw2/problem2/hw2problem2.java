import java.util.ArrayList;
import java.util.Collections;

interface Taxable{
    double computeVAT();
    double computeRoadTax();
    double computeCustomsTax();
    double computeTotalTax();
}

abstract class Vehicle implements Comparable<Vehicle>, Taxable{
    protected double basePrice;
    protected String name;
    protected String country;
    public Vehicle(double basePrice, String name, String country){
        this.basePrice=basePrice;
        this.name=name;
        this.country=country;
    }
    public String toString(){
        return "Vehicle: "+name+" from "+country+" with base price: "+basePrice+" and total tax: "+computeTotalTax();
    }
    public double computeVAT(){
        return basePrice*0.19;
    }
    public abstract double computeRoadTax();
    public double computeCustomsTax(){
        if(!country.equals("Romania")){
            return this.basePrice*0.1;
        }
        return 0;
    }
    public double computeTotalTax(){
        return computeVAT()+computeRoadTax()+computeCustomsTax();
    }
    public int compareTo(Vehicle other){
        int taxComparison=Double.compare(this.computeTotalTax(), other.computeTotalTax());
        if(taxComparison==0){
            return Double.compare(other.basePrice, this.basePrice);
        }
        return taxComparison;
    }
}

class Truck extends Vehicle{
    public Truck(double basePrice, String name, String country){
        super(basePrice, name, country);
    }
    public double computeRoadTax(){
        return basePrice*0.05;
    }
}

class Bus extends Vehicle{
    public Bus(double basePrice, String name, String country){
        super(basePrice, name, country);
    }
    public double computeRoadTax(){
        return basePrice*0.04;
    }
}

class Minibus extends Vehicle{
    public Minibus(double basePrice, String name, String country){
        super(basePrice, name, country);
    }
    public double computeRoadTax(){
        return basePrice*0.03;
    }
}

class hw2problem2{
    public static void main(String[] args){
        ArrayList<Vehicle> vehicles=new ArrayList<>();
        vehicles.add(new Truck(70000, "Volvo", "Sweden"));
        vehicles.add(new Bus(50000, "Mercedes", "Germany"));
        vehicles.add(new Minibus(30000, "Ford", "Romania"));
        vehicles.add(new Minibus(20625, "Iveco", "Italy"));
        vehicles.add(new Truck(60000, "Scania", "Sweden"));

        System.out.println("Vehicles before sorting:");
        for(Vehicle vehicle : vehicles){
            System.out.println(vehicle);
        }
        System.out.println();

        Collections.sort(vehicles);
        System.out.println("Vehicles after sorting by Total Tax (ascending) and Price (descending if tax equals):");
        for(Vehicle vehicle : vehicles){
            System.out.println(vehicle);
        }
    }
}