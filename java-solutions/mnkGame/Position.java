package mnkGame;

public interface Position {
    boolean isValid(Move move);
    Symbol getTurn();
    Cell getCell(int row, int col);
    int getM();
    int getN();
}
