package mnkGame;

import java.io.PrintStream;
import java.util.Scanner;

public class HumanPlayer extends Player{
    private final Scanner in;
    private final PrintStream out;

    public HumanPlayer(int id, final Scanner in, final PrintStream out) {
        super(id, false);
        this.in = in;
        this.out = out;
    }

    public HumanPlayer(int id) {
        this(id, new Scanner(System.in), System.out);
    }

    @Override
    public Move move(final Position position) {
        out.println("Position: ");
        out.println(position);
        while (true) {
            try {
                out.print("Enter row and column: " );
                int row = in.nextInt() - 1;
                int col = in.nextInt() - 1;
                final Move move = new Move(row, col, position.getTurn());
                if (position.isValid(move)) {
                    return move;
                }
                out.println("Your move " + move + " is forbidden! Try to make only correct moves: ");
            } catch (java.util.InputMismatchException e) {
                in.reset();
                out.println("""
                        You are not following the format of making move. Please try again.
                        You must insert row and column as integers. Also split them with at least one whitespace:
                        For example:
                        1 1
                        3 2
                        4 1
                        """);
                in.next();
                in.next();
            }
        }
    }
}
