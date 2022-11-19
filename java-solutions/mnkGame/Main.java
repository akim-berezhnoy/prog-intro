package mnkGame;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        setupMnkGame();
    }

    private static void setupMnkGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter m, n and k: ");
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        System.out.print("Enter the number of players: ");
        int playersNumber;
        while ((playersNumber = scanner.nextInt()) > Game.getMAX_PLAYERS_NUMBER()) {
            System.out.printf("Sorry, but maximum number of players is %d.\nPlease, enter correct value: %n",
                    Game.getMAX_PLAYERS_NUMBER());
        }
        System.out.println();
        String[] availablePlayers = new String[]{"HumanPlayer", "RandomPlayer", "SequencePlayer", "SigmaPlayer"};
        System.out.println("Available players: ");
        for (int i = 0; i < availablePlayers.length; i++) {
            System.out.println(i+1 + ") " + availablePlayers[i]);
        }
        System.out.println();
        Symbol[] availableSymbols = new Symbol[]{Symbol.CROSS, Symbol.CIRCLE, Symbol.DASH, Symbol.VERT,
                                                 Symbol.ORANGUTAN, Symbol.DIAMOND, Symbol.HAMBURGER, Symbol.HEART};
        System.out.println("Available symbols: ");
        for (int i = 0; i < availableSymbols.length; i++) {
            System.out.print(i+1 + ") " + availableSymbols[i] + " "+ availableSymbols[i].value + "   ");
            if ((i+1) % 4 == 0) {
                System.out.println();
            }
        }
        System.out.println('\n');
        List<Player> players= new ArrayList<>();
        Deque<Symbol> turnsOrder = new ArrayDeque<>();
        for (int i = 0; i < playersNumber; i++) {
            System.out.printf("Select player %d type: ", i+1);
            int playerNum = scanner.nextInt();
            System.out.printf("Choose your symbol for player %d: ", i+1);
            int symbolNum = scanner.nextInt();
            switch (playerNum) {
                case 1 -> players.add(new HumanPlayer(i+1));
                case 2 -> players.add(new RandomPlayer(i+1));
                case 3 -> players.add(new SequencePlayer(i+1));
                case 4 -> players.add(new SigmaPlayer(i+1));
            }
            turnsOrder.addLast(availableSymbols[symbolNum-1]);
        }
        Board board = new mnkBoard(m,n,k,turnsOrder);
        new Game(players, false).play(board);
    }
}
