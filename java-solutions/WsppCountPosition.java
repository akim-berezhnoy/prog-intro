import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WsppCountPosition {

    public static List<Map.Entry<String, ArrayList<Integer>>> sorted(Map<String, ArrayList<Integer>> words) {
        return words.entrySet().stream().sorted(new Comparator<Map.Entry<String, ArrayList<Integer>>>() {
            @Override
            public int compare(Map.Entry<String, ArrayList<Integer>> o1, Map.Entry<String, ArrayList<Integer>> o2) {
                if ((o1.getValue() != null) && (o1.getValue() != null)) {
                    if (o1.getValue().size() == o2.getValue().size()) {
                        if (o1.getValue().get(0) == o2.getValue().get(0)) {
                            return o1.getValue().get(1) - o2.getValue().get(1);
                        }
                        return o1.getValue().get(0) - o2.getValue().get(0);
                    }
                    return o1.getValue().size() - o2.getValue().size();
                }
                return 0;
            }
        }).toList();
    }

    public static void main(String[] args) {
        Map<String, ArrayList<Integer>> words = new HashMap<>();
        try (MyScanner scanner = new MyScanner(new File(args[0]), StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(new FileWriter(args[1], StandardCharsets.UTF_8))) {
            int lineCounter = 0;
            while (scanner.hasThisLine()) {
                ++lineCounter;
                int numberCounter = 0;
                while (scanner.hasNextWordInCurrentLine()) {
                    String word = scanner.readWord().toLowerCase();
                    words.putIfAbsent(word, new ArrayList<Integer>());
                    words.get(word).add(lineCounter);
                    words.get(word).add(++numberCounter);
                }
            }
            StringBuilder viewBuilder = new StringBuilder();
            for (Map.Entry<String, ArrayList<Integer>> e : sorted(words)) {
                viewBuilder.setLength(0);
                viewBuilder.append(e.getKey()).append(" ").append(e.getValue().size()/2);
                for (int index = 0; index < e.getValue().size(); index+=2) {
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
