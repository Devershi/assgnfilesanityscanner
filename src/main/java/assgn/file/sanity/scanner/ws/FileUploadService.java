package assgn.file.sanity.scanner.ws;

import assgn.file.sanity.scanner.file.processor.FileProcessController;
import assgn.file.sanity.scanner.utils.FileScannerUtils;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentSkipListSet;

@Path("file")
public class FileUploadService {

    @POST
    @Path("/listOfRestrictedWords")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response getListOfRestrictedWords(
            @FormDataParam("file") InputStream uploadedInputStream) throws IOException {

        ConcurrentSkipListSet<String> cList = readFile(uploadedInputStream, true);
        return Response.status(200).entity(cList.toString()).build();
    }

    @POST
    @Path("/checkForProfanity")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response checkForProfanity(
            @FormDataParam("file") InputStream uploadedInputStream) throws IOException {

        boolean isProfane = false;
        if(!readFile(uploadedInputStream, true).isEmpty()){
            isProfane = true;
        }
        return Response.status(200).entity("The file contains profane words : " + isProfane).build();
    }

    // save uploaded file to new location
    private ConcurrentSkipListSet<String> readFile(InputStream uploadedInputStream, boolean checkOnlyForProfnaity) {
        String fileName = FileScannerUtils.getFileName();
        ConcurrentSkipListSet<String> cSet = new ConcurrentSkipListSet<String>();
        try {
            FileUtils.copyInputStreamToFile(uploadedInputStream, new File(fileName));
            FileInputStream fin=new FileInputStream(fileName);
            FileProcessController fileProcessor = new FileProcessController();
            cSet = fileProcessor.processFile(fin, checkOnlyForProfnaity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cSet;
    }
}