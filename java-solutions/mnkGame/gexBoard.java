package mnkGame;

import java.util.*;

public class gexBoard implements Board, Position {

    final private int m;
    final private int k;

    private int blanks;

    private final Cell[][] field;
    private final Deque<Symbol> turns;

    public gexBoard(int m, int k, Deque<Symbol> turns) {
        this.m = m;
        this.k = k;
        this.blanks = m*m;
        this.field = new Cell[m][m];
        for (Cell[] row : field) {
            for (int col = 0; col < m; col++) {
                row[col] = new Cell(Symbol.BlANK);
            }
        }
        this.turns = new ArrayDeque<>(turns);
    }

    @Override
    public int getM() {
        return m;
    }

    @Override
    public int getN() {
        return m;
    }

    public Cell getCell(int row, int col) {
        return field[row][col].clone();
    }
    public Symbol getTurn() {
        return turns.getFirst();
    }

    @Override
    public void removeLastTurn() {
        turns.removeFirst();
    }


    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public Result makeMove(Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        field[move.getRow()][move.getCol()].setCondition(move.getSymbol());
        if (isWinningMove(move)) {
            return Result.WIN;
        }
        this.blanks--;
        if (blanks == 0) {
            return Result.DRAW;
        }
        turns.addLast(turns.removeFirst());
        return Result.UNKNOWN;
    }

    private boolean isWinningMove(Move lastMove) {
        int row = lastMove.getRow();
        int col = lastMove.getCol();
        int streak = 0;
        while (row < m-1 && field[row+1][col].getCondition() == lastMove.getSymbol()) {
            row++;
        }
        while (row >= 0 && field[row][col].getCondition() == lastMove.getSymbol()) {
            row--;
            streak++;
        }
        if (streak >= k) {
            return true;
        }
        //
        row = lastMove.getRow();
        col = lastMove.getCol();
        streak = 0;
        while (col < m-1 && field[row][col+1].getCondition() == lastMove.getSymbol()) {
            col++;
        }
        while (col >= 0 && field[row][col].getCondition() == lastMove.getSymbol()) {
            col--;
            streak++;
        }
        if (streak >= k) {
            return true;
        }
        //
        row = lastMove.getRow();
        col = lastMove.getCol();
        streak = 0;
        while (row < m-1 && col > 0 && field[row+1][col-1].getCondition() == lastMove.getSymbol()) {
            row++;
            col--;
        }
        while (row >= 0 && col <= m-1 && field[row][col].getCondition() == lastMove.getSymbol()) {
            row--;
            col++;
            streak++;
        }
        return streak >= k;
    }

    public boolean isValid(Move move) {
        return
                0 <= move.getRow() && move.getRow() < m &&
                        0 <= move.getCol() && move.getCol() < m &&
                        field[move.getRow()][move.getCol()].getCondition() == Symbol.BlANK &&
                        move.getSymbol() == turns.getFirst();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("  ");
        for (int i = 0; i < m; i++) {
            if (i < 10) {
                sb.append(" ");
            }
            sb.append(i+1);
        }
        sb.append("\n  ");
        sb.append("__".repeat(Math.max(0, m)));
        sb.append("__");
        sb.append('\n');
        for (int row = 0; row < m; row++) {
            sb.append(" ".repeat(row));
            if (row < 9) {
                sb.append(" ");
            }
            sb.append(row+1).append('\\').append(' ');
            for (int col = 0; col < m; col++) {
                sb.append(field[row][col].getCondition().value);
                if (field[row][col].getCondition().value.length() == 1) {
                    sb.append(" ");
                }
            }
            sb.append("\\ \n");
        }
        sb.append(" ".repeat(Math.max(0, m)));
        sb.append("  ");
        sb.append("‾‾".repeat(Math.max(0, m)));
        sb.append("‾‾");
        return sb.toString();
    }
}
