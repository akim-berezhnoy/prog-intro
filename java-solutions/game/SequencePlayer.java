package game;

public class SequencePlayer extends Player{

    public SequencePlayer(int id) {
        super(id, true);
    }

    @Override
    public Move move(Position position) {
        for (int row = 0; row < position.getM(); row++) {
            for (int col = 0; col < position.getN(); col++) {
                final Move move = new Move(
                    row,
                    col,
                    position.getTurn()
                );
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new AssertionError("No valid moves");
    }
}
