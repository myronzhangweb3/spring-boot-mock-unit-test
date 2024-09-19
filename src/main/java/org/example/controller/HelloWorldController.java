package org.example.controller;

import org.example.bean.ResponseData;
import org.example.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {

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
