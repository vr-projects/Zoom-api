package com.userOnboard.customResponse;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String StatusStr,String message, HttpStatus status, Map<String,Object> responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", message);
            map.put("status", StatusStr);
            map.put("errorCode", status.value());
            map.put("data", responseObj);

            return new ResponseEntity<Object>(map,status);
    }
}
