
package org.example.controller;

import org.example.bean.ResponseData;
import org.example.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @Autowired
    private HelloWorldService helloWorldService;

    @RequestMapping("/hello")
    public ResponseData sayHello() {
        try {
            return new ResponseData(helloWorldService.sayHello(), "0");
        } catch (Exception e) {
            return new ResponseData(null, "1");
        }
    }
}
