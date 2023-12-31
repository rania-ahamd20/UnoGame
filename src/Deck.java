import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Deck {
    private List<Card> cards;

    public Deck() {
        initializeDeck();
        shuffle();
    }
    public int getDeckSize() {
        return cards.size();
    }
    public void addCardToDeck(Card card) {
        cards.add(card);
    }
    private void initializeDeck() {
        cards = new ArrayList<>();

        for (CardType type : CardType.values()) {
            if (type != CardType.ACTION && type != CardType.WILD) {
                for (int value = 0; value <= 9; value++) {
                    for (CardColor color : CardColor.values()) {
                        cards.add(new Card(type, value, color));
                        if (value != 0) {
                            cards.add(new Card(type, value, color));
                        }
                    }
                }
            }
        }

        for (CardColor color : CardColor.values()) {
            if (color != null) {
                for (int i = 0; i < 3; i++) {
                    cards.add(new Card(CardType.ACTION, i, color));
                    cards.add(new Card(CardType.ACTION, i, color));
                }
            }
        }


        for (int i = 0; i < 4; i++) {
            cards.add(new Card(CardType.WILD, 0, null));
            cards.add(new Card(CardType.WILD, 1, null));
        }
    }

    public List<Card> getCards() {
        return cards;
    }
    public void removeCard(int index) {
        if (index >= 0 && index < cards.size()) {
            cards.remove(index);
        }
    }

    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        } else {
            return null;
        }
    }
    public void shuffle() {
        Collections.shuffle(cards);
    }
}
