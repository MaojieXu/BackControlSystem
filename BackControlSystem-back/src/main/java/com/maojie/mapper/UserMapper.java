package com.maojie.mapper;

import com.maojie.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    /**
     * @Description: Retrieves a user by their username
     * @Param: username - The username of the user to retrieve
     * @return: User - User object corresponding to the specified username
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUserName(String username);

    /**
     * @Description: Inserts a new user record into the database with the specified username and password
     * @Param: username - The username of the new user; password - The password of the new user
     * @return: void
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @Insert("INSERT INTO user(username, password, create_time, update_time) " +
            "VALUES(#{username}, #{password}, NOW(), NOW())")
    void add(String username, String password);

    /**
     * @Description: Updates an existing user's profile information
     * @Param: user - User object containing updated user information (nickname, email, update time)
     * @return: void
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @Update("UPDATE user SET nickname=#{nickname}, email=#{email}, update_time=#{updateTime} WHERE id = #{id}")
    void update(User user);

    /**
     * @Description: Updates the user's avatar URL
     * @Param: avatarUrl - URL of the new avatar; id - ID of the user
     * @return: void
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @Update("UPDATE user SET user_pic=#{avatarUrl}, update_time=NOW() WHERE id=#{id}")
    void updataAvatar(String avatarUrl, Integer id);

    /**
     * @Description: Updates the user's password
     * @Param: md5String - New password in MD5 format; id - ID of the user
     * @return: void
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @Update("UPDATE user SET password=#{md5String}, update_time=NOW() WHERE id=#{id}")
    void updatePwd(String md5String, Integer id);
}
