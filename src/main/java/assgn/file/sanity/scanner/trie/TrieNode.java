package assgn.file.sanity.scanner.trie;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
    private final Map<Character, TrieNode> children = new HashMap<>();
    private boolean wordEnd;
    Map<Character, TrieNode> getChildren() {
        return children;
    }
    boolean isWordEnd() {
        return wordEnd;
    }

    void setwordEnd(boolean wordEnd) {
        this.wordEnd = wordEnd;
    }
}
