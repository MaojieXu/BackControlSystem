<script setup>
// Import necessary icons, stores, and functions
import {
  Management,
  Promotion,
  UserFilled,
  User,
  Crop,
  EditPen,
  SwitchButton,
  CaretBottom,
} from "@element-plus/icons-vue";
import avatar from "@/assets/default.png";
import { useUserInfoStore } from "@/stores/userInfo.js";
import { userInfoService } from "@/api/user.js";
import { useTokenStore } from "@/stores/token.js";
import { ElMessageBox, ElMessage } from "element-plus";
import { useRouter } from "vue-router";

// Token and user info stores
const tokenStore = useTokenStore();
const userInfoStore = useUserInfoStore();

/**
 * @Description: Fetches and stores user information
 * @Author: Maojie Xu
 * @return: void
 */
const getUserInfo = async () => {
  let result = await userInfoService();
  userInfoStore.setInfo(result.data);
};

// Initialize user info on component load
getUserInfo();

const router = useRouter();

/**
 * @Description: Handles commands from the dropdown menu, such as logout and navigation
 * @Author: Maojie Xu
 * @Param: command - The command string, either navigation or logout
 * @return: void
 */
const handleCommand = (command) => {
  if (command === "logout") {
    ElMessageBox.confirm("Are you sure you want to log out?", "Warning", {
      confirmButtonText: "OK",
      cancelButtonText: "Cancel",
      type: "warning",
    })
      .then(async () => {
        tokenStore.removeToken();
        userInfoStore.removeInfo();
        await router.push("/login");
        ElMessage({ type: "success", message: "Logged out successfully" });
      })
      .catch(() => {
        ElMessage({
          type: "info",
          message: "Log out canceled",
        });
      });
  } else {
    router.push("/user/" + command);
  }
};
</script>

<template>
  <el-container class="layout-container">
    <!-- Left-side Menu -->
    <el-aside width="200px">
      <div class="el-aside__logo"></div>
      <el-menu
        active-text-color="#ffd04b"
        background-color="#232323"
        text-color="#fff"
        router
      >
        <el-menu-item index="/article/category">
          <el-icon>
            <Management />
          </el-icon>
          <span>Article Categories</span>
        </el-menu-item>
        <el-menu-item index="/article/manage">
          <el-icon>
            <Promotion />
          </el-icon>
          <span>Article Management</span>
        </el-menu-item>
        <el-sub-menu>
          <template #title>
            <el-icon>
              <UserFilled />
            </el-icon>
            <span>Personal Center</span>
          </template>
          <el-menu-item index="/user/info">
            <el-icon>
              <User />
            </el-icon>
            <span>Basic Information</span>
          </el-menu-item>
          <el-menu-item index="/user/avatar">
            <el-icon>
              <Crop />
            </el-icon>
            <span>Change Avatar</span>
          </el-menu-item>
          <el-menu-item index="/user/resetPassword">
            <el-icon>
              <EditPen />
            </el-icon>
            <span>Reset Password</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <!-- Right-side Main Area -->
    <el-container>
      <!-- Header Area -->
      <el-header>
        <div>
          Manager: <strong>{{ userInfoStore.info.nickname }}</strong>
        </div>
        <el-dropdown placement="bottom-end" @command="handleCommand">
          <span class="el-dropdown__box">
            <el-avatar
              :src="
                userInfoStore.info.userPic ? userInfoStore.info.userPic : avatar
              "
            />
            <el-icon>
              <CaretBottom />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="info" :icon="User"
                >Basic Information</el-dropdown-item
              >
              <el-dropdown-item command="avatar" :icon="Crop"
                >Change Avatar</el-dropdown-item
              >
              <el-dropdown-item command="resetPassword" :icon="EditPen"
                >Reset Password</el-dropdown-item
              >
              <el-dropdown-item command="logout" :icon="SwitchButton"
                >Log Out</el-dropdown-item
              >
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>

      <!-- Main Content Area -->
      <el-main>
        <router-view></router-view>
      </el-main>

      <!-- Footer Area -->
      <el-footer>BackControlSystem ©2024 Created by Maojie Xu</el-footer>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;

  .el-aside {
    background-color: #232323;

    &__logo {
      height: 120px;
      background: url("@/assets/logo.png") no-repeat center / 190px auto;
    }

    .el-menu {
      border-right: none;
    }
  }

  .el-header {
    background-color: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .el-dropdown__box {
      display: flex;
      align-items: center;

      .el-icon {
        color: #999;
        margin-left: 10px;
      }

      &:active,
      &:focus {
        outline: none;
      }
    }
  }

  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
  }
}
</style>
