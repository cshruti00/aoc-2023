package aoc.day4;

public class Card {
     int cardNumber;
     int countOfWinningCards;


    public Card(int cardNumber, int countOfWinningCards) {
        this.cardNumber = cardNumber;
        this.countOfWinningCards = countOfWinningCards;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber=" + cardNumber +
                ", countOfWinningCards=" + countOfWinningCards +
                '}';
    }
}
