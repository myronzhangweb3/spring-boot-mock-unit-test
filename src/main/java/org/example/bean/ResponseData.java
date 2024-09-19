package org.example.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {

    private Object result;
    private String code;
    private String message;

    public ResponseData(Object result) {
        this.result = result;
        this.code = "0";
        this.message = "success";
    }
}
