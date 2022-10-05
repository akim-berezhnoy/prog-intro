import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class WordStatInput {

    public static int index(String word, int borderIndex, String[] words) {
        for (int i = 0; i <= borderIndex; i++) if (words[i].equals(word)) return i;
        return -1;
    }

    public static void main(String[] args) {
        String[] words = new String[10];
        int[] counters = new int[10];
        int wordsIter = -1;
        try {
            MyScanner scanner = new MyScanner(new File(args[0]), StandardCharsets.UTF_8);
            try {
                String word = scanner.nextWord();
                while (word != null) {
                    word = word.toLowerCase();
                    int index = index(word, wordsIter, words);
                    if (index != -1) {
                        counters[index]++;
                        word = scanner.nextWord();
                        continue;
                    }
                    wordsIter++;
                    if (wordsIter == words.length) {
                        words = Arrays.copyOf(words, words.length*2+1);
                        counters = Arrays.copyOf(counters, counters.length*2+1);
                    }
                    words[wordsIter] = word;
                    counters[wordsIter]++;
                    word = scanner.nextWord();
                }
            } finally {
                scanner.close();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(args[1], StandardCharsets.UTF_8));
            try {
                for (int i = 0; i <= wordsIter; i++) {
                    writer.write(words[i] + " " + counters[i]);
                    writer.newLine();
                }
            } finally {
                writer.close();
            }
            /*
             InputMismatchException не вылавливаю, мой сканнер его не кидает.
             В такой ситуации метод .next<token name> ожидаемо вернёт null.
            */
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred. Input file does not exist: " + e.getLocalizedMessage());
        } catch (IOException e) {
            System.out.println("Error occurred. Input or output operation has failed: " + e.getLocalizedMessage());
        }
    }
}
