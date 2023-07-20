package reverse;

import scanner.MyScanner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ReverseTranspose {
    public static void main(String[] args) {
        int capacity = 10;
        int[][] lines = new int[capacity][capacity];
        int[] linesIndexes = new int[capacity];
        int linesIndex = -1;
        int[] ints;
        int intIndex;
        int maxLength = -1;
        try {
            MyScanner scanner = new MyScanner(System.in, StandardCharsets.UTF_8);
            try {
                while (scanner.hasThisLine()) {
                    linesIndex++;
                    intIndex = -1;
                    ints = new int[capacity];
                    while (scanner.hasNextIntInCurrentLine()) {
                        int number = scanner.readInt();
                        intIndex++;
                        maxLength = Math.max(intIndex, maxLength);
                        if (intIndex == ints.length) ints = Arrays.copyOf(ints, ints.length * 2 + 1);
                        ints[intIndex] = number;
                    }
                    if (linesIndex == lines.length) {
                        int[][] increasedLines = new int[lines.length * 2 + 1][capacity];
                        System.arraycopy(lines, 0, increasedLines, 0, lines.length);
                        lines = increasedLines;
                        linesIndexes = Arrays.copyOf(linesIndexes, linesIndexes.length * 2 + 1);
                    }
                    linesIndexes[linesIndex] = intIndex;
                    lines[linesIndex] = ints;
                }
            } finally {
                scanner.close();
            }
            for (int index1 = 0; index1 <= maxLength; index1++) {
                for (int index2 = 0; index2 <= linesIndex; index2++) {
                    if (index1 <= linesIndexes[index2]) {
                        System.out.print(lines[index2][index1] + " ");
                    }
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred. Input file does not exist: " + e.getLocalizedMessage());
        } catch (IOException e) {
            System.out.println("Error occurred. Input or output operation has failed: " + e.getLocalizedMessage());
        }
    }
}
