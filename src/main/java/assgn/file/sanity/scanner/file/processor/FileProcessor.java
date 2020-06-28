package assgn.file.sanity.scanner.file.processor;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class FileProcessor implements Runnable {

    public static final int testNoOfThreads = 4;

    private FileChannel fileChannel;
    private long fileStartLocation;
    private int sizeToBeAllocated;
    int sequenceNumber;

    public FileProcessor(long loc, int size, FileChannel fChannel, int sequence) {
        this.fileStartLocation = loc;
        this.sizeToBeAllocated = size;
        this.fileChannel = fChannel;
        this.sequenceNumber = sequence;
    }

    @Override
    public void run() {
        try {
            System.out.println("Reading the channel: " + fileStartLocation + ":" + sizeToBeAllocated);
            ByteBuffer buff = ByteBuffer.allocate(sizeToBeAllocated);
            fileChannel.read(buff, fileStartLocation);
            String stringChunk = new String(buff.array(), Charset.forName("UTF-8"));
            System.out.println("-------------------------------------------------------------------");
            System.out.println("Read the following : " + stringChunk);
            System.out.println("-------------------------------------------------------------------");
            System.out.println("Done Reading the channel: " + fileStartLocation + ":" + sizeToBeAllocated);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}