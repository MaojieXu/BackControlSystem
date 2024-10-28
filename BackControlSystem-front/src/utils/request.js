// Importing required modules and components
import { ElMessage } from "element-plus"; // Message component for displaying notifications
import axios from "axios"; // Axios for HTTP requests
import { useTokenStore } from "../stores/token"; // Pinia store to manage token
import router from "@/router"; // Router for navigation

// Define the base URL for all HTTP requests
const baseURL = "/api";

// Create an Axios instance with the defined base URL
const instance = axios.create({ baseURL });

/**
 * @Description: Request interceptor to add Authorization token to headers if available.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 */
instance.interceptors.request.use(
  (config) => {
    const tokenStore = useTokenStore();
    if (tokenStore.token) {
      config.headers.Authorization = tokenStore.token; // Add token to request headers
    }
    return config;
  },
  (err) => {
    return Promise.reject(err); // Reject the promise if the request fails
  }
);

/**
 * @Description: Response interceptor to handle success and error responses.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 */
instance.interceptors.response.use(
  (result) => {
    if (result.data.code === 0) {
      return result.data; // Return data if the business code indicates success
    }
    // Display error message if the business code indicates failure
    ElMessage.error(result.data.msg ? result.data.msg : "Business error");
    return Promise.reject(result.data); // Reject the promise for error status
  },
  (err) => {
    if (err.response.status === 401) {
      ElMessage.error("Please log in first"); // Notify user to log in if unauthorized
      router.push("/login"); // Redirect to the login page
    } else {
      ElMessage.error("Server error"); // Notify of server error for other statuses
    }
    return Promise.reject(err); // Reject the promise to propagate the error
  }
);

export default instance; // Export the configured Axios instance
