import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameDriver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine();
        if(numPlayers>1){
            List<Player> players = new ArrayList<>();
            for (int i = 0; i < numPlayers; i++) {
                System.out.print("Enter name for Player " + (i + 1) + ": ");
                String playerName = scanner.nextLine();
                players.add(new Player(playerName));
            }

            Deck deck = new Deck();
            MyUnoGame unoGame = new MyUnoGame(players, deck);
            unoGame.play();
        }
        else{
            System.out.print("Sorry, the minimum number of Players is 2 .");
        }
        scanner.close();
    }
}
