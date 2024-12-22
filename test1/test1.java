import java.util.*;
import java.time.LocalDate;
import java.util.ArrayList;

class City{
    private String name;
    private List<Temperature> temperatures;
    public City(String name){
        this.name=name;
        this.temperatures=new ArrayList<>();
    }
    public void addTemperature(Temperature temperature){
        temperatures.add(temperature);
    }
    public String getName(){
        return name;
    }
    public List<Temperature> getTemperatures(){
        return temperatures;
    }
    public String toString(){
        return name;
    }
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null || getClass()!=o.getClass()) return false;
        City city=(City) o;
        return Objects.equals(o, city);
    }
    public int hashCode(){
        return Objects.hash(name);
    }
}
class Temperature{
    private double value;
    private LocalDate timestamp;
    public Temperature(double value, LocalDate timestamp){
        this.value=value;
        this.timestamp=timestamp;
    }
    public double getValue(){
        return value;
    }
    public LocalDate getTimestamp(){
        return timestamp;
    }
    public String toString(){
        return value+" "+timestamp;
    }
}
class Weather{
    private List<City> cities;
    public Weather(){
        this.cities=new ArrayList<>();
    }
    public void addCity(City city){
        cities.add(city);
    }
    public Map<City, Double> averageTemperature(){
        Map<City, Double> result=new HashMap<>();
        for(City city : cities){
            double sum=0;
            for(Temperature temperature : city.getTemperatures()){
                sum+=temperature.getValue();
            }
            result.put(city, sum/city.getTemperatures().size());
        }
        return result;
    }
    public Map<City, List<Double>> allTemperatures(){
        Map<City, List<Double>> result=new HashMap<>();
        for(City city : cities){
            List<Double> temperatures=new ArrayList<>();
            for(Temperature temperature : city.getTemperatures()){
                temperatures.add(temperature.getValue());
            }
            temperatures.sort(Comparator.naturalOrder());
            result.put(city, temperatures);
        }
        return result;
    }
}
public class test1{
    public static void main(String[] args){
        City city1=new City("Valcea");
        city1.addTemperature(new Temperature(20, LocalDate.of(2024,8,20)));
        city1.addTemperature(new Temperature(25,LocalDate.of(2024,8,23)));
        city1.addTemperature(new Temperature(22,LocalDate.of(2024,9,3)));
        city1.addTemperature(new Temperature(21,LocalDate.of(2024,9,5)));
        city1.addTemperature(new Temperature(23,LocalDate.of(2024,10,6)));
        City city2=new City("Bucharest");
        city2.addTemperature(new Temperature(32, LocalDate.of(2024,8,20)));
        city2.addTemperature(new Temperature(28,LocalDate.of(2024,8,23)));
        city2.addTemperature(new Temperature(33,LocalDate.of(2024,9,3)));
        city2.addTemperature(new Temperature(27,LocalDate.of(2024,9,5)));
        city2.addTemperature(new Temperature(25,LocalDate.of(2024,10,6)));
        City city3=new City("Bucharest");
        city3.addTemperature(new Temperature(35, LocalDate.of(2024,8,20)));
        city3.addTemperature(new Temperature(30,LocalDate.of(2024,8,23)));
        city3.addTemperature(new Temperature(38,LocalDate.of(2024,9,3)));
        city3.addTemperature(new Temperature(33,LocalDate.of(2024,9,5)));
        city3.addTemperature(new Temperature(28,LocalDate.of(2024,10,6)));
        City city4=new City("Constanta");
        city4.addTemperature(new Temperature(29, LocalDate.of(2024,8,20)));
        city4.addTemperature(new Temperature(30,LocalDate.of(2024,8,23)));
        city4.addTemperature(new Temperature(27,LocalDate.of(2024,9,3)));
        city4.addTemperature(new Temperature(28,LocalDate.of(2024,9,5)));
        city4.addTemperature(new Temperature(23,LocalDate.of(2024,10,6)));
        City city5=new City("Cluj");
        city5.addTemperature(new Temperature(25, LocalDate.of(2024,8,20)));
        city5.addTemperature(new Temperature(22,LocalDate.of(2024,8,23)));
        city5.addTemperature(new Temperature(20,LocalDate.of(2024,9,3)));
        city5.addTemperature(new Temperature(21,LocalDate.of(2024,9,5)));
        city5.addTemperature(new Temperature(19,LocalDate.of(2024,10,6)));

        Weather weather=new Weather();
        weather.addCity(city1);
        weather.addCity(city2);
        weather.addCity(city3);
        weather.addCity(city4);
        weather.addCity(city5);
        weather.averageTemperature().forEach((city, average)->System.out.println(city+" "+average));
        weather.allTemperatures().forEach((city, temperatures)->System.out.println(city+" "+temperatures));
    }
}

