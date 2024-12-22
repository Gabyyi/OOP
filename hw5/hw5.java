import java.util.*;

class Attraction{
    String name;
    String location;
    public Attraction(String name, String location){
        this.name=name;
        this.location=location;
    }
    @Override
    public int hashCode(){
        return Objects.hash(name, location);
    }
    @Override
    public boolean equals(Object obj){
        if(this==obj) return true;
        if(obj==null || getClass()!=obj.getClass()) return false;
        Attraction atr=(Attraction) obj;
        return Objects.equals(name, atr.name) && Objects.equals(location, atr.location);
    }
    public String getName(){
        return name;
    }
    public String getLocation(){
        return location;
    }
    @Override
    public String toString(){
        return name+" "+location;
    }
}

class TouristicPackage{
    String name;
    int id;
    double price;
    List<Attraction> attractions;
    public TouristicPackage(String name, int id, double price, List<Attraction> attractions){
        this.name=name;
        this.id=id;
        this.price=price;
        this.attractions=attractions;
    }
    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }
    public double getPrice(){
        return price;
    }
    public String toString(){
        return name+" "+id+" "+price+" "+attractions;
    }
}

public class hw5{
    public static void main(String[] args){
        Attraction a1=new Attraction("Paltinis", "Romania");
        Attraction a2=new Attraction("Bran Castle", "Romania");
        Attraction a3=new Attraction("Eiffel Tower", "France");
        Attraction a4=new Attraction("Colosseum", "Italy");
        Attraction a5=new Attraction("Sagrada Familia", "Spain");
        Attraction a6=new Attraction("Louvre Museum", "France");
        Attraction a7=new Attraction("Big Ben", "UK");
        Attraction a8=new Attraction("Acropolis", "Greece");
        Attraction a9=new Attraction("Mount Fuji", "Japan");
        Attraction a10=new Attraction("Statue of Liberty", "USA");

        List<TouristicPackage> packages=new ArrayList<>();
        packages.add(new TouristicPackage("Romania Explorer", 1, 300.0, Arrays.asList(a1, a2)));
        packages.add(new TouristicPackage("Paris Highlights", 2, 500.0, Arrays.asList(a3, a6)));
        packages.add(new TouristicPackage("Ancient Wonders", 3, 400.0, Arrays.asList(a4, a8)));
        packages.add(new TouristicPackage("Europe Favorites", 4, 450.0, Arrays.asList(a2, a3, a7)));
        packages.add(new TouristicPackage("Asian Adventure", 5, 600.0, Arrays.asList(a9, a10)));
        packages.add(new TouristicPackage("Romanian Castles", 6, 320.0, Arrays.asList(a2, a1)));
        packages.add(new TouristicPackage("Historical Europe", 7, 520.0, Arrays.asList(a4, a7, a6)));
        packages.add(new TouristicPackage("Greek Odyssey", 8, 480.0, Arrays.asList(a8, a7)));
        packages.add(new TouristicPackage("New York Essentials", 9, 550.0, Arrays.asList(a10, a6)));
        packages.add(new TouristicPackage("Transylvania Tour", 10, 340.0, Arrays.asList(a1, a2, a5)));

        Map<Attraction, Integer> attractionCountMap=new HashMap<>();
        for(TouristicPackage tourPackage : packages){
            for(Attraction attraction : tourPackage.attractions){
                if(!attractionCountMap.containsKey(attraction)){
                    attractionCountMap.put(attraction, 1);
                }else{
                    attractionCountMap.put(attraction, attractionCountMap.get(attraction)+1);
                }
            }
        }

        int totalAttractionCount=0;
        for(TouristicPackage tourPackage : packages){
            totalAttractionCount+=tourPackage.attractions.size();
        }

        Map<Attraction, Double> attractionPopularityMap=new HashMap<>();
        for(Map.Entry<Attraction, Integer> entry : attractionCountMap.entrySet()){
            Attraction attraction=entry.getKey();
            int count=entry.getValue();
            double relativePopularity=(double)count/totalAttractionCount;
            attractionPopularityMap.put(attraction, relativePopularity);
        }
        
        List<Map.Entry<Attraction, Double>> sortedAttractions=new ArrayList<>();
        for(Map.Entry<Attraction, Double> entry : attractionPopularityMap.entrySet()){
            sortedAttractions.add(entry);
        }
        for(int i=0;i<sortedAttractions.size();i++){
            for(int j=0;j<sortedAttractions.size()-i-1;j++){
                if(sortedAttractions.get(j).getValue()<sortedAttractions.get(j+1).getValue()){
                    Map.Entry<Attraction, Double> temp=sortedAttractions.get(j);
                    sortedAttractions.set(j,sortedAttractions.get(j+1));
                    sortedAttractions.set(j+1,temp);
                }
            }
        }

        System.out.println("Attraction popularity statistics:");
        for(Map.Entry<Attraction, Double> entry : sortedAttractions){
            Attraction attraction=entry.getKey();
            int count=attractionCountMap.get(attraction);
            double relativePopularity=entry.getValue();
            System.out.println(attraction+" was bought "+count+" times with a popularity of "+String.format("%.2f", relativePopularity));
        }
    }
}
