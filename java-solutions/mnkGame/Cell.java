package mnkGame;

public class Cell {
    Symbol condition;

    Cell(Symbol condition) {
        this.condition = condition;
    }

    public Symbol getCondition() {
        return condition;
    }

    public void setCondition(Symbol condition) {
        this.condition = condition;
    }

    @Override
    public Cell clone(){
        return new Cell(this.condition);
    }
}
