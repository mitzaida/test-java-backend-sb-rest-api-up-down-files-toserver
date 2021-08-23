package com.example.test.java.backend.sb.rest.api.up.down.files.toserver;

import org.springframework.boot.context.properties.ConfigurationProperties;
import javax.persistence.*;

@ConfigurationProperties(prefix = "file")
@Entity
@Table(name="ontology_documents")
public class DocumentStorageProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="document_id")
    private Integer documentId;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="file_name")
    private String fileName;

    @Column(name="document_type")
    private String documentType;

    @Column(name="document_format")
    private String documentFormat;

    @Column(name="upload_dir")
    private String uploadDir;  // will get initialized by the value put in application.properties (file.upload-dir).


                 // A esta Entity ahora le creamos los Getter and Setter

    public Integer getDocumentId() {
        return documentId;
    }
    public void setDocumentId(Integer documentId) {
        this.documentId=documentId;
    }


    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId=userId;
    }


    public String getFileName(){
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName=fileName;
    }


    public String getDocumentType() {
        return documentType;
    }
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }


    public String getDocumentFormat() {
        return documentFormat;
    }
    public void setDocumentFormat(String documentFormat) {
        this.documentFormat=documentFormat;
    }


    public String getUploadDir () {
        return uploadDir;
    }
    public void setUploadDir(String uploadDir) {
        this.uploadDir=uploadDir;
    }

}  // End of Class DocumentStorageProperties
