package org.example.mapper;

import org.example.bean.SayWordsBean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HelloWorldMapper {

    public SayWordsBean sayHello(Map<String, Object> params) {
        return new SayWordsBean("Hello World");
    }
}
