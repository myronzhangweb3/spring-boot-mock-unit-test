package org.example.controller;

import org.example.bean.ResponseData;
import org.example.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {

    private String uploadDir = "/Users/myronzhang/MyFile/myron/java-project/springboottest/springboottest/src/main/resources/files";

    @Autowired
    private HelloWorldService helloWorldService;

    @PostMapping("/hello")
    public ResponseData sayHello(@RequestBody Map<String, Object> params) {
        return helloWorldService.sayHelloResponse(params);
    }

    @GetMapping("/hello")
    public ResponseData sayHello() {
        try {
            return new ResponseData(helloWorldService.sayHello(new HashMap<>()));
        } catch (Exception e) {
            return new ResponseData(null, "1", e.getMessage());
        }
    }

}
