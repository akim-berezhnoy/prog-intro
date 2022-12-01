package expression;

import java.util.Objects;

public class Variable implements GigachadExpression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public int evaluate(int variable) {
        return variable;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (name) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> 0;
        };
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final Variable that) {
            return Objects.equals(that.name, this.name);
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
