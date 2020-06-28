package assgn.file.sanity.scanner.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebListener
public class InitializeSettings implements ServletContextListener {
    public static final String PROFANITY_WORDS_FILE = "C:\\Users\\Devershi Srivastava\\Desktop\\Tester\\words.txt";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("initializing.........................");
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("C:\\Users\\Devershi Srivastava\\IdeaProjects\\assgnfilesanityscanner\\config.properties")) {
            prop.load(input);
            System.out.println(prop.getProperty("noOfThreads"));
            System.out.println(prop.getProperty("restrictedWordsFile"));
            ContentProcessor.loadProfanityFile(prop.getProperty("restrictedWordsFile"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("initialized.........................");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
