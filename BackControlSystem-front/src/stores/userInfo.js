import { defineStore } from "pinia";
import { ref } from "vue";

/**
 * @Description: Pinia store for managing user information, with methods to set and remove user data. Configured to persist data.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 */
export const useUserInfoStore = defineStore(
  "userInfo",
  () => {
    const info = ref({}); // Reactive reference to hold user information as an object

    /**
     * @Description: Sets new user information.
     * @Param: newInfo - The new user information to store
     * @return: void
     */
    const setInfo = (newInfo) => {
      info.value = newInfo;
    };

    /**
     * @Description: Clears the current user information.
     * @return: void
     */
    const removeInfo = () => {
      info.value = {};
    };

    return { info, setInfo, removeInfo };
  },
  {
    persist: true, // Enables persistent storage to retain user information across page reloads
  }
);
