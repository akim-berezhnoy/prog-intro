package expression;

public class Const implements GigachadExpression {
    private final double value;
    private final String stringValue;

    public Const(int value) {
        this.value = value;
        stringValue = String.valueOf(value);
    }

    public Const(double value) {
        this.value = value;
        stringValue = String.valueOf(value);
    }

    @Override
    public int evaluate(int x) {
        return (int)value;
    }

    @Override
    public double evaluate(double x) {
        return value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (int)value;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final Const that) {
            return that.value == this.value;
        }
        return false;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
