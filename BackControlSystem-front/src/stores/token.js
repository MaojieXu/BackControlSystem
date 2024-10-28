import { defineStore } from "pinia";
import { ref } from "vue";

/**
 * @Description: Pinia store for managing the user token, with methods to set and remove the token. Configured to persist data.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 */
export const useTokenStore = defineStore(
  "token",
  () => {
    const token = ref(""); // Reactive reference to hold the token value

    /**
     * @Description: Sets a new token value.
     * @Param: newToken - The new token to store
     * @return: void
     */
    const setToken = (newToken) => {
      token.value = newToken;
    };

    /**
     * @Description: Clears the current token value.
     * @return: void
     */
    const removeToken = () => {
      token.value = "";
    };

    return {
      token,
      setToken,
      removeToken,
    };
  },
  {
    persist: true, // Enables persistence for the store to retain data across page reloads
  }
);
