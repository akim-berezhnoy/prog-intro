package md2html;

import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

public class Md2Html {
    private static final md2html.Md2Html.ExclusiveTree marks = new md2html.Md2Html.ExclusiveTree();
    private static String currentLine;
    private static final Set<String> openedButNotClosed = new HashSet<>();
    private static final Set<String> toClose = new HashSet<>();
    private static final Deque<md2html.Md2Html.Pair> marksDeque = new ArrayDeque<>();
    private static final Map<Character, String> special = new HashMap<>();

    private static void toggle(Set<String> set, String s) {
        if (set.contains(s)) {
            set.remove(s);
        } else {
            set.add(s);
        }
    }
    private static int writeSpecials(StringBuilder chunk, BufferedWriter writer, int start) throws IOException {
        while (!marksDeque.isEmpty() && special.containsKey(marksDeque.getFirst().mark.charAt(0))) {
            md2html.Md2Html.Pair first = marksDeque.getFirst();
            writer.append(chunk.substring(start, first.index)).write(special.get(first.mark.charAt(0)));
            start = first.index + first.mark.length();
            marksDeque.removeFirst();
        }
        return start;
    }
    private static void translate(StringBuilder chunk, BufferedWriter writer) throws IOException {
        marksDeque.clear();
        openedButNotClosed.clear();
        toClose.clear();
        int i = 0;
        int start = i;
        while (i < chunk.length()) {
            int inputMarkLength = tryToParseMark(chunk, i);
            if (inputMarkLength == 0) {
                String currentMark = chunk.substring(i, i + inputMarkLength);
                toggle(openedButNotClosed, currentMark);
                marksDeque.add(new md2html.Md2Html.Pair(currentMark, i));
                if (marksDeque.size() > 0) {
                    start = writeSpecials(chunk, writer, start);
                    if (marksDeque.size() > 1 && marksDeque.getFirst().mark.equals(currentMark)) {
                        while (!marksDeque.isEmpty()) {
                            start = writeSpecials(chunk, writer, start);
                            while (openedButNotClosed.contains(marksDeque.getFirst().mark)) {
                                marksDeque.removeFirst();
                                toggle(openedButNotClosed, marksDeque.getFirst().mark);
                            }
                            md2html.Md2Html.Pair first = marksDeque.getFirst();
                            String mark = first.mark;
                            writer.write(chunk.substring(start, first.index));
                            if (!chunk.substring(start, first.index).contains("\\")) {
                                writer.append('<');
                                if (toClose.contains(mark)) {
                                    writer.append('/');
                                }
                                toggle(toClose, mark);
                                writer.append(parseMark(mark)).write('>');
                            } else {
                                writer.append(chunk.substring(start, chunk.lastIndexOf("\\"))).write(chunk.substring(chunk.lastIndexOf("\\")+1, marksDeque.getFirst().index ));
                            }
                            start = first.index + marksDeque.getFirst().mark.length();
                            marksDeque.removeFirst();
                        }
                        i = start-1;
                    } else {
                        i += inputMarkLength-1;
                    }
                }
            }
            i++;
        }
        String s = chunk.substring(start);
        for (int j = 0; j < s.length(); j++) {
            if (s.charAt(j) != '\\') {
                writer.append(s.charAt(j));
            }
        }
        writer.write("");
    }
    private static int tryToParseMark(StringBuilder sb, int startIndex) {
        int iterator = startIndex;
        if (special.containsKey(sb.charAt(iterator))) {
            return 1;
        }
        md2html.Md2Html.Node currentNode = marks.root.outbounds.getOrDefault(sb.charAt(iterator), null);
        while (currentNode != null) {
            if (startIndex != 0 && sb.charAt(startIndex-1) == '\\') {
                currentNode = null;
                break;
            }
            iterator++;
            if (currentNode.terminal) {
                if (iterator < sb.length() &&
                        currentNode.outbounds.getOrDefault(sb.charAt(iterator), null) != null) {
                    currentNode = currentNode.outbounds.get(sb.charAt(iterator));
                    continue;
                }
                break;
            }
            currentNode = currentNode.outbounds.getOrDefault(sb.charAt(iterator), null);
        }
        if (currentNode == null) {
            return 0;
        }
        return iterator-startIndex;
    }
    private static String parseMark(String sb) {
        int iterator = 0;
        if (special.containsKey(sb.charAt(iterator))) {
            return sb.substring(iterator, iterator + 1);
        }
        md2html.Md2Html.Node currentNode = marks.root.outbounds.getOrDefault(sb.charAt(iterator), null);
        String mark;
        while (true) {
            iterator++;
            if (currentNode.terminal) {
                if (iterator < sb.length() &&
                        currentNode.outbounds.getOrDefault(sb.charAt(iterator), null) != null) {
                    currentNode = currentNode.outbounds.get(sb.charAt(iterator));
                    continue;
                }
                mark = currentNode.meaning;
                break;
            }
            currentNode = currentNode.outbounds.getOrDefault(sb.charAt(iterator), null);
        }
        return mark;
    }
    private static void fillWithTheWholeChunk(StringBuilder sb, BufferedReader reader, int startIndex) throws IOException {
        sb.append(currentLine.substring(startIndex));
        currentLine = reader.readLine();
        if (currentLine == null) {
            return;
        }
        while (currentLine != null && !currentLine.isEmpty()) {
            sb.append(System.lineSeparator()).append(currentLine);
            currentLine = reader.readLine();
        }
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0], StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(new FileWriter(args[1], StandardCharsets.UTF_8))) {
            //
            marks.add("*", "em");
            marks.add("_", "em");
            marks.add("**", "strong");
            marks.add("__", "strong");
            marks.add("--", "s");
            marks.add("`", "code");
            marks.add("++", "u");
            special.put('<', "&lt;");
            special.put('>', "&gt;");
            special.put('&', "&amp;");
            //
            StringBuilder chunk = new StringBuilder();
            currentLine = reader.readLine();
            while (currentLine != null) {
                if (!currentLine.isEmpty()) {
                    if (currentLine.startsWith("#")) {
                        int headingLevel = 0;
                        while (headingLevel < currentLine.length() - 1 && currentLine.charAt(headingLevel) == '#') {
                            headingLevel++;
                        }
                        if (currentLine.charAt(headingLevel) == ' ') {
                            writer.append("<h").append(String.valueOf(headingLevel)).write(">");
                            fillWithTheWholeChunk(chunk, reader, headingLevel+1);
                            translate(chunk, writer);
                            writer.append("</h").append(String.valueOf(headingLevel)).append(">").write(System.lineSeparator());
                            chunk.setLength(0);
                            continue;
                        }
                    }
                    writer.write("<p>");
                    fillWithTheWholeChunk(chunk, reader, 0);
                    translate(chunk, writer);
                    writer.append("</p>").write(System.lineSeparator());
                    chunk.setLength(0);
                }
                currentLine = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred. Input or output file does not exist: " + e.getLocalizedMessage());
        } catch (SecurityException e){
            System.out.println("Error occurred. Security violation: " + e.getLocalizedMessage());
        } catch (IOException e) {
            System.out.println("Error occurred. Input or output operation has failed: " + e.getLocalizedMessage());
        }
    }

    private static class ExclusiveTree {
        final private md2html.Md2Html.Node root = new md2html.Md2Html.Node();
        public void add(String codeWord, String meaning) {
            md2html.Md2Html.Node currentNode = root;
            for (int i = 0; i < codeWord.length(); i++){
                currentNode.addBound(codeWord.charAt(i));
                currentNode = currentNode.outbounds.get(codeWord.charAt(i));
            }
            currentNode.terminal = true;
            currentNode.meaning = meaning;
        }
    }
    private static class Node {
        final protected HashMap<Character, md2html.Md2Html.Node> outbounds = new HashMap<>();
        boolean terminal = false;
        String meaning;
        public void addBound(char ch) {
            outbounds.putIfAbsent(ch, new md2html.Md2Html.Node());
        }
    }
    private static class Pair {
        String mark;
        Integer index;
        public Pair(String s, Integer i) {
            mark = s;
            index = i;
        }
        @Override
        public String toString() {
            return this.mark + " " + this.index;
        }
    }
}
