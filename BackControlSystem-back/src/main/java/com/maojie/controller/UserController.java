package com.maojie.controller;

import com.maojie.pojo.Result;
import com.maojie.pojo.User;
import com.maojie.service.UserService;
import com.maojie.utils.JwtUtil;
import com.maojie.utils.Md5Util;
import com.maojie.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @Description: Registers a new user in the system if the username is not already taken
     * @Param: username - Username for the new user; password - Password for the new user
     * @return: Result - Success message or error if user already exists
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        User u = userService.findByUserName(username);
        if (u == null) {
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("User exist");
        }
    }

    /**
     * @Description: Logs in a user by validating username and password; returns a token upon success
     * @Param: username - Username for login; password - Password for login
     * @return: Result<String> - Success message with token or error message for incorrect credentials
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        User loginUser = userService.findByUserName(username);
        if (loginUser == null) {
            return Result.error("Username errors");
        }

        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            // login success
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            // store token into redis
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 1, TimeUnit.HOURS);
            return Result.success(token);
        }
        return Result.error("Password errors");
    }

    /**
     * @Description: Retrieves user information based on the provided authorization token
     * @Param: token - Authorization token from request header
     * @return: Result<User> - Success message with user information
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name = "Authorization") String token) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    /**
     * @Description: Updates the user's profile information
     * @Param: user - User object with updated profile information
     * @return: Result - Success message after updating the user
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success("User information update successful");
    }

    /**
     * @Description: Updates the user's avatar URL
     * @Param: avatarUrl - URL of the new avatar
     * @return: Result - Success message after updating the avatar
     * @Author: Maojie Xu
     * @Date: 10/25/2024
     */
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success("update avatar successful");
    }

    /**
     * @Description: Updates the user's password after validating the old password
     * @Param: params - Map containing old, new, and confirmation passwords; token - Authorization token from request header
     * @return: Result - Success message or error message if validation fails
     * @Author: Maojie Xu
     * @Date: 10/25/2024
     */
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("need necessary argument");
        }

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUserName(username);
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("original password is wrong");
        }
        if (!rePwd.equals(newPwd)) {
            return Result.error("Two new passwords entered do not match");
        }
        // use service to update the password
        userService.updatePwd(newPwd);
        // delete token from Redis
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }
}
