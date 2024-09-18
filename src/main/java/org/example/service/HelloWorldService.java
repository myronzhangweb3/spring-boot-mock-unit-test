package org.example.service;

import org.example.bean.SayWordsBean;
import org.example.bean.SayWordsResponse;
import org.example.mapper.HelloWorldMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    @Autowired
    private HelloWorldMapper helloWorldMapper;

    public SayWordsResponse sayHello() {
        SayWordsBean mapper = helloWorldMapper.sayHello();
        return new SayWordsResponse(mapper.getWords());
    }
}
