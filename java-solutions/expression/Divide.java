package expression;

public class Divide extends BinaryOperation {
    public Divide(Expr e1, Expr e2) {
        super(e1, e2);
    }

    @Override
    public String getSign() {
        return "/";
    }

    public static int priority() {
        return 3;
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public boolean isLeftAssociative() {
        return false;
    }

    @Override
    public boolean isRightAssociative() {
        return false;
    }

    @Override
    public int makeOperation(int a, int b) {
        return a / b;
    }

    @Override
    public double makeOperation(double a, double b) {
        return a / b;
    }
}
