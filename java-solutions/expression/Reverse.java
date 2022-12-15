package expression;

public class Reverse extends UnaryOperation {
    public Reverse(Express operand) {
        super(operand);
    }

    @Override
    public boolean isLeftAssociative() {
        return true;
    }

    @Override
    public boolean isRightAssociative() {
        return true;
    }

    @Override
    public String getSign() {
        return "reverse";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public int makeOperation(int a) {
        StringBuilder number = new StringBuilder(Long.toString(a)).reverse();
        int start = 0;
        while (start < number.length()-1 && number.charAt(start) == '0') {
            start++;
        }
        int end = start;
        boolean neg = false;
        while (end < number.length()) {
            if (number.charAt(end) == '-') {
                neg = true;
                break;
            }
            end++;
        }
        return  neg ?
                (int)-Long.parseLong(number.substring(start, end))%2147483647 :
                (int)Long.parseLong(number.substring(start, end))%2147483647;
    }
}
