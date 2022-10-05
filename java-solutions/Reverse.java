import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Reverse {
    public static void main(String[] args) {
        int capacity = 10;
        int[][] lines = new int[capacity][capacity];
        int[] linesIndexes = new int[capacity];
        int linesIndex = -1;
        int[] ints;
        int intIndex;
        try {
            MyScanner lineScan = new MyScanner(System.in, StandardCharsets.UTF_8);
            try {
                String line = lineScan.nextLine();
                while (line != null) {
                    linesIndex++;
                    intIndex = -1;
                    ints = new int[capacity];
                    MyScanner intScan = new MyScanner(line, StandardCharsets.UTF_8);
                    try {
                        String integer = intScan.nextInt();
                        while (integer != null) {
                            intIndex++;
                            if (intIndex == ints.length) ints = Arrays.copyOf(ints, ints.length*2+1);
                            ints[intIndex] = Integer.parseInt(integer);
                            integer = intScan.nextInt();
                        }
                    } finally {
                        intScan.close();
                    }
                    if (linesIndex == lines.length) {
                        int[][] increasedLines = new int[lines.length*2+1][capacity];
                        System.arraycopy(lines, 0, increasedLines, 0, lines.length);
                        lines = increasedLines;
                        linesIndexes = Arrays.copyOf(linesIndexes, linesIndexes.length*2+1);
                    }
                    linesIndexes[linesIndex] = intIndex;
                    lines[linesIndex] = ints;
                    line = lineScan.nextLine();
                }
                int index1;
                int index2;
                for (index1 = linesIndex; index1 >= 0; index1--) {
                    for (index2 = linesIndexes[index1]; index2 >= 0; index2--) {
                        System.out.print(lines[index1][index2] + " ");
                    }
                    if (index1 != linesIndex) {
                        System.out.println();
                    }
                }
            } finally {
                lineScan.close();
            }
            /*
             InputMismatchException не вылавливаю, мой сканнер его не кидает.
             В такой ситуации метод .next<token name> ожидаемо вернёт null.
            */
        } catch (IOException e) {
            System.out.println("Error occurred. Input or output operation has failed: " + e.getLocalizedMessage());
        }
    }
}