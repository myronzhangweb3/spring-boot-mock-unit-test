package org.example.controller;

import org.example.bean.ResponseData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@RestController
public class FileController {

    @Value("${file-path}")
    private String uploadDir;

    @PostMapping("/uploadFile")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file,
                                   @RequestParam("fileName") String fileName,
                                   HttpServletRequest request) {
        System.out.println("params file name: " + fileName);
        String userAgent = request.getHeader("User-Agent");
        String contentType = request.getHeader("Content-Type");
        String cookie = request.getHeader("Cookie");
        System.out.println("User-Agent: " + userAgent);
        System.out.println("Content-Type: " + contentType);
        System.out.println("Cookie: " + cookie);

        if (file.isEmpty()) {
            return new ResponseData(null, "1", "文件为空");
        }

        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename: " + originalFilename);
        File destinationFile = new File(uploadDir, originalFilename);

        try {
            file.transferTo(destinationFile);
            return new ResponseData(null, "0", "上传成功: " + originalFilename);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseData(null, "2", "上传失败: " + e.getMessage());
        }
    }

    @GetMapping("/downloadFile/{filename:.+}")
    public ResponseEntity<FileSystemResource> downloadFileGet(@PathVariable String filename) {
        return downloadFileInternal(filename);
    }

    @PostMapping("/downloadFile/{filename:.+}")
    public ResponseEntity<FileSystemResource> downloadFilePost(@PathVariable String filename) {
        return downloadFileInternal(filename);
    }

    @PostMapping("/downloadFile")
    public ResponseEntity<Void> downloadFilePostWithBody(HttpServletResponse response, @RequestBody Map<String, String> params) {
        String filename = params.get("fileName");

        if (filename == null || filename.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        File file = new File(uploadDir, filename);

        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.setContentLength((int) file.length());

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

        return ResponseEntity.ok().build();
    }

    private ResponseEntity<FileSystemResource> downloadFileInternal(String filename) {
        File file = new File(uploadDir, filename);

        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .body(new FileSystemResource(file));
    }
}