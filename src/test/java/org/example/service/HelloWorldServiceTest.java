package org.example.service;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.example.bean.SayWordsBean;
import org.example.bean.SayWordsResponse;
import org.example.mapper.HelloWorldMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HelloWorldServiceTest {

    @Tested
    private HelloWorldService service;

    @Injectable
    private HelloWorldMapper mapper;

    /**
     * 测试 HelloWorld 接口正常返回 Hello World!
     */
    @Test
    public void testHelloWorldSuccess() {
        // 设置期望
        new Expectations() {{
            mapper.sayHello();
            result = new SayWordsBean("Hello World Mock For SayWordsBean");
        }};

        // 调用被测试的方法
        SayWordsResponse result = service.sayHello();

        // 验证结果
        Assert.assertEquals("Hello World Mock For SayWordsBean", result.getWords());
    }

}
