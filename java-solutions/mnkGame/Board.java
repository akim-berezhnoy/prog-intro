package mnkGame;

public interface Board {
    Position getPosition();
    Result makeMove(Move move);
    void removeLastTurn();
}
