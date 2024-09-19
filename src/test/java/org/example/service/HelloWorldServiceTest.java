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

import java.util.HashMap;

/**
 * 标记该类为 Spring Boot 测试类，自动加载 Spring 上下文
 */
@SpringBootTest
public class HelloWorldServiceTest {

    /**
     * @Tested 注解用于标记被测试的类的实例。
     * 这个注解会自动处理依赖注入，确保被测试的类的依赖项（如使用 @Injectable 注解的对象）被正确注入。
     * 在这里，我们标记 HelloWorldService 类的实例，以便在测试中使用。
     */
    @Tested
    private HelloWorldService service;

    /**
     * @Injectable 注解用于标记一个类的实例为可注入的依赖项。
     * 这个注解通常用于模拟（mock）一个类的行为，以便在测试中替代真实的实现。
     * 在这里，我们标记 HelloWorldMapper 的实例，以便在测试中使用模拟的 mapper。
     */
    @Injectable
    private HelloWorldMapper mapper;

    /**
     * 测试 HelloWorld 接口正常返回 "Hello World Mock For SayWordsBean"。
     * 这个测试方法验证了 HelloWorldService 的 sayHello() 方法的行为。
     */
    @Test
    public void testHelloWorldSuccess() {
        // 设置期望
        new Expectations() {{
            // 当调用 mapper.sayHello() 方法时，返回一个 SayWordsBean 对象
            mapper.sayHello(new HashMap<>());
            // 定义 mock 返回的结果
            result = new SayWordsBean("Hello World Mock For SayWordsBean");
        }};

        // 调用被测试的方法
        SayWordsResponse result = service.sayHello(new HashMap<>());

        // 验证结果
        // 断言返回的 SayWordsResponse 对象的 words 属性是否等于预期的字符串
        Assert.assertEquals("Hello World Mock For SayWordsBean", result.getWords());
    }
}
