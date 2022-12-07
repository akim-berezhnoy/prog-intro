package game;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        setupGame();
    }

    private static int scanInt(Scanner in, String offer) {
        System.out.print(offer);
        while (true) {
            try {
                return in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error. Please enter correct integer values. (Example: 3)\n" + offer);
                in.next();
            }
        }
    }

    private static void setupGame() {
        Scanner in = new Scanner(System.in);
        int boardType = scanInt(in, "Choose your board(1 - mnk, 2 - gex): ");
        int m = 0, n = 0, k = 0;
        switch (boardType) {
            case 1 -> {
                m = scanInt(in, "Enter m: ");
                n = scanInt(in, "Enter n: ");
                k = scanInt(in, "Enter k: ");
            }
            case 2 -> {
                m = scanInt(in, "Enter length: ");
                k = scanInt(in, "Enter k: ");
            }
        }
        int playersNumber;
        while ((playersNumber = scanInt(in, "Enter the number of players: ")) > Game.getMAX_PLAYERS_NUMBER()) {
            System.out.printf("Max number of players is %d.\n",
                Game.getMAX_PLAYERS_NUMBER());
        }
        System.out.println();
        String[] availablePlayers = new String[]{"HumanPlayer", "RandomPlayer", "SequencePlayer", "SigmaPlayer"};
        System.out.println("Available players: ");
        for (int i = 0; i < availablePlayers.length; i++) {
            System.out.println(i + 1 + ") " + availablePlayers[i]);
        }
        System.out.println();
        Symbol[] availableSymbols = new Symbol[]{Symbol.CROSS, Symbol.CIRCLE, Symbol.DASH, Symbol.VERT,
                Symbol.ORANGUTAN, Symbol.DIAMOND, Symbol.HAMBURGER, Symbol.HEART};
        System.out.println("Available symbols: ");
        for (int i = 0; i < availableSymbols.length; i++) {
            System.out.print(i + 1 + ") " + availableSymbols[i] + " " + availableSymbols[i].value + "   ");
            if ((i + 1) % 4 == 0) {
                System.out.println();
            }
        }
        System.out.println('\n');
        List<Player> players = new ArrayList<>();
        Deque<Symbol> turnsOrder = new ArrayDeque<>();
        for (int i = 0; i < playersNumber; i++) {
            int playerNum = scanInt(in, "Select player %d type: ".formatted(i + 1));
            int symbolNum = scanInt(in, "Choose your symbol for player %d: ".formatted(i + 1));
            switch (playerNum) {
                case 1 -> players.add(new HumanPlayer(i + 1));
                case 2 -> players.add(new RandomPlayer(i + 1));
                case 3 -> players.add(new SequencePlayer(i + 1));
                case 4 -> players.add(new SigmaPlayer(i + 1));
            }
            turnsOrder.addLast(availableSymbols[symbolNum - 1]);
        }
        int roundsNumber = scanInt(in , "Enter the number or rounds to win: ");
        int[] rounds = new int[playersNumber+1];
        for (int round = 1; ; round++) {
            System.out.println("Round " + round);
            Board board = switch (boardType) {
                case 1 -> new mnkBoard(m, n, k, turnsOrder);
                case 2 -> new gexBoard(m, k, turnsOrder);
                default -> null;
            };
            int winner = new Game(players, false).play(board);
            rounds[winner]++;
            for (int player = 1; player < playersNumber+1; player++) {
                if (rounds[player] >= roundsNumber) {
                    System.out.println("\nABSOLUTE CHAMPION: Player " + player);
                    System.exit(0);
                }
            }
            turnsOrder.addLast(turnsOrder.removeFirst());
        }
    }
}
