import java.util.List;

public abstract class Game {
    protected List<Player> players;
    protected Deck deck;

    public Game(List<Player> players, Deck deck) {
        this.players = players;
        this.deck = deck;
    }

    public abstract void start();

    public abstract boolean isGameOver();

    public abstract void playTurn(Player player);

    public void play() {
        start();
        while (!isGameOver()) {
            for (Player player : players) {
                playTurn(player);
                if (player.getHand().isEmpty()) {
                    declareWinner(player);
                    return;
                }
            }
        }
        handleEndGame();
    }

    protected void declareWinner(Player player) {
        System.out.println("Player " + player.getName() + " has won the game!");
    }

    protected void handleEndGame() {
        Player winner = determineWinner();
        if (winner != null) {
            declareWinner(winner);
        } else {
            System.out.println("The game ended in a draw!");
        }
    }

    protected Player determineWinner() {
        Player potentialWinner = null;
        int minCards = Integer.MAX_VALUE;

        for (Player player : players) {
            int numCards = player.getHand().size();
            if (numCards < minCards) {
                minCards = numCards;
                potentialWinner = player;
            } else if (numCards == minCards) {
                potentialWinner = null;
            }
        }
        return potentialWinner;
    }
}
