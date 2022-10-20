import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WsppCountPosition {

    public static List<Map.Entry<String, DynamicIntArray>> sorted(Map<String, DynamicIntArray> words) {
        return words.entrySet().stream().sorted(new Comparator<Map.Entry<String, DynamicIntArray>>() {
            @Override
            public int compare(Map.Entry<String, DynamicIntArray> o1, Map.Entry<String, DynamicIntArray> o2) {
                DynamicIntArray arr1 = o1.getValue();
                DynamicIntArray arr2 = o2.getValue();
                if (arr1.getLength() == arr2.getLength()) {
                    // ctrl + alt + l
                    if (arr1.get(0) == arr2.get(0)) return arr1.get(1) - arr2.get(1);
                    return arr1.get(0) - arr2.get(0);
                }
                return arr1.getLength() - arr2.getLength();
            }
        }).toList();
    }

    public static void main(String[] args) {
        Map<String, DynamicIntArray> words = new HashMap<>();
        try (MyScanner scanner = new MyScanner(new File(args[0]), StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(new FileWriter(args[1], StandardCharsets.UTF_8))) {
            int lineCounter = 0;
            while (scanner.hasThisLine()) {
                ++lineCounter;
                int numberCounter = 0;
                while (scanner.hasNextWordInCurrentLine()) {
                    String word = scanner.readWord().toLowerCase();
                    words.putIfAbsent(word, new DynamicIntArray(3));
                    words.get(word).add(lineCounter);
                    words.get(word).add(++numberCounter);
                }
            }
            StringBuilder viewBuilder = new StringBuilder();
            for (Map.Entry<String, DynamicIntArray> e : sorted(words)) {
                viewBuilder.setLength(0);
                viewBuilder.append(e.getKey()).append(" ").append(e.getValue().getLength()/2);
                for (int index = 0; index < e.getValue().getLength(); index+=2) {
                    // not store string
                    // write immediatelly
                    viewBuilder.append(" ").append(e.getValue().get(index)).append(":").append(e.getValue().get(index+1));
                }
                writer.write(viewBuilder.toString());
                writer.newLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error. Input file does not exist: " + e.getLocalizedMessage());
        } catch (SecurityException e){
            System.out.println("Error. Security violation while reading input file: " + e.getLocalizedMessage());
        } catch (IOException e) {
            System.out.println("Error. Input operation has failed: " + e.getLocalizedMessage());
        }
    }
}