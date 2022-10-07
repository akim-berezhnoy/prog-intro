import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ReverseAbc {

    public static void main(String[] args) {
        int capacity = 10;
        String[][] lines = new String[capacity][capacity];
        int[] linesIndexes = new int[capacity];
        int linesIterator = -1;
        String[] words;
        int wordsIterator;
        try {
            MyScanner scanner = new MyScanner(System.in, StandardCharsets.UTF_8);
            try {
                while (scanner.hasThisLine()) {
                    linesIterator++;
                    wordsIterator = -1;
                    words = new String[capacity];
                    while (scanner.hasNextWordInCurrentLine()) {
                        String token = scanner.readWord();
                        wordsIterator++;
                        if (wordsIterator == words.length) words = Arrays.copyOf(words, words.length * 2 + 1);
                        words[wordsIterator] = token;
                    }
                    if (linesIterator == lines.length) {
                        String[][] increasedLines = new String[lines.length*2+1][capacity];
                        System.arraycopy(lines, 0, increasedLines, 0, lines.length);
                        lines = increasedLines;
                        linesIndexes = Arrays.copyOf(linesIndexes, linesIndexes.length*2+1);
                    }
                    linesIndexes[linesIterator] = wordsIterator;
                    lines[linesIterator] = words;
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