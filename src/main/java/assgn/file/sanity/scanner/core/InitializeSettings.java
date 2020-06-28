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
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("initializing.........................");
        try (InputStream input = new FileInputStream("C:\\Users\\Devershi Srivastava\\IdeaProjects\\assgnfilesanityscanner\\config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            System.out.println(prop.getProperty("noOfThreads"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("initialized.........................");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
