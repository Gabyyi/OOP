import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
interface Storable{
    void store(String file);
}
final class Card{
    public enum Rank{
        Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Knight, Queen, King
    }
    public enum Suit{
        Clubs, Diamonds, Hearts, Spades
    }
    private Rank rank;
    private Suit suit;
    public Card(Rank rank, Suit suit){
        this.rank=rank;
        this.suit=suit;
    }
    public Rank getRank(){
        return rank;
    }
    public Suit getSuit(){
        return suit;
    }
    public String toString(){
        return "Rank: "+rank+" "+"Suit: "+suit;
    }
}
class DuplicateCardException extends Exception{
    public DuplicateCardException(String message){
        super(message);
    }
}
class Hand implements Storable{
    private ArrayList<Card> cards;
    public Hand(){
        this.cards=new ArrayList<Card>();
    }
    public void addCard(Card c){
        try{
            if(cards.contains(c)){
                throw new DuplicateCardException("Card "+c+" is already in the hand.");
            }
            else cards.add(c);
        }catch(DuplicateCardException e){
            System.out.println(e.getMessage());
        }
        // if(!cards.contains(c)){
        //     cards.add(c);
        // }
    }
    public void deleteCard(Card.Rank rank, Card.Suit suit){
        cards.removeIf(card -> card.getRank()==rank && card.getSuit()==suit);
    }
    public void sort(){
        cards.sort((card1, card2) -> {
            int suitComparison=card1.getSuit().compareTo(card2.getSuit());
            if(suitComparison!=0){
                return suitComparison;
            }
            else{
                return card1.getRank().compareTo(card2.getRank());
            }
        });
    }
    public void display() {
        for (Card card : cards) {
            System.out.println(card);
        }
    }
    public void store(String file){
        try(FileWriter writer=new FileWriter(file)){
            for(Card card : cards){
                writer.write(card.toString()+"\n");
            }
        }catch(IOException e){
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
public class lab3_ex1{
    public static void main(String[] args){
        Hand hand=new Hand();
        hand.addCard(new Card(Card.Rank.Seven, Card.Suit.Diamonds));
        hand.addCard(new Card(Card.Rank.Seven, Card.Suit.Diamonds));
        hand.addCard(new Card(Card.Rank.Seven, Card.Suit.Spades));
        hand.addCard(new Card(Card.Rank.Ace, Card.Suit.Clubs));
        hand.addCard(new Card(Card.Rank.King, Card.Suit.Diamonds));
        hand.addCard(new Card(Card.Rank.Queen, Card.Suit.Hearts));
        hand.addCard(new Card(Card.Rank.Knight, Card.Suit.Spades));
        hand.store("unsortedcards.txt");
        hand.sort();
        hand.store("sortedcards.txt");
    }
}