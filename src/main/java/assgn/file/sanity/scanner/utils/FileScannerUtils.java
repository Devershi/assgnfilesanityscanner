package assgn.file.sanity.scanner.utils;

import java.util.Random;

public class FileScannerUtils {

    public static final String PROFANITY_WORDS_FILE = "C:\\Users\\Devershi Srivastava\\Desktop\\Tester\\words.txt";
    public static final String FILE_NAME = "src/main/resources/temp";
    public static final String FILE_EXTENSION = ".txt";

    public static String getFileName(){
        return FILE_NAME + getRandomNumberString() + getRandomNumberString();
    }

    public static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}
