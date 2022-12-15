package expression;

public class Clear extends BinaryOperation {
    public Clear(Express leftOperand, Express rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public String getSign() {
        return "clear";
    }

    @Override
    public int getPriority() {
        return priority();
    }

    public static int priority() {
        return 10;
    }

    @Override
    public int makeOperation(int a, int b) {
        return a & ~(1<<b);
    }

    @Override
    public boolean isLeftAssociative() {
        return false;
    }

    @Override
    public boolean isRightAssociative() {
        return false;
    }
}

