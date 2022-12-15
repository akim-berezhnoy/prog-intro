package expression.parser;

import expression.Add;
import expression.Clear;
import expression.Const;
import expression.Count;
import expression.Divide;
import expression.Express;
import expression.Multiply;
import expression.Negate;
import expression.Reverse;
import expression.Subtract;
import expression.TripleExpression;
import expression.Variable;
import java.util.Set;
import java.util.Stack;

public class ExpressionParser extends BaseParser implements TripleParser {
    public static TripleExpression parse(final CharSource source) {
        return new ExpressionParser(source).parseExpression();
    }

    private static final PrefixTree binaryTokens = new PrefixTree(
        Set.of("+", "-", "/", "*", "clear", "set", ")"));
    private static final PrefixTree unaryTokens = new PrefixTree(
        Set.of("-", "x", "y", "z", "count", "reverse", "("));

    public ExpressionParser(CharSource source) {
        super(source);
    }

    /*
     * Main ExpressionParser method. Parses till source end or closing bracket.
     * May be executed recursively inside brackets to parse full inside block.
     */
    private Express parseExpression() {
        Stack<Express> operands = new Stack<>();
        Stack<String> operators = new Stack<>();
        operands.push(nextOperand());
        skipWhitespace(); //SKIP WHITESPACE
        while (!eof()) {
            String token = parseToken(binaryTokens);
            skipWhitespace(); //SKIP WHITESPACE
            if (token.equals(")")) {
                break;
            }
            int currentTokenPriority = priority(token);
            while (!operators.isEmpty() && priority(operators.peek()) <= currentTokenPriority) {
                collectDescendingOperatorsOperand(operands, operators);
            }
            operands.push(nextOperand());
            skipWhitespace(); //SKIP WHITESPACE
            operators.push(token);
        }
        while (!operators.isEmpty()) {
            collectDescendingOperatorsOperand(operands, operators);
        }
        return operands.pop();
    }

    /*
     * Collects operators from the top of the operator stack and pushes created operands in the operand stack.
     */
    private void collectDescendingOperatorsOperand(Stack<Express> units, Stack<String> operations) {
        do {
            units.push(createOperation(operations.pop(), units.pop(), units.pop()));
        } while (!operations.isEmpty() && priority(operations.peek()) <= units.peek().getPriority());
    }

    /*
     * Transforms an operand in expression. (Expr)
     */
    private Express nextOperand() {
        String operand = parseOperand();
        if (isNumber(operand)) {
            // Constants
            return new Const(Integer.parseInt(operand));
        } else {
            return switch (operand) {
                // Blocks
                case "(" -> parseExpression();
                // Variables
                case "x", "y", "z" -> new Variable(operand);
                // UnaryOperations
                case "-", "count", "reverse" -> createOperation(operand, nextOperand());
                default -> throw error("Expected unit (block, variable, constant), but found nothing.");
            };
        }
    }

    /*
     * Gobs next operand. (String)
     *
     * operands:
     *     signed constants
     *     bracket blocks
     *     variables
     *     operands, which were produced by applying upcoming unary operation
     */
    private String parseOperand() {
        skipWhitespace(); //SKIP WHITESPACE
        StringBuilder operand = new StringBuilder();
        if (!between('0', '9')) {
            operand.append(parseToken(unaryTokens)); // x
        }
        if (!(operand.isEmpty() || operand.toString().equals("-"))) {
            return operand.toString();
        }
        takeDigits(operand);
        return operand.toString();
    }

    /*
     * Gobs next token. (String)
     */
    private String parseToken(PrefixTree grammar) {
        skipWhitespace(); //SKIP WHITESPACE
        StringBuilder token = new StringBuilder();
        while (!grammar.contains(token.toString()) || (!eof() && grammar.hasPrefix(token.toString() + peek()))) {
            token.append(take());
        }
        return token.toString();
    }

    ////////////////////////////////////////////////////////////////////////////////

    /*
     * auxiliary methods (number checking, expressions creation, priority management)
     */

    private boolean isNumber(String s) {
        return Character.isDigit(s.charAt(0)) || (s.length() > 1 && Character.isDigit(s.charAt(1)));
//            try {
//                Integer.parseInt(s);
//                return true;
//            } catch (NumberFormatException e) {
//                return false;
//            }
    }

    private int priority(String operation) {
        return switch (operation) {
            case "+" -> Add.priority();
            case "-" -> Subtract.priority();
            case "*" -> Multiply.priority();
            case "/" -> Divide.priority();
            case "clear" -> Clear.priority();
            case "set" -> expression.Set.priority();
            //
            // HERE
            //
            default -> 0;
        };
    }

    private Express createOperation(String operator, Express right, Express left) {
        return switch (operator) {
            case "+" -> new Add(left, right);
            case "-" -> new Subtract(left, right);
            case "*" -> new Multiply(left, right);
            case "/" -> new Divide(left, right);
            case "clear" -> new Clear(left, right);
            case "set" -> new expression.Set(left, right);
            //
            // HERE
            //
            default -> throw new UnsupportedOperationException("Unsupported binary operator: " + operator);
        };
    }

    private Express createOperation(String operator, Express operand) {
        return switch (operator) {
            case "-" -> new Negate(operand);
            case "count" -> new Count(operand);
            case "reverse" -> new Reverse(operand);
            //
            // HERE
            //
            default -> throw new UnsupportedOperationException("Unsupported binary operator: " + operator);
        };
    }

    @Override
    public TripleExpression parse(String expression) {
        return parseExpression();
    }
}
