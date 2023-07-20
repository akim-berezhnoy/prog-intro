package wordStat;

import scanner.MyScanner;

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
        int wordsIterator = -1;
        try {
            MyScanner scanner = new MyScanner(new File(args[0]), StandardCharsets.UTF_8);
            try {
                while (scanner.hasThisLine()) {
                    while (scanner.hasNextWordInCurrentLine()) {
                        String word = scanner.readWord().toLowerCase();
                        int index = index(word, wordsIterator, words);
                        if (index != -1) {
                            counters[index]++;
                            continue;
                        }
                        wordsIterator++;
                        if (wordsIterator == words.length) {
                            words = Arrays.copyOf(words, words.length * 2 + 1);
                            counters = Arrays.copyOf(counters, counters.length * 2 + 1);
                        }
                        words[wordsIterator] = word;
                        counters[wordsIterator]++;
                    }
                }
            } finally {
                scanner.close();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(args[1], StandardCharsets.UTF_8));
            try {
                for (int i = 0; i <= wordsIterator; i++) {
                    writer.write(words[i] + " " + counters[i]);
                    writer.newLine();
                }
            } finally {
                writer.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred. Input file does not exist: " + e.getLocalizedMessage());
        } catch (SecurityException e){
            System.out.println("Error occurred. Security violation: " + e.getLocalizedMessage());
        } catch (IOException e) {
            System.out.println("Error occurred. Input or output operation has failed: " + e.getLocalizedMessage());
        }
    }
}
