package com.mushan.im.exception;



import com.mushan.im.utils.ResponseUtils;
import com.mushan.im.utils.TokenExcption;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object exceptionHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        if (e instanceof TokenExcption){
            return ResponseUtils.unlogin();
        }else if (e instanceof MalformedJwtException) {
            return ResponseUtils.unlogin();
        }else if (e instanceof ExpiredJwtException){
            return ResponseUtils.unlogin();
        }else {
            return ResponseUtils.fail();
        }
    }

}