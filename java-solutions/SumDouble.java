public class SumDouble {
    public static void main(String[] args) {
        double sum = 0;
        int i = 0;
        int j = 0;
        for (String arg : args) {
            i = 0;
            while (i != arg.length()) {
                if (!Character.isWhitespace(arg.charAt(i))) {
                    j = 0;
                    while ((i+j+1 != arg.length()) && !Character.isWhitespace(arg.charAt(i+j+1))) {
                        j += 1;
                    }
                    sum += Double.parseDouble(arg.substring(i,i+j+1));
                    i = i+j;
                }
                i++;
            }
        }
        System.out.println(sum);
    }
}