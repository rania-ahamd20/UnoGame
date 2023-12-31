public class Card {
    private CardType type;
    private int value;
    private CardColor color;
    public Card(CardType type, int value,CardColor color) {
        this.type = type;
        this.value = value;
        this.color=color;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }
}
