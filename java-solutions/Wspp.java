import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Wspp {
    public static void main(String[] args) {
        ArrayList<String> wordsOrder = new ArrayList<>();
        HashMap<String, ArrayList<Integer>> words = new HashMap<>();
        // **************************************************************************************
        try (MyScanner scanner = new MyScanner(new File(args[0]), StandardCharsets.UTF_8)) {
            int counter = 0;
            while (scanner.hasThisLine()) {
                while (scanner.hasNextWordInCurrentLine()) {
                    String word = scanner.readWord().toLowerCase();
                    if (!words.containsKey(word)) {
                        words.put(word, new ArrayList<>());
                        wordsOrder.add(word);
                    }
                    words.get(word).add(++counter);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error. Input file does not exist: " + e.getLocalizedMessage());
        } catch (SecurityException e){
            System.out.println("Error. Security violation while reading input file: " + e.getLocalizedMessage());
        } catch (IOException e) {
            System.out.println("Error. Input operation has failed: " + e.getLocalizedMessage());
        }
        // **************************************************************************************
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(args[1], StandardCharsets.UTF_8))) {
            StringBuilder viewBuilder = new StringBuilder();
            for (String word : wordsOrder) {
                viewBuilder.setLength(0);
                viewBuilder.append(word).append(" ").append(words.get(word).size());
                for (int index : words.get(word)) viewBuilder.append(" ").append(index);
                writer.write(viewBuilder.toString());
                writer.newLine();
            }
        } catch (SecurityException e){
            System.out.println("Error. Security violation while writing in output file: " + e.getLocalizedMessage());
        } catch (IOException e) {
            System.out.println("Error. Output operation has failed: " + e.getLocalizedMessage());
        }
    }
}
