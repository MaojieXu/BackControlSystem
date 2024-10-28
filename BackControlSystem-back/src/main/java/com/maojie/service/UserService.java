package com.maojie.service;

import com.maojie.pojo.User;

/**
 * @Description: Service interface for managing user operations, including finding, registering, updating, and managing passwords and avatars.
 */
public interface UserService {

    /**
     * @Description: Finds a user by their username.
     * @Param: username - The username of the user to retrieve
     * @return: User - User object corresponding to the specified username
     */
    User findByUserName(String username);

    /**
     * @Description: Registers a new user by saving the username and password.
     * @Param: username - Username for the new user; password - Password for the new user
     * @return: void
     */
    void register(String username, String password);

    /**
     * @Description: Updates an existing user's profile information.
     * @Param: user - User object containing updated profile details
     * @return: void
     */
    void update(User user);

    /**
     * @Description: Updates the user's avatar URL.
     * @Param: avatarUrl - URL of the new avatar
     * @return: void
     */
    void updateAvatar(String avatarUrl);

    /**
     * @Description: Updates the user's password.
     * @Param: newPwd - New password for the user
     * @return: void
     */
    void updatePwd(String newPwd);
}
