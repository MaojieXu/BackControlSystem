package com.maojie.interceptors;

import com.maojie.utils.JwtUtil;
import com.maojie.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @Description: Pre-handle method that checks if the user is authenticated by validating the JWT token from the request header. It retrieves the token from Redis and verifies it. If valid, sets the token claims in ThreadLocal for use in the request lifecycle.
     * @Param: request - HTTP request; response - HTTP response; handler - The handler for the current request
     * @return: boolean - Returns true if authentication is successful, false otherwise
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        try {
            // Get token from Redis
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(token);
            if (redisToken == null) {
                // Check token
                throw new RuntimeException();
            }
            Map<String, Object> claims = JwtUtil.parseToken(token);
            ThreadLocalUtil.set(claims);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

    /**
     * @Description: After-completion method that clears the ThreadLocal storage to prevent memory leaks after request completion
     * @Param: request - HTTP request; response - HTTP response; handler - The handler for the current request; ex - Any exception thrown, if applicable
     * @return: void
     * @Author: Maojie Xu
     * @Date: 10/25/2024
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
