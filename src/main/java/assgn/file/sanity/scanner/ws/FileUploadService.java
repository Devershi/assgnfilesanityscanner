package assgn.file.sanity.scanner.ws;

import assgn.file.sanity.scanner.file.processor.FileProcessController;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;

@Path("file")
public class FileUploadService {

    @POST
    @Path("/checkForRestrictedWords")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream) throws IOException {

        System.out.println("got request.....");
        readFile(uploadedInputStream);
        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response processFile(
            @FormDataParam("file") InputStream uploadedInputStream) throws IOException {

        readFile(uploadedInputStream);
        return Response.status(200).entity("").build();
    }

    // save uploaded file to new location
    private void readFile(InputStream uploadedInputStream) {
        FileInputStream is = (FileInputStream) uploadedInputStream;
        FileProcessController.processFile(is);
    }

    public static void main(String[] args) throws FileNotFoundException {
        FileUploadService f=new FileUploadService();
        f.readFile(new FileInputStream("C:\\Users\\Devershi Srivastava\\IdeaProjects\\assgnfilesanityscanner\\config.properties"));
    }
}