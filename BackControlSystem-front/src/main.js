// Import global styles and main libraries
import "./assets/main.scss";
import { createApp } from "vue";
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import router from "@/router";
import App from "./App.vue";
import { createPinia } from "pinia";
import { createPersistedState } from "pinia-persistedstate-plugin";

// Initialize the Vue application
const app = createApp(App);

// Set up Pinia state management with persistence
const pinia = createPinia();
const persist = createPersistedState();
pinia.use(persist);

// Use plugins and components
app.use(pinia); // State management
app.use(router); // Routing
app.use(ElementPlus); // UI component library

// Mount the app to the #app element in index.html
app.mount("#app");
