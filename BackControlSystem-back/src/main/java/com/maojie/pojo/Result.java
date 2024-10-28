package com.maojie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: Unified response result object, containing a status code, message, and optional data.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {

    private Integer code; // Business status code: 0 - success, 1 - failure

    private String message; // Response message

    private T data; // Response data

    /**
     * @Description: Returns a successful operation response with data.
     * @Param: data - The data to include in the response
     * @return: Result<E> - Response with status code 0, success message, and data
     */
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "Operation successful", data);
    }

    /**
     * @Description: Returns a successful operation response without data.
     * @return: Result - Response with status code 0 and success message
     */
    public static Result success() {
        return new Result(0, "Operation successful", null);
    }

    /**
     * @Description: Returns an error response with a specified message.
     * @Param: message - The error message to include in the response
     * @return: Result - Response with status code 1 and error message
     */
    public static Result error(String message) {
        return new Result(1, message, null);
    }
}
