import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Reverse {

    public static void main(String[] args) {
        int capacity = 10;
        int[][] lines = new int[capacity][capacity];
        int[] linesIndexes = new int[capacity];
        int linesIterator = -1;
        int[] ints;
        int intsIterator;
        try {
            MyScanner scanner = new MyScanner(System.in, StandardCharsets.UTF_8);
            try {
                while (scanner.hasThisLine()) {
                    linesIterator++;
                    intsIterator = -1;
                    ints = new int[capacity];
                    while (scanner.hasNextIntInCurrentLine()) {
                        int number = scanner.readInt();
                        intsIterator++;
                        if (intsIterator == ints.length) ints = Arrays.copyOf(ints, ints.length * 2 + 1);
                        ints[intsIterator] = number;
                    }
                    if (linesIterator == lines.length) {
                        int[][] increasedLines = new int[lines.length*2+1][capacity];
                        System.arraycopy(lines, 0, increasedLines, 0, lines.length);
                        lines = increasedLines;
                        linesIndexes = Arrays.copyOf(linesIndexes, linesIndexes.length*2+1);
                    }
                    linesIndexes[linesIterator] = intsIterator;
                    lines[linesIterator] = ints;
                }
                for (int index1 = linesIterator; index1 >= 0; index1--) {
                    for (int index2 = linesIndexes[index1]; index2 >= 0; index2--) {
                        System.out.print(lines[index1][index2] + " ");
                    }
                    System.out.println();
                }
            } finally {
                scanner.close();
            }
        } catch (IOException e) {
            System.out.println("Error occurred. Input or output operation has failed: " + e.getLocalizedMessage());
        }
    }
}