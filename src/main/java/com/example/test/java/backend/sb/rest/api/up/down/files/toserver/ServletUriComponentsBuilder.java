package com.example.test.java.backend.sb.rest.api.up.down.files.toserver;

import org.springframework.web.util.UriComponentsBuilder;

 public class ServletUriComponentsBuilder extends UriComponentsBuilder {

     public static ServletUriComponentsBuilder fromCurrentContextPath() {
         return fromContextPath(getCurrentRequest());
     }

     private static ServletUriComponentsBuilder fromContextPath(Object currentRequest) {
         return (ServletUriComponentsBuilder) currentRequest;
     }

     private static Object getCurrentRequest() {
         return getCurrentRequest();
     }

}
