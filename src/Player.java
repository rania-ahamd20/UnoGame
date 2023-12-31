import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getHand() {
        return hand;
    }
    public void setHand(List<Card> newHand) {
        this.hand = newHand;
    }

    public void drawCard(Card card) {
        hand.add(card);
    }

    public Card playCard(int index) {
        if (index >= 0 && index < hand.size()) {
            return hand.remove(index);
        } else {
            return null;
        }
    }

    public boolean hasUno() {
        return hand.size() == 1;
    }

    public void showHand() {
        if (hand == null || hand.isEmpty()) {
            System.out.println(name + " has no cards.");
            return;
        }

        System.out.println(name + "'s hand:");
        for (Card card : hand) {
            String valueString;
            if (card.getType() == CardType.ACTION) {
                if (card.getValue() == 0) {
                    valueString = "Reverse";
                } else if (card.getValue() == 1) {
                    valueString = "Skip";
                } else if (card.getValue() == 2) {
                    valueString = "Draw+2";
                } else {
                    valueString = "Unknown Action";
                }
            } else if (card.getType() == CardType.WILD) {
                if (card.getValue() == 0) {
                    valueString = "Wild";
                } else if (card.getValue() == 1) {
                    valueString = "Wild+4";
                } else {
                    valueString = "Unknown Wild";
                }
            } else if (card.getType() == CardType.NUMBER) {
                valueString = String.valueOf(card.getValue());
            } else {
                valueString = "Unknown Type";
            }

            String colorString = (card.getColor() != null) ? card.getColor().toString() : "Colorless";
            System.out.println(card.getType() + " " + valueString + " " + colorString);

        }
    }

}
