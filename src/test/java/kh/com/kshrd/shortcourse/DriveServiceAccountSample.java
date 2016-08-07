package kh.com.kshrd.shortcourse;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.common.io.Files;

public class DriveServiceAccountSample {


    private static final String APPLICATION_NAME = "";

    /** E-mail address of the service account. */
    private static final String SERVICE_ACCOUNT_EMAIL = "senate-elibrary@senate-elibrary-of-cambodiav03.iam.gserviceaccount.com" ;

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    /** Global instance of the JSON factory. */
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();


    public static void main(String[] args) {
      try {
        try {
          httpTransport = GoogleNetHttpTransport.newTrustedTransport();
          String p12Content = Files.readFirstLine(new java.io.File("C:\\Users\\HRD\\Downloads\\Senate Elibrary of CambodiaV03-807fdf4300da.p12"), Charset.defaultCharset());

          // service account credential (uncomment setServiceAccountUser for domain-wide delegation)
          GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
              .setJsonFactory(JSON_FACTORY)
              .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
              .setServiceAccountScopes(Collections.singleton(DriveScopes.DRIVE))
              .setServiceAccountPrivateKeyFromP12File(new java.io.File("C:\\Users\\HRD\\Downloads\\Senate Elibrary of CambodiaV03-807fdf4300da.p12"))

              .build();


          Drive drive = new Drive.Builder(httpTransport, JSON_FACTORY,credential).build();
          System.out.println(drive);
          
       // create a folder

          File imageFolder = new File();
                  imageFolder.setName("IMAGE");
                  imageFolder.setMimeType("application/vnd.google-apps.folder");

          File imagefile = drive.files().create(imageFolder).execute();

          String imagefolderid = imagefile.getId();


          //upload a image to this folder


          File body = new File();
          body.setName("image");
          body.setDescription("Test");
          body.setMimeType("image/jpeg");
                  
           if (imagefolderid != null && imagefolderid.length() > 0) {
                body.setParents(Arrays.asList(imagefolderid));
          } 
                   
          java.io.File fileContent = new java.io.File("C:\\Users\\HRD\\Desktop\\13442180_1963318313894492_1359584135105536321_n.jpg");
          FileContent mediaContent = new FileContent("image/jpeg", fileContent);

          File file = drive.files().create(body, mediaContent).execute();


          // share this folder to other normal google drive account

          Permission newPermission = new Permission();
          newPermission.setEmailAddress("senate.elibrary@gmail.com");
          newPermission.setType("user");
          newPermission.setRole("reader");
          drive.permissions().create(imagefolderid, newPermission).execute();
          
          return;
        } catch (IOException e) {
          System.err.println(e.getMessage());
        }
      } catch (Throwable t) {
        t.printStackTrace();
      }
      System.exit(1);
    }
    
    
}
