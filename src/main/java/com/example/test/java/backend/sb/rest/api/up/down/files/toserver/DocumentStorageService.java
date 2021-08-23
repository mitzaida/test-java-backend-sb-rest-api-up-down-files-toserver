package com.example.test.java.backend.sb.rest.api.up.down.files.toserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class DocumentStorageService {

    private final Path fileStorageLocation;

    @Autowired
    DocumentStoragePropertiesRepo docStorageRepo;

    // Constructor
    @Autowired
    public DocumentStorageService(DocumentStorageProperties fileStorageProperties)
            throws DocumentStorageException {

        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new DocumentStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }



    public String storeFile(MultipartFile file, Integer userId, String documentType) {
        // Normalize file name
        String originalFileName= StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = "";

        try {
            // Check if the file's name contains invalid characters
            if (originalFileName.contains("..")) {
                throw new DocumentStorageException("Sorry filename contains invalid path sequence " + originalFileName);
            }

            String fileExtension = "";
            try {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            } catch (Exception e) {
                fileExtension="";
            }
            fileName=userId+"_"+documentType+fileExtension;  // nombre del user+tipo+extensión...no se y el nombre?
            //Copy file to the target location (Replacing existing file with the same name)

            Path targetLocation  = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            DocumentStorageProperties doc = docStorageRepo.checkDocumentByUserId(userId, documentType);
            if (doc != null ) {
                doc.setDocumentFormat(file.getContentType());
                doc.setFileName(fileName);
                docStorageRepo.save(doc);
            } else {
                DocumentStorageProperties newDoc = new DocumentStorageProperties();
                newDoc.setUserId(userId);
                newDoc.setDocumentFormat(file.getContentType());
                newDoc.setFileName(fileName);
                newDoc.setDocumentType(documentType);
                docStorageRepo.save(newDoc);
            }
            return null;
        }  catch (IOException ex){
             throw new DocumentStorageException("Could not store file " + fileName + " Please tray again.", ex );
        }

    }  // End of M. storeFile



    public Resource loadFileAsResource(String fileName) throws  Exception {

        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = (Resource) new UrlResource(filePath.toUri());

            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName);
        }


    }  //  End of M. loadFileAsResource


    
    public String getDocumentName(Integer userId, String documentType ) {
        return docStorageRepo.getUploadDocumentPath(userId, documentType);
    }

} // End of class DocumentStorageService