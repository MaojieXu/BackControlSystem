<script setup>
// Import required modules and stores
import { ref } from "vue";
import { useUserInfoStore } from "@/stores/userInfo.js";
import { userInfoUpdateService } from "@/api/user.js";
import { ElMessage } from "element-plus";

// User info store for accessing and updating user information
const userInfoStore = useUserInfoStore();

// Clone user info into a reactive reference for the form
const userInfo = ref({ ...userInfoStore.info });

// Validation rules for the user form
const rules = {
  nickname: [
    { required: true, message: "Please enter a nickname", trigger: "blur" },
    {
      pattern: /^\S{2,10}$/,
      message: "Nickname must be a non-empty string of 2-10 characters",
      trigger: "blur",
    },
  ],
  email: [
    {
      required: true,
      message: "Please enter an email address",
      trigger: "blur",
    },
    { type: "email", message: "Invalid email format", trigger: "blur" },
  ],
};

/**
 * @Description: Updates user information and displays a success message
 * @Author: Maojie Xu
 * @return: void
 */
const updateUserInfo = async () => {
  let result = await userInfoUpdateService(userInfo.value);
  ElMessage.success(result.msg ? result.msg : "Update successful");

  // Update user info in the store
  userInfoStore.setInfo(userInfo.value);
};
</script>

<template>
  <el-card class="page-container">
    <!-- Card Header -->
    <template #header>
      <div class="header">
        <span>Basic Information</span>
      </div>
    </template>

    <el-row>
      <el-col :span="12">
        <!-- User Information Form -->
        <el-form
          :model="userInfo"
          :rules="rules"
          label-width="100px"
          size="large"
        >
          <el-form-item label="Username">
            <el-input v-model="userInfo.username" disabled></el-input>
          </el-form-item>
          <el-form-item label="Nickname" prop="nickname">
            <el-input v-model="userInfo.nickname"></el-input>
          </el-form-item>
          <el-form-item label="Email" prop="email">
            <el-input v-model="userInfo.email"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updateUserInfo">
              Submit Changes
            </el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>
