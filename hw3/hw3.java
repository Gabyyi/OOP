import java.util.ArrayList;
import java.util.Collections;
final class PlayingCard implements Comparable<PlayingCard>{
    public enum Rank{
        Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King
    }
    public enum Suit{
        Clubs, Diamonds, Hearts, Spades
    }
    private Rank rank;
    private Suit suit;
    public PlayingCard(Rank rank, Suit suit){
        this.rank=rank;
        this.suit=suit;
    }
    public Rank getRank(){
        return rank;
    }
    public Suit getSuit(){
        return suit;
    }
    @Override
    public int compareTo(PlayingCard other) {
        int suitComparison = this.suit.compareTo(other.suit);
        if (suitComparison != 0) {
            return suitComparison;
        } else {
            return this.rank.compareTo(other.rank);
        }
    }
    public String toString(){
        return "Rank: "+rank+" "+"Suit: "+suit;
    }
}
class Deck{
    private ArrayList<PlayingCard> cards;
    public Deck(){
        this.cards=new ArrayList<PlayingCard>();
    }
    public void addCard(PlayingCard c){
        cards.add(c);
    }
    public PlayingCard removeCard(){
        return cards.remove(0);
    }
    public PlayingCard getCard(int index){
        return cards.get(index);
    }
    public void shuffle(){
        Collections.shuffle(cards);
    }
    public int getNoCards(){
        return cards.size();
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
    public void display(){
        int i=0;
        for (PlayingCard card : cards){
            System.out.println((++i)+" "+card);
        }
    }
}

class hw3{
    public static void main(String[] args){
        int min=Integer.MAX_VALUE;
        int max=Integer.MIN_VALUE;
        for(int k=0;k<10;k++){
            // System.out.println("--------------------");
            Deck deck=new Deck();
            for(PlayingCard.Suit suit : PlayingCard.Suit.values()){
                for(PlayingCard.Rank rank : PlayingCard.Rank.values()){
                    deck.addCard(new PlayingCard(rank, suit));
                }
            }
            deck.sort();
            // deck.display();
            // System.out.println("--------------------");
            deck.shuffle();
            // deck.display();
            // System.out.println("--------------------");

            Deck[] player=new Deck[4];
            for(int i=0;i<4;i++){
                player[i]=new Deck();
            }
            for(int j=0;j<13;j++){
                for(int i=0;i<4;i++){
                    player[i].addCard(deck.removeCard());
                }
            }
            // for(int i=0;i<4;i++){
                // System.out.println("Player "+(i+1)+" cards:");
                // player[i].display();
                // System.out.println("--------------------");
            // }
            int rounds=0;
            // System.out.println(player[0].getNoCards());
            // System.out.println(player[1].getNoCards());
            // System.out.println(player[2].getNoCards());
            // System.out.println(player[3].getNoCards());
            while(player[0].getNoCards()!=0 && player[1].getNoCards()!=0 && player[2].getNoCards()!=0 && player[3].getNoCards()!=0){
                rounds++;
                Deck cardsInPlay=new Deck();
                Deck sortedCardsInPlay=new Deck();
                for(int i=0;i<4;i++){
                    cardsInPlay.addCard(player[i].removeCard());
                    sortedCardsInPlay.addCard(cardsInPlay.getCard(i));
                }
                // cardsInPlay.display();
                // System.out.println("--------------------");
                sortedCardsInPlay.sort();
                // sortedCardsInPlay.display();
                // System.out.println("--------------------");
                for(int i=0;i<4;i++){
                    if(sortedCardsInPlay.getCard(3).compareTo(cardsInPlay.getCard(i))==0){
                        // System.out.println("Player "+(i+1)+" wins the round.");
                        cardsInPlay.sort();
                        for(int j=0;j<4;j++){
                            player[i].addCard(cardsInPlay.removeCard());
                        }
                        break;
                    }
                }
                // for(int i=0;i<4;i++){
                    // System.out.println("Player "+(i+1)+" cards:");
                    // player[i].display();
                    // System.out.println("--------------------");
                // }
                // System.out.println("Rounds played: "+rounds);
                if(rounds>=100){
                    System.out.println("Round limit reached, game ends in a draw.");
                    break;
                }
            }
            System.out.println("Match played: "+(k+1));
            // for(int i=0;i<4;i++){
                // System.out.println("Player "+(i+1)+" cards: " + player[i].getNoCards());
            // }
            for(int i=0;i<4;i++)
                if(player[i].getNoCards()==0) System.out.println("Player "+(i+1)+" lost the game.");
            System.out.println("Rounds played: "+rounds);
            if(rounds<min) min=rounds;
            if(rounds>max) max=rounds;
        }
        System.out.println("Minimum rounds played: "+min);
        System.out.println("Maximum rounds played: "+max);
        System.out.println("Mean rounds played: "+(min+max)/2);
    }
}