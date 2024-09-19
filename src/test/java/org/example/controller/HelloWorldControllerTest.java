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

import java.util.HashMap;

import static org.mockito.Mockito.mock;

/**
 * 标记该类为 Spring Boot 测试类，自动加载 Spring 上下文
 */
@SpringBootTest
public class HelloWorldControllerTest {

    /**
     * @Tested 注解用于标记被测试的类的实例。
     * 这个注解会自动处理依赖注入，确保被测试的类的依赖项（如使用 @Injectable 注解的对象）被正确注入。
     * 在这里，我们标记 HelloWorldController 类的实例，以便在测试中使用。
     */
    @Tested
    private HelloWorldController controller;

    /**
     * @Injectable 注解用于标记一个类的实例为可注入的依赖项。
     * 这个注解通常用于模拟（mock）一个类的行为，以便在测试中替代真实的实现。
     * 在这里，我们标记 HelloWorldService 的实例，以便在测试中使用模拟的服务。
     */
    @Injectable
    private HelloWorldService service;

    /**
     * 测试 HelloWorld 接口正常返回 "Hello World Mock For SayWordsResponse"。
     * 这个测试方法验证了 HelloWorldController 的 sayHello() 方法的行为。
     */
    @Test
    public void testSayHelloWorldSuccess() {
        // 设置期望
        new Expectations() {{
            // 当调用 service.sayHello() 方法时，返回一个 SayWordsResponse 对象
            service.sayHello(new HashMap<>());
            // 定义 mock 返回的结果
            result = new SayWordsResponse("Hello World Mock For SayWordsResponse");
        }};

        // 调用被测试的方法
        ResponseData result = controller.sayHello();

        // 验证结果
        // 断言返回的 ResponseData 对象的 code 属性是否等于 "0"
        Assert.assertEquals("0", result.getCode());
        // 断言返回的 ResponseData 对象的 result 属性是否包含预期的 SayWordsResponse 对象
        Assert.assertEquals("Hello World Mock For SayWordsResponse", ((SayWordsResponse) (result.getResult())).getWords());
    }

    /**
     * 测试 HelloWorld 接口返回异常信息。
     * 这个测试方法验证了当服务抛出异常时，HelloWorldController 的 sayHello() 方法的行为。
     */
    @Test
    public void testSayHelloWorldException() {
        // 设置期望
        new Expectations() {{
            // 当调用 service.sayHello() 方法时，抛出一个异常
            service.sayHello(new HashMap<>());
            // 定义 mock 返回的结果
            result = new Exception("Service Exception");
        }};

        // 调用被测试的方法
        ResponseData result = controller.sayHello();

        // 验证结果
        // 断言返回的 ResponseData 对象的 code 属性是否等于 "1"
        Assert.assertEquals("1", result.getCode());
        // 断言返回的 ResponseData 对象的 result 属性是否为 null
        Assert.assertNull(result.getResult());
    }
}
