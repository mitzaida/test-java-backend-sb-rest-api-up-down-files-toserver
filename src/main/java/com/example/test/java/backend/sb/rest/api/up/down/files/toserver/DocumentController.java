package com.example.test.java.backend.sb.rest.api.up.down.files.toserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
//import org.springframework.web.servlet.support;

@RestController
public class DocumentController {
    @Autowired
    private DocumentStorageService documentStorageService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,
                                         @RequestParam("userId") Integer userId,
                                         @RequestParam("documentType") String documentType)  {
        String fileName = documentStorageService.storeFile(file, userId, documentType);
        String fileDownloadUri=
            ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    } // end of M. uploadFile.




    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam("userId") Integer userId,
                                                 @RequestParam("documentType") String documentType,
                                                 HttpServletRequest request) {
        String fileName= documentStorageService.getDocumentName(userId, documentType);
        Resource resource = null;

        if (fileName!=null && !fileName.isEmpty()) {
            try {
                resource = (Resource) documentStorageService.loadFileAsResource(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Try to determine file's content type
            String contentType = null;

            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                //logger.info("Could not determine file type.");
            }

            // Fallback to the default content type if type could not be determined
            if(contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + resource.getFilename() + "\"")
                  .body(resource);


        } else {
            return ResponseEntity.notFound().build();
        }

    }  //  End of M. downloadFile


}  //  End of class DocumentController
