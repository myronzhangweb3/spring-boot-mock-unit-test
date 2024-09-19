package org.example.service;

import org.example.bean.ResponseData;
import org.example.bean.SayWordsBean;
import org.example.bean.SayWordsResponse;
import org.example.mapper.HelloWorldMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class HelloWorldService {

    @Autowired
    private HelloWorldMapper helloWorldMapper;

    public SayWordsResponse sayHello(Map<String, Object> params) {
        SayWordsBean mapper = helloWorldMapper.sayHello(params);
        return new SayWordsResponse(mapper.getWords());
    }

    public ResponseData sayHelloResponse(Map<String, Object> params) {
        Object name = params.get("name");
        if (name.toString().length() < 2) {
            return new ResponseData(new ArrayList<>(), "1", "姓名长度不正确");
        }
        return new ResponseData(helloWorldMapper.sayHello(params));
    }
}
