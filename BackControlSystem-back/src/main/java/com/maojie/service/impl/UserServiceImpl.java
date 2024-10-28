package com.maojie.service.impl;

import com.maojie.mapper.UserMapper;
import com.maojie.pojo.User;
import com.maojie.service.UserService;
import com.maojie.utils.Md5Util;
import com.maojie.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * @Description: Finds a user by their username.
     * @Param: username - The username of the user to retrieve
     * @return: User - User object corresponding to the specified username
     */
    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    /**
     * @Description: Registers a new user by encrypting the password and saving the user details.
     * @Param: username - Username for the new user; password - Password for the new user
     * @return: void
     */
    @Override
    public void register(String username, String password) {
        String md5String = Md5Util.getMD5String(password);
        userMapper.add(username, md5String);
    }

    /**
     * @Description: Updates an existing user's profile information and sets the update timestamp.
     * @Param: user - User object containing updated profile details
     * @return: void
     */
    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    /**
     * @Description: Updates the user's avatar URL.
     * @Param: avatarUrl - URL of the new avatar
     * @return: void
     */
    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updataAvatar(avatarUrl, id);
    }

    /**
     * @Description: Updates the user's password after encrypting it with MD5.
     * @Param: newPwd - New password for the user
     * @return: void
     */
    @Override
    public void updatePwd(String newPwd) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
    }
}
