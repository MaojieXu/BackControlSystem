<script setup>
// Import required icons and modules
import { Plus, Upload } from "@element-plus/icons-vue";
import { ref } from "vue";
import { useTokenStore } from "../../stores/token";
import { useUserInfoStore } from "@/stores/userInfo.js";
import { ElMessage } from "element-plus";
import { userAvatarUpdateService } from "@/api/user.js";

// Token store for managing the authorization token
const tokenStore = useTokenStore();

// User info store to manage and access user data
const userInfoStore = useUserInfoStore();

// References for image URL and file upload component
const imgUrl = ref(userInfoStore.info.userPic);
const uploadRef = ref();

/**
 * @Description: Handles successful image upload and updates the avatar URL
 * @Author: Maojie Xu
 * @Param: result - The response from the upload API
 * @return: void
 */
const uploadSuccess = (result) => {
  imgUrl.value = result.data;
};

/**
 * @Description: Updates the user's avatar by saving the new URL in user info
 * @Author: Maojie Xu
 * @return: void
 */
const updateAvatar = async () => {
  let result = await userAvatarUpdateService(imgUrl.value);
  ElMessage.success(result.msg ? result.msg : "Avatar updated successfully");
  userInfoStore.info.userPic = imgUrl.value;
};
</script>

<template>
  <el-card class="page-container">
    <!-- Card Header -->
    <template #header>
      <div class="header">
        <span>Change Avatar</span>
      </div>
    </template>

    <el-row>
      <el-col :span="12">
        <!-- Upload Component -->
        <el-upload
          ref="uploadRef"
          class="avatar-uploader"
          :show-file-list="false"
          :auto-upload="true"
          name="file"
          :headers="{ Authorization: tokenStore.token }"
          :on-success="uploadSuccess"
        >
          <img v-if="imgUrl" :src="imgUrl" class="avatar" />
          <img v-else :src="avatar" width="278" />
        </el-upload>

        <br />
        <!-- Choose Image and Upload Buttons -->
        <el-button
          type="primary"
          :icon="Plus"
          size="large"
          @click="uploadRef.$el.querySelector('input').click()"
        >
          Choose Image
        </el-button>
        <el-button
          type="success"
          :icon="Upload"
          size="large"
          @click="updateAvatar"
        >
          Upload Avatar
        </el-button>
      </el-col>
    </el-row>
  </el-card>
</template>

<style lang="scss" scoped>
.avatar-uploader {
  :deep() {
    .avatar {
      width: 278px;
      height: 278px;
      display: block;
    }

    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }

    .el-upload:hover {
      border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 278px;
      height: 278px;
      text-align: center;
    }
  }
}
</style>
