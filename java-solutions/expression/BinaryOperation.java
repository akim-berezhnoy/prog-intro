package expression;

import java.util.HashMap;

public abstract class BinaryOperation implements GigachadExpression {

    private static final HashMap<String, Integer> priority = new HashMap<>();

    static {
        priority.put(Add.class.getName(), 2);
        priority.put(Subtract.class.getName(), 2);
        priority.put(Divide.class.getName(), 1);
        priority.put(Multiply.class.getName(), 1);
        priority.put(Variable.class.getName(), 0);
        priority.put(Const.class.getName(), 0);
    }

    private final GigachadExpression leftOperand;
    private final GigachadExpression rightOperand;

    public BinaryOperation(GigachadExpression leftOperand, GigachadExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public abstract String getSign();

    public abstract int makeOperation(int a, int b);

    public abstract double makeOperation(double a, double b);

    @Override
    public int evaluate(int x) {
        return makeOperation(leftOperand.evaluate(x), rightOperand.evaluate(x));
    }

    @Override
    public double evaluate(double x) {
        return makeOperation(leftOperand.evaluate(x), rightOperand.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return makeOperation(leftOperand.evaluate(x,y,z), rightOperand.evaluate(x,y,z));
    }

    @Override
    public int hashCode() {
        return (((this.getClass().hashCode()) +
                leftOperand.hashCode()) * 7) +
                rightOperand.hashCode() * 17;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final BinaryOperation that) {
            return this.leftOperand.equals(that.leftOperand) &&
                    this.rightOperand.equals(that.rightOperand)&&
                    that.getClass().getName().equals(this.getClass().getName());
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + leftOperand + " " + getSign() + " " + rightOperand + ")";
    }

    public static int prior(Expression e) {
        return priority.get(e.getClass().getName());
    }

    @Override
    public String toMiniString() {
        boolean leftBrackets = prior(leftOperand) > prior(this); // Если приоретет левого операнда слабее - скобочки
        boolean rightBrackets = prior(rightOperand) > prior(this);
        // Свойства вычитания
        if (this instanceof Subtract && prior(this) == prior(rightOperand)) {
            rightBrackets = true;
        }
        // Свойства деления
        if (this instanceof Divide || rightOperand instanceof Divide && prior(rightOperand) == prior(this)) {
            rightBrackets = true;
        }
        // Проверка на константы
        if (leftOperand instanceof Const || leftOperand instanceof Variable) {
            leftBrackets = false;
        }
        if (rightOperand instanceof Const || rightOperand instanceof Variable) {
            rightBrackets = false;
        }
        return (leftBrackets ? '(' : "") + leftOperand.toMiniString() + (leftBrackets ? ')' : "") +
                ' ' + getSign() + ' ' +
                (rightBrackets ? '(' : "") + rightOperand.toMiniString() + (rightBrackets ? ')' : "");
    }
}
