package org.example.controller;

import org.example.bean.ResponseData;
import org.example.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class HelloWorldController {

    private String uploadDir = "/Users/myronzhang/MyFile/myron/java-project/springboottest/springboottest/src/main/resources/files";

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

    @PostMapping("/uploadFile")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName) {
        System.out.println("params file name: " + fileName);
        // 检查文件是否为空
        if (file.isEmpty()) {
            return new ResponseData(null, "1", "文件为空");
        }

        // 获取文件名
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename: " + originalFilename);
        File destinationFile = new File(uploadDir, originalFilename);

        try {
            // 保存文件
            file.transferTo(destinationFile);
            return new ResponseData(null, "0", "上传成功: " + originalFilename);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseData(null, "2", "上传失败: " + e.getMessage());
        }
    }

    @GetMapping("/downloadFile/{filename:.+}")
    public ResponseEntity<FileSystemResource> downloadFile(@PathVariable String filename) {
        File file = new File(uploadDir, filename);

        // 检查文件是否存在
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

        // 返回文件
        return ResponseEntity.ok()
                .headers(headers)
                .body(new FileSystemResource(file));
    }

    @PostMapping("/downloadFile")
    public ResponseEntity<Void> downloadFilePost(HttpServletResponse response, @RequestBody Map<String, String> params) {
        String filename = params.get("fileName");

        if (filename == null || filename.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        File file = new File(uploadDir, filename);

        // 检查文件是否存在
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 设置响应头
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.setContentLength((int) file.length());

        // 读取文件并写入响应
        try (FileInputStream fileInputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok().build(); // 返回 200 状态
    }

}
