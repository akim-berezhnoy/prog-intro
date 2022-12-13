package expression.parser;

import java.util.Set;
import java.util.HashMap;

public class PrefixTree {
    public static class Node {
        final private HashMap<Character, Node> outbounds = new HashMap<>();
        private boolean terminal = false;
    }
    final private Node root = new Node();

    public PrefixTree(Set<String> words) {
        for (String word : words) {
            add(word);
        }
    }

    public boolean hasPrefix(String prefix) {
        Node currentNode = root;
        for (int i = 0; i < prefix.length(); i++) {
            currentNode = currentNode.outbounds.getOrDefault(prefix.charAt(i), null);
            if (currentNode == null) {
                return false;
            }
        }
        return true;
    }

    public boolean contains(String prefix) {
        Node currentNode = root;
        for (int i = 0; i < prefix.length(); i++) {
            currentNode = currentNode.outbounds.getOrDefault(prefix.charAt(i), null);
            if (currentNode == null) {
                return false;
            }
        }
        return currentNode.terminal;
    }

    public void add(String codeWord) {
        Node currentNode = root;
        for (int i = 0; i < codeWord.length(); i++) {
            char ch = codeWord.charAt(i);
            currentNode.outbounds.putIfAbsent(ch, new Node());
            currentNode = currentNode.outbounds.get(ch);
        }
        currentNode.terminal = true;
    }
}
