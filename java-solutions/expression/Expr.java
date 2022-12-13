package expression;

public interface Expr extends Expression, TripleExpression, ToMiniString {
    int getPriority();
    boolean isLeftAssociative();
    boolean isRightAssociative();
}
