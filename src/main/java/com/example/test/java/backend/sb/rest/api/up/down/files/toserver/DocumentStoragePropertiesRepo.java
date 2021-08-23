package com.example.test.java.backend.sb.rest.api.up.down.files.toserver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// Repository interface for DocumentStorageProperties Entity, to get built-in CRUD operations method support.

public interface DocumentStoragePropertiesRepo extends
        JpaRepository<DocumentStorageProperties, Integer> {

    //NOTE: in both queris use the variable docType but id doesn¿ot exist, so i put document_type.

    @Query("Select a from DocumentStorageProperties a where user_id = ?1 and document_type = ?2")
    DocumentStorageProperties checkDocumentByUserId(Integer userid, String documentType);


    @Query("Select filename from DocumentStorageProperties a where user_id = ?1 and document_type=?2")
    String getUploadDocumentPath(Integer userId, String documentType);

}
