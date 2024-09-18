package org.example.controller;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.example.bean.ResponseData;
import org.example.bean.SayWordsResponse;
import org.example.service.HelloWorldService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HelloWorldControllerTest {

    @Tested
    private HelloWorldController controller;

    @Injectable
    private HelloWorldService service;

    /**
     * 测试 HelloWorld 接口正常返回 Hello World!
     */
    @Test
    public void testSayHelloWorldSuccess() {
        // 设置期望
        new Expectations() {{
            service.sayHello();
            result = new SayWordsResponse("Hello World Mock For SayWordsResponse");
        }};

        // 调用被测试的方法
        ResponseData result = controller.sayHello();

        // 验证结果
        Assert.assertEquals("0", result.getCode());
        Assert.assertEquals("Hello World Mock For SayWordsResponse", ((SayWordsResponse) (result.getResult())).getWords());
    }

    /**
     * 测试 HelloWorld 接口返回异常信息
     */
    @Test
    public void testSayHelloWorld() {
        // 设置期望
        new Expectations() {{
            service.sayHello();
            result = new Exception();
        }};

        // 调用被测试的方法
        ResponseData result = controller.sayHello();

        // 验证结果
        Assert.assertEquals("1", result.getCode());
        Assert.assertNull(result.getResult());
    }
}
