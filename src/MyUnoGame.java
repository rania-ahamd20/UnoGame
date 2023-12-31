import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MyUnoGame extends Game {
    private int currentPlayerIndex;
    private int currentDirection;
    private Card topCard;
    private Deck deck;

    public MyUnoGame(List<Player> players, Deck deck) {
        super(players, deck);
        this.deck = deck;
        currentPlayerIndex = 0;
        currentDirection = 1;
    }

    @Override
    public void start() {
        deck.shuffle();
        dealCardsToPlayers();
        topCard = deck.drawCard();
        System.out.println("------------------------------------------------------------------------");
        System.out.println("topCard: "+ topCard.getType() +" "+ topCard.getValue()+" "+topCard.getColor());
        System.out.println("------------------------------------------------------------------------");
    }

    private void dealCardsToPlayers() {
        Random random = new Random();
        deck.shuffle();

        for (Player player : players) {
            List<Card> playerHand = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                int randomIndex = random.nextInt(deck.getDeckSize());
                Card card = deck.getCards().get(randomIndex);
                playerHand.add(card);
                deck.removeCard(randomIndex);
            }
            player.setHand(playerHand);
            System.out.println("-----------------------------");
            player.showHand();
            System.out.println("-----------------------------");
        }
    }
   @Override
   public void playTurn(Player player) {
       Card playedCard = player.playCard(getValidCardIndex(player));
       if (playedCard != null) {
           if (isValidMove(playedCard)) {
               System.out.println("********************");
               System.out.println("player: " + player.getName());
               System.out.println("playedCard: " + playedCard.getType() + " " + playedCard.getValue() + " " + playedCard.getColor());
               System.out.println("********************");
               topCard = playedCard;
               handleSpecialCards(playedCard, player);
           } else {
               Card drawnCard = deck.drawCard();
               player.drawCard(drawnCard);
           }
       } else {
           Card drawnCard = deck.drawCard();
           player.drawCard(drawnCard);
       }
       if (player.hasUno()) {
           System.out.println(player.getName() + " has UNO!");
       }
       currentPlayerIndex = getNextPlayerIndex();
       System.out.println("-----------------------------");
       player.showHand();
       System.out.println("-----------------------------");

   }


    private int getValidCardIndex(Player player) {
        List<Card> hand = player.getHand();
        for (int i = 0; i < hand.size(); i++) {
            if (isValidMove(hand.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private boolean isValidMove(Card card) {
        CardType topCardType = topCard.getType();
        int topCardValue = topCard.getValue();
        CardColor topCardColor = topCard.getColor();

        CardType cardType = card.getType();
        int cardValue = card.getValue();
        CardColor cardColor = card.getColor();

         if (cardType == topCardType || cardColor == topCardColor) {

            if (cardType == CardType.ACTION && cardColor == topCardColor) {
                return true;
            }

            if (cardType == CardType.NUMBER && (cardColor == topCardColor || cardValue == topCardValue)) {
                return true;
            }
        }else if (cardType == CardType.WILD) {
            return true;
        }
        return false;
    }


    private void handleSpecialCards(Card card , Player player) {
        if (card.getType() == CardType.ACTION) {
            handleActionCard(card);
        } else if (card.getType() == CardType.WILD) {
            handleWildCard(card,player);
        }
    }

    private void handleActionCard(Card card) {
        int cardValue = card.getValue();

        // Reverse action
        if (cardValue == 0) {
            currentDirection *= -1;
            System.out.println("Reversed direction of play.");
        }
        // Skip action
        else if (cardValue == 1) {
            currentPlayerIndex += currentDirection;
            System.out.println("Skipped the next player's turn.");
        }
        // Draw 2 action
        else if (cardValue == 2) {
            Player nextPlayer = getNextPlayer();
            if (nextPlayer != null) {
                drawCardsForPlayer(nextPlayer, 2);
                System.out.println("Next player drew 2 cards.");
            }
        }
    }

    private Player getNextPlayer() {
        int nextIndex = currentPlayerIndex + currentDirection;
        if (nextIndex >= players.size()) {
            return players.get(0);
        } else if (nextIndex < 0) {
            return players.get(players.size() - 1);
        } else {
            return players.get(nextIndex);
        }
    }
    private void drawCardsForPlayer(Player player, int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            Card drawnCard = deck.drawCard();
            if (drawnCard != null) {
                player.drawCard(drawnCard);
            } else {
                System.out.println("No more cards in the deck!");
                break;
            }
        }
    }


    private void handleWildCard(Card card, Player currentPlayer) {
        int cardValue = card.getValue();
        // Wild
        if (cardValue == 0) {
            CardColor mostCommonColor = getMostCommonColorInHand(currentPlayer);
            if (mostCommonColor != null) {
                topCard.setColor(mostCommonColor);
                System.out.println("Changed top card color to: " + mostCommonColor);
            }
        }
        // Wild add 4
        else if (cardValue == 1) {
            CardColor mostCommonColor = getMostCommonColorInHand(currentPlayer);
            if (mostCommonColor != null) {
                topCard.setColor(mostCommonColor);
                System.out.println("Changed top card color to: " + mostCommonColor);
                drawCardsForPlayer(getNextPlayer(), 4);
            }
        }
    }

    private CardColor getMostCommonColorInHand(Player player) {
        List<Card> hand = player.getHand();
        Map<CardColor, Integer> colorFrequency = new HashMap<>();

        for (Card card : hand) {
            CardColor color = card.getColor();
            colorFrequency.put(color, colorFrequency.getOrDefault(color, 0) + 1);
        }

        CardColor mostCommonColor = null;
        int maxFrequency = 0;

        for (Map.Entry<CardColor, Integer> entry : colorFrequency.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                mostCommonColor = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }

        return mostCommonColor;
    }

    private int getNextPlayerIndex() {
        int nextIndex = currentPlayerIndex + currentDirection;
        if (nextIndex >= players.size()) {
            return 0;
        } else if (nextIndex < 0) {
            return players.size() - 1;
        } else {
            return nextIndex;
        }
    }
    @Override
    public boolean isGameOver() {
        for (Player player : players) {
            if (player.getHand().isEmpty()) {
                System.out.println("Game Over");
                return true;
            }
        }
        return false;
    }

}
