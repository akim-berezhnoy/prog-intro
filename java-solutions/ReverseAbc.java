import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ReverseAbc {

//    private static int abcToDec(String token) {
//        StringBuilder abcToDecStringBuilder = new StringBuilder();
//        for (int i = 0; i < token.length(); i++) {
//            if(Character.isLetter(token.charAt(i))) {
//                abcToDecStringBuilder.append((int)token.charAt(i) - 'a');
//            } else {
//                abcToDecStringBuilder.append(token.charAt(i));
//            }
//        }
//        return Integer.parseInt(abcToDecStringBuilder.toString());
//    }

    public static void main(String[] args) {
        int capacity = 10;
        String[][] lines = new String[capacity][capacity];
        int[] linesIndexes = new int[capacity];
        int linesIndex = -1;
        String[] strings;
        int intIndex;
        try {
            MyScanner lineScan = new MyScanner(System.in, StandardCharsets.UTF_8);
            try {
                String line = lineScan.nextLine();
                while (line != null) {
                    linesIndex++;
                    intIndex = -1;
                    strings = new String[capacity];
                    MyScanner intScan = new MyScanner(line, StandardCharsets.UTF_8);
                    try {
                        String token = intScan.nextWord();
                        while (token != null) {
                            intIndex++;
                            if (intIndex == strings.length) strings = Arrays.copyOf(strings, strings.length*2+1);
                            strings[intIndex] = token;
                            token = intScan.nextWord();
                        }
                    } finally {
                        intScan.close();
                    }
                    if (linesIndex == lines.length) {
                        String[][] increasedLines = new String[lines.length*2+1][capacity];
                        System.arraycopy(lines, 0, increasedLines, 0, lines.length);
                        lines = increasedLines;
                        linesIndexes = Arrays.copyOf(linesIndexes, linesIndexes.length*2+1);
                    }
                    linesIndexes[linesIndex] = intIndex;
                    lines[linesIndex] = strings;
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