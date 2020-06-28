package assgn.file.sanity.scanner.file.processor;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Math.toIntExact;

public class FileProcessController {

    private static final int NO_OF_THREADS = 4;

    public static void processFile(FileInputStream fileInputStream) {
        try {
            FileChannel channel = fileInputStream.getChannel();
            long chSize = 0;
            chSize = channel.size();

            long fileSizePerThread = 1024;
            ExecutorService executor = Executors.newFixedThreadPool(NO_OF_THREADS);
            long startingLocation = 0;
            int i = 0;
            while (chSize >= fileSizePerThread) {
                executor.execute(new FileProcessor(startingLocation, toIntExact(fileSizePerThread), channel, i));
                chSize = chSize - fileSizePerThread;
                startingLocation = startingLocation + fileSizePerThread;
                i++;
            }
            executor.execute(new FileProcessor(startingLocation, toIntExact(chSize), channel, i));
            executor.shutdown();

            while (!executor.isTerminated()) {
            }
            System.out.println("Processing finished.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
