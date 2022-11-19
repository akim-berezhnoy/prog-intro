package mnkGame;

public class Move {
    private final int row;
    private final int col;
    private final Symbol symbol;

    public Move(int row, int col, Symbol symbol) {
        this.row = row;
        this.col = col;
        this.symbol = symbol;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "(%d, %d; %s)".formatted(row+1, col+1, symbol.value);
    }
}
