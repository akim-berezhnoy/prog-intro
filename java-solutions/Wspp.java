import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Wspp {
    public static void main(String[] args) {
        Map<String, ArrayList<Integer>> words = new HashMap<>();
        try (MyScanner scanner = new MyScanner(new File(args[0]), StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(new FileWriter(args[1], StandardCharsets.UTF_8))) {
            int counter = 0;
            while (scanner.hasThisLine()) {
                while (scanner.hasNextWordInCurrentLine()) {
                    String word = scanner.readWord().toLowerCase();
                    words.putIfAbsent(word, new ArrayList<>());
                    words.get(word).add(++counter);
                }
            }
            StringBuilder viewBuilder = new StringBuilder();
            for (Map.Entry<String, ArrayList<Integer>> e :
                    words.entrySet().stream().sorted(Comparator.comparingInt(o -> o.getValue().get(0))).toList()) {
                viewBuilder.setLength(0);
                viewBuilder.append(e.getKey()).append(" ").append(e.getValue().size());
                for (int index : e.getValue()) viewBuilder.append(" ").append(index);
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
