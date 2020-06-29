package assgn.file.sanity.scanner.file.processor;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Math.toIntExact;

public class FileProcessController {

    private static final int NO_OF_THREADS = 4;
    private ConcurrentSkipListSet<String> badWordsList= new ConcurrentSkipListSet<>();
    private volatile boolean keepRunning = true;
    boolean checkOnlyForProfanity = false;
    private ConcurrentSkipListSet<String> allBadWords = new ConcurrentSkipListSet<String>();

    public ConcurrentSkipListSet<String> processFile(FileInputStream fileInputStream, boolean checkOnlyForProfanity) {
        try {
            this.checkOnlyForProfanity = checkOnlyForProfanity;
            FileChannel channel = fileInputStream.getChannel();
            long chSize = channel.size();

            int fileSizePerThread = 1024;
            ExecutorService executor = Executors.newFixedThreadPool(NO_OF_THREADS);
            long startingLocation = 0;
            int i = 0;
            FileProcessor fp = new FileProcessor(startingLocation, fileSizePerThread, channel, i, checkOnlyForProfanity, this);
            // executing chunks according to fileSizePerThread
            while (chSize >= fileSizePerThread) {
                executor.execute(fp);
                chSize = chSize - fileSizePerThread;
                startingLocation = startingLocation + fileSizePerThread;
                fp.setFileStartLocation(startingLocation);
                fp.setSequenceNumber(++i);
            }
            // executing left over chunk
            fp.setSizeToBeAllocated((int) chSize);
            executor.execute(fp);
            executor.shutdown();

            while (!executor.isTerminated() && this.keepRunning) {
            }
            System.out.println("Processing finished.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("all bad words : : " + this.allBadWords);
        return this.allBadWords;
    }

    public void onProfanityFound(List<String> foundWords){
        if(this.checkOnlyForProfanity){
            this.keepRunning = false;
        }
        this.allBadWords.addAll(foundWords);
    }
}
