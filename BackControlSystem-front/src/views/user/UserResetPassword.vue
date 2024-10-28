<script setup>
// Import required modules and components
import { ref } from "vue";
import { ElMessage } from "element-plus";
import { userRestPasswordService } from "@/api/user.js";

// Form data model for password reset
const passwordForm = ref({
  oldPassword: "",
  newPassword: "",
  rePassword: "",
});

// State to control submit button loading status
const isSubmitting = ref(false);

// Validation rules for password form fields
const rules = {
  oldPassword: [
    {
      required: true,
      message: "Please enter your current password",
      trigger: "blur",
    },
  ],
  newPassword: [
    { required: true, message: "Please enter a new password", trigger: "blur" },
    {
      min: 5,
      max: 16,
      message: "Password length must be between 5-16 characters",
      trigger: "blur",
    },
  ],
  rePassword: [
    {
      required: true,
      message: "Please re-enter your password",
      trigger: "blur",
    },
    {
      validator: (rule, value, callback) => {
        if (value !== passwordForm.value.newPassword) {
          callback(new Error("The passwords do not match"));
        } else {
          callback();
        }
      },
      trigger: "blur",
    },
  ],
};

/**
 * @Description: Submits the password reset request to the server
 * @Author: Maojie Xu
 * @return: void
 */
const resetPassword = async () => {
  let result = await userRestPasswordService({
    old_pwd: passwordForm.value.oldPassword,
    new_pwd: passwordForm.value.newPassword,
    re_pwd: passwordForm.value.rePassword,
  });
  ElMessage.success(result.msg ? result.msg : "Password updated successfully");
};
</script>

<template>
  <el-card class="page-container">
    <!-- Card Header -->
    <template #header>
      <div class="header">Reset Password</div>
    </template>

    <el-row>
      <el-col :span="12">
        <!-- Password Reset Form -->
        <el-form
          :model="passwordForm"
          :rules="rules"
          label-width="100px"
          size="large"
        >
          <!-- Current Password -->
          <el-form-item label="Current Password" prop="oldPassword">
            <el-input
              v-model="passwordForm.oldPassword"
              type="password"
              placeholder="Enter your current password"
            ></el-input>
          </el-form-item>

          <!-- New Password -->
          <el-form-item label="New Password" prop="newPassword">
            <el-input
              v-model="passwordForm.newPassword"
              type="password"
              placeholder="Enter a new password"
            ></el-input>
          </el-form-item>

          <!-- Confirm New Password -->
          <el-form-item label="Confirm New Password" prop="rePassword">
            <el-input
              v-model="passwordForm.rePassword"
              type="password"
              placeholder="Re-enter the new password"
            ></el-input>
          </el-form-item>

          <!-- Submit Button -->
          <el-form-item>
            <el-button
              type="primary"
              :loading="isSubmitting"
              @click="resetPassword"
            >
              Submit Changes
            </el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>
