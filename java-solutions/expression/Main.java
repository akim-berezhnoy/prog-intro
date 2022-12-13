package expression;

import expression.parser.ExpressionParser;

public class Main {
    public static void main(String[] args) {
//        Expression exp = new Add(
//                             new Subtract(
//                                     new Multiply(
//                                             new Variable("x"),
//                                             new Variable("x")),
//                                     new Multiply(
//                                             new Const(2),
//                                             new Variable("x"))),
//                             new Const(1));
//        System.out.println(exp.evaluate(Integer.parseInt(args[0])));
        System.out.println(new ExpressionParser().parse("- 0"));
    }
}
