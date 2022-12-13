package expression.parser;

import expression.*;

import java.util.*;

public class ExpressionParser implements TripleParser {
    public static TripleExpression parse(final CharSource source) {
        return new StaticExpressionParser(source).parseExpression();
    }
    @Override
    public TripleExpression parse(String expression) {
        return parse(new StringSource(expression));
    }

    private static class StaticExpressionParser extends BaseParser {

        private static final PrefixTree binaryGrammar = new PrefixTree(Set.of("+", "-", "/", "*", ")"));
        private static final PrefixTree unaryGrammar = new PrefixTree(Set.of("-", "x", "y", "z", "("));

        public StaticExpressionParser(CharSource source) {
            super(source);
        }

        private Expr parseExpression() {
            Stack<Expr> units = new Stack<>();
            units.push(parseUnit());
            Stack<String> operations = new Stack<>();
            while (!eof()) {
                /* after-unit tokens:
                 *
                 * 1. )
                 * 2. +
                 * 3. -
                 * 4. *
                 * 5. /
                 *
                 */
                String token = nextLexem(false);
                skipWhitespace();
                if (token.equals(")")) {
                    break;
                }
                int currentTokenPriority = priority(token);
                while (!operations.isEmpty() && priority(operations.peek()) <= currentTokenPriority) {
                    collapse(units, operations);
                }
                units.push(parseUnit());
                operations.push(token);
            }
            while (!operations.isEmpty()) {
                collapse(units, operations);
            }
            return units.pop();
        }

        private void collapse(Stack<Expr> units, Stack<String> operations) {
            do {
                units.push(createOperation(operations.pop(), units.pop(), units.pop()));
            } while (!operations.isEmpty() && priority(operations.peek()) <= units.peek().getPriority());
        }

        private int priority(String operation) {
            return switch (operation) {
                case "+" -> Add.priority();
                case "-" -> Subtract.priority();
                case "*" -> Multiply.priority();
                case "/" -> Divide.priority();
                default -> 0;
            };
        }

        private Expr createOperation(String operator, Expr right, Expr left) {
            return switch (operator) {
                case "+" -> new Add(left, right);
                case "-" -> new Subtract(left, right);
                case "*" -> new Multiply(left, right);
                case "/" -> new Divide(left, right);
                default -> throw new UnsupportedOperationException("Unknown binary operator: " + operator);
            };
        }

        private Expr createOperation(String operator, Expr operand) {
            return switch (operator) {
                case "-" -> new Negate(operand);
                default -> throw new UnsupportedOperationException("Unknown binary operator: " + operator);
            };
        }

        /* Unit cases:
         *
         * 1. const
         * 2. x|y|z
         * 3. (expression)
         * 4. - unit
         *
         */

        private Expr parseUnit() {
            String token = nextLexem(true);
            skipWhitespace();
            if (isNumber(token)) {
                // Constant
                return new Const(Integer.parseInt(token));
            } else {
                return switch (token) {
                    // Variable
                    case "x", "y", "z" -> new Variable(token);
                    // Block
                    case "(" -> parseExpression();
                    // Unary
                    case "-" -> createOperation(token, parseUnit());
                    default -> throw error("Expected unit (block, variable, constant), but found nothing.");
                };
            }
        }

        private boolean isNumber(String s) {
            return Character.isDigit(s.charAt(0)) || s.startsWith("-") && s.length() > 1;
        }

        private String nextLexem(boolean expectingUnary) {
            skipWhitespace();
            StringBuilder lexem = new StringBuilder();
            if (expectingUnary) {
                takeDigits(lexem);
                if (lexem.isEmpty()) {
                    addLexemPart(unaryGrammar, lexem);
                }
                if (lexem.toString().equals("(")) {
                    return lexem.toString();
                }
                takeDigits(lexem);
            } else {
                addLexemPart(binaryGrammar, lexem);
            }
            return lexem.toString();
        }

        private void addLexemPart(PrefixTree grammar, StringBuilder lexem) {
            while (!grammar.contains(lexem.toString()) || (!eof() && grammar.hasPrefix(lexem.toString() + peek()))) {
                lexem.append(take());
            }
        }
    }
}
