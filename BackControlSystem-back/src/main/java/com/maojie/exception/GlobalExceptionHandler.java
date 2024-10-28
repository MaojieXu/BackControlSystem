package com.maojie.exception;

import com.maojie.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @Description: Handles all exceptions globally by returning a standardized error response. Logs the exception details and provides an error message if available.
     * @Param: e - The exception that was thrown
     * @return: Result - Standardized error message with exception details, or a generic failure message if no details are provided
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "Operation failed ");
    }
}
