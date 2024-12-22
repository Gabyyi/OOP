import java.time.LocalDate;
import java.util.*;

final class Event implements Comparable<Event>{
    public enum Type{
        WORKSHOP, SEMINAR, OUTDOOR, INDOOR, VIRTUAL
    }

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final double price;
    private final Type type;
    private final String location;

    public Event(LocalDate startDate, LocalDate endDate, double price, Type type, String location){
        this.startDate=startDate;
        this.endDate=endDate;
        this.price=price;
        this.type=type;
        this.location=location;
    }

    public LocalDate getStartDate(){
        return startDate;
    }
    public LocalDate getEndDate(){
        return endDate;
    }
    public double getPrice(){
        return price;
    }
    public Type getType(){
        return type;
    }
    public String getLocation(){
        return location;
    }
    @Override
    public int compareTo(Event other){
        return Double.compare(this.price, other.price);
    }
    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null || getClass()!=o.getClass()) return false;
        Event event=(Event) o;
        return Double.compare(event.price, price)==0 &&
                startDate.equals(event.startDate) &&
                endDate.equals(event.endDate) &&
                type == event.type &&
                location.equals(event.location);
    }
    @Override
    public int hashCode(){
        return Objects.hash(startDate, endDate, price, type, location);
    }
    @Override
    public String toString(){
        return "Event: "+"startDate="+startDate+", endDate="+endDate+", price="+price+", type="+type+", location='"+location;
    }
    public boolean overlapsWith(Event other){
        return !this.endDate.isBefore(other.startDate) && !other.endDate.isBefore(this.startDate);
    }
}

final class Client{
    private final String name;
    private final String email;
    private final List<Event> purchasedEvents;

    public Client(String name, String email){
        this.name=name;
        this.email=email;
        this.purchasedEvents=new ArrayList<>();
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }

    public boolean addEvent(Event event){
        for(Event e : purchasedEvents){
            if(e.overlapsWith(event)){
                return false;
            }
        }
        return purchasedEvents.add(event);
    }
    public List<Event> getEventsSortedByPrice(){
        List<Event> sortedEvents=new ArrayList<>(purchasedEvents);
        sortedEvents.sort(Comparator.comparingDouble(Event::getPrice));
        return sortedEvents;
    }

    public List<Event> getEventsSortedByType(){
        List<Event> sortedEvents=new ArrayList<>(purchasedEvents);
        sortedEvents.sort(Comparator.comparing(Event::getType));
        return sortedEvents;
    }

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null || getClass()!=o.getClass()) return false;
        Client client=(Client) o;
        return name.equals(client.name) && email.equals(client.email);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, email);
    }
    @Override
    public String toString(){
        return "Client: "+"name="+name+", email="+email;
    }
}

class TeamBuildingOrganizer{
    private final List<Client> clients;
    public TeamBuildingOrganizer(){
        clients=new ArrayList<>();
    }
    public boolean addClient(Client client){
        return clients.add(client);
    }

    public Map<Event.Type, List<Client>> getClientsByEventType(){
        Map<Event.Type, List<Client>> clientsByType=new EnumMap<>(Event.Type.class);

        for(Event.Type type : Event.Type.values()){
            clientsByType.put(type, new ArrayList<>());
        }

        for(Client client : clients){
            for(Event event : client.getEventsSortedByPrice()){
                clientsByType.get(event.getType()).add(client);
            }
        }

        return clientsByType;
    }
}

public class Main {
    public static void main(String[] args) {
        Event event1=new Event(LocalDate.of(2024, 1, 10), LocalDate.of(2024, 1, 12), 300.0, Event.Type.WORKSHOP, "New York");
        Event event2=new Event(LocalDate.of(2024, 2, 15), LocalDate.of(2024, 2, 17), 450.0, Event.Type.SEMINAR, "San Francisco");
        Event event3=new Event(LocalDate.of(2024, 1, 20), LocalDate.of(2024, 1, 22), 200.0, Event.Type.OUTDOOR, "Chicago");
        Event event4=new Event(LocalDate.of(2024, 3, 10), LocalDate.of(2024, 3, 12), 150.0, Event.Type.VIRTUAL, "Online");
        Event event5=new Event(LocalDate.of(2024, 1, 10), LocalDate.of(2024, 1, 12), 300.0, Event.Type.WORKSHOP, "New York");
        Event event6=new Event(LocalDate.of(2024, 1, 10), LocalDate.of(2024, 1, 12), 320.0, Event.Type.WORKSHOP, "Los Angeles");

        Client client1=new Client("Alice", "alice@example.com");
        Client client2=new Client("Bob", "bob@example.com");
        Client client3=new Client("Charlie", "charlie@example.com");

        client1.addEvent(event1);
        client1.addEvent(event2);
        client1.addEvent(event5);
        client2.addEvent(event3);
        client2.addEvent(event4);
        client2.addEvent(event6);
        client3.addEvent(event2);
        
        System.out.println("Client 1 Events (Sorted by Price): " + client1.getEventsSortedByPrice());
        System.out.println("Client 1 Events (Sorted by Type): " + client1.getEventsSortedByType());
        System.out.println("Client 2 Events (Sorted by Price): " + client2.getEventsSortedByPrice());
        System.out.println("Client 2 Events (Sorted by Type): " + client2.getEventsSortedByType());
        System.out.println("Client 3 Events (Sorted by Price): " + client3.getEventsSortedByPrice());
        System.out.println("Client 3 Events (Sorted by Type): " + client3.getEventsSortedByType());

        TeamBuildingOrganizer organizer=new TeamBuildingOrganizer();
        organizer.addClient(client1);
        organizer.addClient(client2);
        organizer.addClient(client3);

        System.out.println("Clients by Event Type:");
        Map<Event.Type, List<Client>> clientsByType=organizer.getClientsByEventType();
        for(Event.Type type : clientsByType.keySet()){
            System.out.println(type+": "+clientsByType.get(type));
        }
    }
}





/*
 A company organizes team building events. Each event has a start date, end date, price, type and location. 
 Clients (name, email) buy events which are added to their purchases. Check when adding events that they do not overlap. 
 Clients can display their purchased events, sorted by their price or by their type.

Also given the collection of clients implement a method that returns

Map<Event.TYPE,List<Client>> - where Event.TYPE is an enumeration representing the types of events. 
This map contains the list of clients for each type of events.

You can use classes from the Collection framework in Java. The grade takes into consideration the proper use of generics, 
immutable classes, and the proper choice of the collection classes.

Implement all classes and copy the implementation below. Make sure to correctly implement (if required) Comparable interface, 
hashCode and equals methods.

 */