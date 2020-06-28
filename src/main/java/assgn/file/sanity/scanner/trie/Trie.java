package assgn.file.sanity.scanner.trie;

public class Trie {
    private TrieNode root;
    private int total;

    public Trie() {
        root = new TrieNode();
        total = 0;
    }

    public void insert(String word) {
        if(containsNode(word))
            return;
        TrieNode current = root;

        for (char l : word.toCharArray()) {
            current = current.getChildren().computeIfAbsent(l, c -> new TrieNode());
        }
        current.setwordEnd(true);
        total++;

    }

    public boolean delete(String word) {
        return delete(root, word, 0);
    }

    public boolean containsNode(String word) {
        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.getChildren().get(ch);
            if (node == null) {
                return false;
            }
            current = node;
        }
        return current.isWordEnd();
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int getTotal() {
        return total;
    }

    public boolean delete(TrieNode current, String word, int index) {
        if (index == word.length()) {
            if (!current.isWordEnd()) {
                return false;
            }
            current.setwordEnd(false);
            return current.getChildren().isEmpty();
        }
        char ch = word.charAt(index);
        TrieNode node = current.getChildren().get(ch);
        if (node == null) {
            return false;
        }
        boolean deleteNode = delete(node, word, index + 1) && !node.isWordEnd();

        if (deleteNode) {
            current.getChildren().remove(ch);
            total--;
            return current.getChildren().isEmpty();
        }
        return false;
    }
}
