import request from "@/utils/request.js";

/**
 * @Description: Service to register a new user by sending registration data.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 * @Param: registerData - Object containing registration details (e.g., username and password)
 * @return: Promise - API response after registration
 */
export const userRegisterService = (registerData) => {
  const params = new URLSearchParams();
  for (let key in registerData) {
    params.append(key, registerData[key]);
  }
  return request.post("/user/register", params);
};

/**
 * @Description: Service to log in a user by sending login credentials.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 * @Param: loginData - Object containing login details (e.g., username and password)
 * @return: Promise - API response after login
 */
export const userLoginService = (loginData) => {
  const params = new URLSearchParams();
  for (let key in loginData) {
    params.append(key, loginData[key]);
  }
  return request.post("/user/login", params);
};

/**
 * @Description: Service to retrieve the current user's information.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 * @return: Promise - API response containing user information
 */
export const userInfoService = () => {
  return request.get("/user/userInfo");
};

/**
 * @Description: Service to update the current user's profile information.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 * @Param: userInfoData - Object containing updated user profile data
 * @return: Promise - API response after updating user information
 */
export const userInfoUpdateService = (userInfoData) => {
  return request.put("/user/update", userInfoData);
};

/**
 * @Description: Service to update the user's avatar by sending a new avatar URL.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 * @Param: avatarUrl - URL of the new avatar
 * @return: Promise - API response after updating the avatar
 */
export const userAvatarUpdateService = (avatarUrl) => {
  const params = new URLSearchParams();
  params.append("avatarUrl", avatarUrl);
  return request.patch("/user/updateAvatar", params);
};

/**
 * @Description: Service to reset the user's password by sending new password data.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 * @Param: passwordData - Object containing old and new password information
 * @return: Promise - API response after resetting the password
 */
export const userRestPasswordService = (passwordData) => {
  return request.patch("/user/updatePwd", passwordData);
};
