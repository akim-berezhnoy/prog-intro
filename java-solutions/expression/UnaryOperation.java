package expression;

public abstract class UnaryOperation implements Expr {

    private final Expr operand;

    public UnaryOperation(Expr operand) {
        this.operand = operand;
    }

    public abstract String getSign();
    public abstract int getPriority();

    public abstract int makeOperation(int a);

    public abstract double makeOperation(double a);

    @Override
    public int evaluate(int x) {
        return makeOperation(operand.evaluate(x));
    }

    @Override
    public double evaluate(double x) {
        return makeOperation(operand.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return makeOperation(operand.evaluate(x,y,z));
    }

    @Override
    public int hashCode() {
        return (((this.getClass().hashCode()) +
                operand.hashCode()) * 7);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final UnaryOperation that) {
            return this.operand.equals(that.operand) &&
                    that.getClass().equals(this.getClass());
        }
        return false;
    }


    @Override
    public String toString() {
        return getSign() + "(" + operand + ")";
    }

    @Override
    public String toMiniString() {
        if (operand.getPriority() > getPriority()) {
            return getSign() + "(" + operand.toMiniString() + ")";
        } else {
            return getSign() + " " + operand.toMiniString();
        }
    }
}
