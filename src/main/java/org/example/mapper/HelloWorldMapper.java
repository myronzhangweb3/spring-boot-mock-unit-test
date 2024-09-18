package org.example.mapper;

import org.example.bean.SayWordsBean;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldMapper {

    public SayWordsBean sayHello() {
        return new SayWordsBean("Hello World");
    }
}
