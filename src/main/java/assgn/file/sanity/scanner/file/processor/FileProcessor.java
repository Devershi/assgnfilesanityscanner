package assgn.file.sanity.scanner.file.processor;

import assgn.file.sanity.scanner.core.ContentProcessor;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

public class FileProcessor implements Runnable {

    public static final int testNoOfThreads = 4;

    private FileChannel fileChannel;
    private long fileStartLocation;
    private int sizeToBeAllocated;
    int sequenceNumber;
    boolean checkOnlyForProfanity;
    FileProcessController fpc;

    public void setFileChannel(FileChannel fileChannel) {
        this.fileChannel = fileChannel;
    }

    public void setFileStartLocation(long fileStartLocation) {
        this.fileStartLocation = fileStartLocation;
    }

    public void setSizeToBeAllocated(int sizeToBeAllocated) {
        this.sizeToBeAllocated = sizeToBeAllocated;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public FileProcessor(long loc, int size, FileChannel fChannel, int sequence, boolean checkOnlyForProfanity, FileProcessController fpc) {
        this.fileStartLocation = loc;
        this.sizeToBeAllocated = size;
        this.fileChannel = fChannel;
        this.sequenceNumber = sequence;
        this.checkOnlyForProfanity = checkOnlyForProfanity;
        this.fpc = fpc;
    }

    @Override
    public void run() {
        try {
            System.out.println("-------------------------------------------------------------------");
            ByteBuffer buff = ByteBuffer.allocate(sizeToBeAllocated);
            fileChannel.read(buff, fileStartLocation);
            String stringChunk = new String(buff.array(), Charset.forName("UTF-8"));
            ContentProcessor c = new ContentProcessor();
            List<String> badWordList = c.process(stringChunk);
            if(!badWordList.isEmpty()){
                fpc.onProfanityFound(badWordList);
            }
            System.out.println("Read the channel: " + fileStartLocation + ":" + sizeToBeAllocated);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}