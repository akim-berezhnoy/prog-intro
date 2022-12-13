package expression;

public interface Expr extends Expression, TripleExpression, DoubleExpression {
    int getPriority();
    boolean isLeftAssociative();
    boolean isRightAssociative();
}
