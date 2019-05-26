package com.nine96kibs.nine96kibsserver.dto;

import lombok.Getter;
import lombok.Setter;

public class CommonResult {

    private static final int SUCCESS = 200;

    private static final int FAILED = 500;

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private Object data;

    public CommonResult success(Object data){
        this.code = SUCCESS;
        this.message = "OK";
        this.data = data;
        return this;
    }

    public CommonResult failed(String message){
        this.code = FAILED;
        this.message = message;
        return this;
    }

    public CommonResult success(){
        this.code = SUCCESS;
        this.message = "OK";
        return this;
    }
}
