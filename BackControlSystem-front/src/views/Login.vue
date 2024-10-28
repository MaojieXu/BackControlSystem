<script setup>
// Import required icons and modules
import { User, Lock } from "@element-plus/icons-vue";
import { ref } from "vue";
import { ElMessage } from "element-plus";
import { userRegisterService, userLoginService } from "@/api/user.js";
import { useRouter } from "vue-router";
import { useTokenStore } from "../stores/token";

// State to control the display of the form (default to login)
const isRegister = ref(false);

// Form data model for registration and login
const registerData = ref({
  username: "",
  password: "",
  rePassword: "",
});

// Validator to ensure repeated password matches
const checkRePassword = (rule, value, callback) => {
  if (value === "") {
    callback(new Error("Please re-enter the password"));
  } else if (value !== registerData.value.password) {
    callback(new Error("Passwords do not match"));
  } else {
    callback();
  }
};

// Validation rules for the form fields
const rules = {
  username: [
    { required: true, message: "Please enter a username", trigger: "blur" },
    {
      min: 5,
      max: 16,
      message: "Length must be between 5-16 characters",
      trigger: "blur",
    },
  ],
  password: [
    { required: true, message: "Please enter a password", trigger: "blur" },
    {
      min: 5,
      max: 16,
      message: "Length must be between 5-16 characters",
      trigger: "blur",
    },
  ],
  rePassword: [{ validator: checkRePassword, trigger: "blur" }],
};

/**
 * @Description: Submits registration data to the API
 * @Author: Maojie Xu
 * @return: void
 */
const register = async () => {
  let result = await userRegisterService(registerData.value);
  ElMessage.success(result.msg ? result.msg : "Registration successful");
};

// Router instance and token store for login functionality
const router = useRouter();
const tokenStore = useTokenStore();

/**
 * @Description: Submits login data to the API and stores token upon success
 * @Author: Maojie Xu
 * @return: void
 */
const login = async () => {
  let result = await userLoginService(registerData.value);
  ElMessage.success(result.msg ? result.msg : "Login successful");
  tokenStore.setToken(result.data);
  router.push("/");
};

/**
 * @Description: Clears form data for both login and registration
 * @Author: Maojie Xu
 * @return: void
 */
const clearRegisterData = () => {
  registerData.value = {
    username: "",
    password: "",
    rePassword: "",
  };
};
</script>

<template>
  <el-row class="login-page">
    <el-col :span="12" class="bg"></el-col>
    <el-col :span="6" :offset="3" class="form">
      <!-- Registration Form -->
      <el-form
        ref="form"
        size="large"
        autocomplete="off"
        v-if="isRegister"
        :model="registerData"
        :rules="rules"
      >
        <el-form-item>
          <h1>Register</h1>
        </el-form-item>
        <el-form-item prop="username">
          <el-input
            :prefix-icon="User"
            placeholder="Enter username"
            v-model="registerData.username"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            :prefix-icon="Lock"
            type="password"
            placeholder="Enter password"
            v-model="registerData.password"
          ></el-input>
        </el-form-item>
        <el-form-item prop="rePassword">
          <el-input
            :prefix-icon="Lock"
            type="password"
            placeholder="Re-enter password"
            v-model="registerData.rePassword"
          ></el-input>
        </el-form-item>
        <!-- Register Button -->
        <el-form-item>
          <el-button
            class="button"
            type="primary"
            auto-insert-space
            @click="register"
          >
            Register
          </el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link
            type="info"
            :underline="false"
            @click="
              isRegister = false;
              clearRegisterData();
            "
          >
            ← Back to Login
          </el-link>
        </el-form-item>
      </el-form>

      <!-- Login Form -->
      <el-form
        ref="form"
        size="large"
        autocomplete="off"
        v-else
        :model="registerData"
        :rules="rules"
      >
        <el-form-item>
          <h1>Login</h1>
        </el-form-item>
        <el-form-item prop="username">
          <el-input
            :prefix-icon="User"
            placeholder="Enter username"
            v-model="registerData.username"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            name="password"
            :prefix-icon="Lock"
            type="password"
            placeholder="Enter password"
            v-model="registerData.password"
          ></el-input>
        </el-form-item>
        <el-form-item class="flex">
          <div class="flex">
            <el-checkbox>Remember me</el-checkbox>
            <el-link type="primary" :underline="false"
              >Forgot password?</el-link
            >
          </div>
        </el-form-item>
        <!-- Login Button -->
        <el-form-item>
          <el-button
            class="button"
            type="primary"
            auto-insert-space
            @click="login"
          >
            Login
          </el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link
            type="info"
            :underline="false"
            @click="
              isRegister = true;
              clearRegisterData();
            "
          >
            Register →
          </el-link>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
/* Page and form styling */
.login-page {
  height: 100vh;
  background-color: #fff;

  .bg {
    background: url("@/assets/logo2.png") no-repeat 60% center / 240px auto,
      url("@/assets/login_bg.jpg") no-repeat center / cover;
    border-radius: 0 20px 20px 0;
  }

  .form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;

    .title {
      margin: 0 auto;
    }

    .button {
      width: 100%;
    }

    .flex {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
</style>
