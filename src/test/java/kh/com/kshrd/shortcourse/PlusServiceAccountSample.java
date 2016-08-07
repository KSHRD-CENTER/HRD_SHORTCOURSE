package kh.com.kshrd.shortcourse;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.Permission;
import com.google.common.io.Files;

public class PlusServiceAccountSample {


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
          // check for valid setup
          /*if (SERVICE_ACCOUNT_EMAIL.startsWith("Enter ")) {
            System.err.println(SERVICE_ACCOUNT_EMAIL);
            System.exit(1);
          }*/
          String p12Content = Files.readFirstLine(new File("C:\\Users\\HRD\\Downloads\\Senate Elibrary of CambodiaV03-807fdf4300da.p12"), Charset.defaultCharset());
/*          if (p12Content.startsWith("Please")) {
            System.err.println(p12Content);
            System.exit(1);
          }*/
          // service account credential (uncomment setServiceAccountUser for domain-wide delegation)
          GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
              .setJsonFactory(JSON_FACTORY)
              .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
              .setServiceAccountScopes(Collections.singleton(DriveScopes.DRIVE))
              .setServiceAccountPrivateKeyFromP12File(new File("C:\\Users\\HRD\\Downloads\\Senate Elibrary of CambodiaV03-807fdf4300da.p12"))

              .build();


          Drive service = new Drive.Builder(httpTransport, JSON_FACTORY,credential).build();
          
         //System.out.println(service.getApplicationName());
          
/*          com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
          fileMetadata.setName("Project plan");
          fileMetadata.setMimeType("application/vnd.google-apps.drive-sdk");
          com.google.api.services.drive.model.File file = service.files().create(fileMetadata)
                  .setFields("id")
                  .execute();
          System.out.println("File ID: " + file.getId());*/
          
          
          com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
          fileMetadata.setName("My Report");
          fileMetadata.setMimeType("application/vnd.google-apps.spreadsheet");

          java.io.File filePath = new java.io.File("C:\\Users\\HRD\\Desktop\\SQL QUIZ CORRECTION.csv");
          FileContent mediaContent = new FileContent("text/csv", filePath);
          //0B6qsMktn80a3dHE3ZGVhcGR6MjA
          com.google.api.services.drive.model.File file = service.files().create(fileMetadata, mediaContent)
                  .setFields("id")
                  .execute();
          System.out.println("File ID: " + file.getId());
          
          
          /*com.google.api.services.drive.model.File fileMetadata1 = new com.google.api.services.drive.model.File();
          fileMetadata1.setName("Invoices");
          fileMetadata1.setMimeType("application/vnd.google-apps.folder");

          com.google.api.services.drive.model.File file1 = service.files().create(fileMetadata1)
                  .setFields("id")
                  .execute();
          System.out.println("Folder ID: " + file1.getId());*/
          
          Permission newPermission = new Permission();
          //for showing files in browser that reason only using additional permission 
          newPermission.setId(SERVICE_ACCOUNT_EMAIL);
          newPermission.setEmailAddress("senate.elibrary@gmail.com");
          newPermission.setType("user");
          newPermission.setRole("writer");
          //service.permissions().create(file.getId(), newPermission).execute();
          
          String folderId = "0BwxmFmAg8DYKTWRNc0U1dFQ2bkU";
          com.google.api.services.drive.model.File fileMetadata1 = new com.google.api.services.drive.model.File();
          fileMetadata.setName("photo.jpg");
          fileMetadata.setParents(Collections.singletonList(folderId));
          java.io.File filePath1 = new java.io.File("C:\\Users\\HRD\\Desktop\\13442180_1963318313894492_1359584135105536321_n.jpg");
          FileContent mediaContent1 = new FileContent("image/jpeg", filePath1);
          
          
          
          com.google.api.services.drive.model.File file1 = service.files().create(fileMetadata1, mediaContent1)
                  .setFields("id, parents")
                .execute();
          System.out.println("File ID: " + file1.getId());
          //service.permissions().create(file1.getId(), newPermission).execute();
          
          com.google.api.services.drive.model.File newContent = new com.google.api.services.drive.model.File();
          newContent.setTrashed(true);
          service.files().update(file1.getId(), newContent).execute();
          
         
          
          //System.out.println(file.getOriginalFilename());
          

         /* com.google.api.services.drive.model.File  file = new com.google.api.services.drive.model.File();
          file.setName("testdd");
          //file.setTitle("testdd");
          file.setMimeType("application/vnd.google-apps.spreadsheet");
          Drive.Files.Create insert = null;
          try {
              insert = service.files().create(file);
              file = insert.execute();
          } catch (Exception e) {
              e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
          }
          
          

          com.google.api.services.drive.model.File uploadedDoc = service.files().get("1ZJnkgFXAiUNSBeq3DmhHGqXn7-v37vLg1UsRekRQNjU").execute();

          System.out.println("Title: " + uploadedDoc.getName());*/
          
      /*    com.google.api.services.drive.Drive.Files.List request = service.files().list().setQ(
        	       "mimeType='application/vnd.google-apps.spreadsheet' and trashed=false");
          FileList files = request.execute();
          
          for(com.google.api.services.drive.model.File f : files.getFiles()){
        	  System.out.println(f.getName());
          }*/
          
         
          
          /*com.google.api.services.drive.model.File body = new com.google.api.services.drive.model.File();
          body.setName("title");
          body.setMimeType("application/vnd.google-apps.folder");
          com.google.api.services.drive.model.File file22 = service.files().create(body).execute();*/
          
/*          String pageToken = null;
          do {
              FileList result = service.files().list()
                      //.setQ("mimeType='image/jpeg'")
                      .setSpaces("drive")
                      .setFields("nextPageToken, files(id, name)")
                      .setPageToken(pageToken)
                      .execute();
              for(com.google.api.services.drive.model.File f: result.getFiles()) {
                  System.out.printf("Found file: %s (%s)\n",
                          file.getDescription(), f.getId());
              }
              pageToken = result.getNextPageToken();
          } while (pageToken != null);
			*/
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
