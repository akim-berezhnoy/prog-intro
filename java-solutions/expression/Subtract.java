package expression;

public class Subtract extends BinaryOperation {
    public Subtract(GigachadExpression e1, GigachadExpression e2) {
        super(e1, e2);
    }

    public String getSign() {
        return "-";
    }

    @Override
    public int evaluate(int variable) {
        return getLeftOperand().evaluate(variable) - getRightOperand().evaluate(variable);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return getLeftOperand().evaluate(x, y, z) - getRightOperand().evaluate(x, y, z) ;
    }
}
