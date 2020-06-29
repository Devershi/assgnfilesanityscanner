package assgn.file.sanity.scanner.core;

import assgn.file.sanity.scanner.trie.Trie;
import assgn.file.sanity.scanner.utils.FileScannerUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ContentProcessor {

    static Trie trie;

    public static void loadProfanityFile(String filePath) {
        trie = new Trie();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                trie.insert(line.trim().toUpperCase());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String preprocess(String line) {
    //uppercase
    // Can include steps to remove all punctuations  : :,
    // can replace the common numerics 1->I, 8->B
        return line.toUpperCase();

    }

    public List<String> process(String line) {
        preprocess(line);
        line = preprocess(line);
        ArrayList<String> badWords = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(line);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trie.containsNode(token)) {
                badWords.add(token);
            }
        }
        return badWords;
    }

    public static void test() {

        System.out.println("Loading config file");
        ContentProcessor m = new ContentProcessor();

        m.loadProfanityFile(FileScannerUtils.PROFANITY_WORDS_FILE);
        System.out.println("We have database of " + m.trie.getTotal() + " words");

        String linesForTesting[] = {"nksnm mjsdk Mklsm snms jkm, ",
                "hello n,mnxa,sl nmasnxsa  kka ",
                "no bad words here"};

        for (String line : linesForTesting) {
            ArrayList<String> findBadWords = (ArrayList<String>) m.process(line);
            if (findBadWords.size() > 0) {
                System.out.println("your line had following bad words " + findBadWords);
            } else {
                System.out.println("No Profanity");
            }
        }

    }
}
