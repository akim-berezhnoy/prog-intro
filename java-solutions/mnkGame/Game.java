package mnkGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private final List<Player> players;
    private final Set<Player> losers = new HashSet<>();
    static final private int MAX_PLAYERS_NUMBER = 8;

    private final boolean log;

    public Game(List<Player> players, boolean log) {
        this.players = new ArrayList<>(players);
        this.log = log;
    }

    public static int getMAX_PLAYERS_NUMBER() {
        return MAX_PLAYERS_NUMBER;
    }

    public int play(final Board board) {
        while (true) {
            for (Player player : players) {
                if (!losers.contains(player)) {
                    loop: while (true) {
                        final Outcome outcome = makeMove(board, player);
                        final Result result = outcome.getResult();
                        switch (result) {
                            case LOSE -> {
                                System.out.printf("What a pity. Player #%d's last move was forbidden and he lefts our game :( %n", player.getId());
                                losers.add(outcome.getCauser());
                                board.removeLastTurn();
                                return -1;
                            }
                            case WIN -> {
                                System.out.println(board);
                                System.out.printf("Winner winner chicken dinner!\nCongrats, Player #%d!%n".formatted(outcome.getCauser().getId()));
                                return outcome.getCauser().getId();
                            }
                            case DRAW -> {
                                System.out.println(board);
                                System.out.printf("Draw. Everybody won and everybody lost!%n");
                                return 0;
                            }
                            case EXTRA -> {
                                System.out.println("GOOD! Make extra move.\n");
                            }
                            case UNKNOWN -> {
                                break loop;
                            }
                        }
                    }
                }
            }
        }
    }

    private Outcome makeMove(Board board, Player player) {
        Move move = player.move(board.getPosition());
        Result result = board.makeMove(move);
        if (result == Result.WIN) {
            System.out.printf("LAST WINNER'S move is: %s%n", move.toString());
        }
        if (log) {
            System.out.printf("PLayer #%d, %s", player.getId(), move.toString());
            System.out.println(board);
        }
        return new Outcome(result, player);
    }
}
