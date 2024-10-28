import { createRouter, createWebHistory } from "vue-router";

import LoginVue from "@/views/Login.vue";
import LayoutVue from "@/views/Layout.vue";
import ArticleCategoryVue from "@/views/article/ArticleCategory.vue";
import ArticleManageVue from "@/views/article/ArticleManage.vue";
import UserAvatarVue from "@/views/user/UserAvatar.vue";
import UserInfoVue from "@/views/user/UserInfo.vue";
import UserResetPasswordVue from "@/views/user/UserResetPassword.vue";

/**
 * @Description: Route configuration array defining paths and components for different views.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 */
const routes = [
  { path: "/login", component: LoginVue }, // Login page route
  {
    path: "/",
    component: LayoutVue, // Main layout component
    redirect: "/article/manage", // Default redirect to article management
    children: [
      { path: "article/category", component: ArticleCategoryVue }, // Article category management
      { path: "article/manage", component: ArticleManageVue }, // Article management
      { path: "user/avatar", component: UserAvatarVue }, // User avatar management
      { path: "user/info", component: UserInfoVue }, // User info management
      { path: "user/resetPassword", component: UserResetPasswordVue }, // User password reset
    ],
  },
];

/**
 * @Description: Creates and exports a Vue Router instance with history mode and defined routes.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 */
const router = createRouter({
  history: createWebHistory(), // Uses HTML5 history mode
  routes: routes,
});

export default router;
