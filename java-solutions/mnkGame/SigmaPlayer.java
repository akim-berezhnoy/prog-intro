package mnkGame;

public class SigmaPlayer extends Player{
    public SigmaPlayer(int id) {
        super(id, true);
    }

    private static int row = 0;
    private static int col = 0;

    @Override
    public Move move(Position position) {
        while (true) {
            if (col == position.getM()) {
                col = 0;
                row++;
            }
            Move move = new Move(row, col, position.getTurn());
            if (position.isValid(move)) {
                return move;
            } else {
                col++;
            }
        }
    }
}
