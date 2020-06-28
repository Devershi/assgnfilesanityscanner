package assgn.file.sanity.scanner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestPropertiesFile {

    public static void main(String[] ar) {
        System.out.println("initializing.........................");
        String userDir = System.getProperty("user.dir");
        System.out.println(userDir);
        try (InputStream input = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            System.out.println(prop.getProperty("noOfThreads"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("initialized.........................");
    }
}
