package game;

import java.util.Random;

public class RandomPlayer extends Player{

    private final Random random = new Random();

    public RandomPlayer(int id) {
        super(id, true);
    }

    @Override
    public Move move(Position position) {
        while (true) {
            final Move move = new Move(
                    random.nextInt(position.getM()),
                    random.nextInt(position.getN()),
                    position.getTurn()
            );
            if (position.isValid(move)) {
                return move;
            }
        }
    }
}
