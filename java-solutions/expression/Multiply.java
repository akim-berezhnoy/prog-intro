package expression;

public class Multiply extends BinaryOperation {
    public Multiply(GigachadExpression e1, GigachadExpression e2) {
        super(e1, e2);
    }

    public String getSign() {
        return "*";
    }

    @Override
    public int makeOperation(int a, int b) {
        return a * b;
    }

    @Override
    public double makeOperation(double a, double b) {
        return a * b;
    }
}