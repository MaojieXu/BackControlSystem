<script setup>
// Import required modules and components
import { Edit, Delete, Plus } from "@element-plus/icons-vue";
import { ref } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
import { useTokenStore } from "@/stores/token.js";

// Import article API services
import {
  articleCategoryListService,
  articleListService,
  articleAddService,
  articleDeleteService,
  articleUpdateService,
} from "@/api/article.js";

// Data models
const categorys = ref([]); // Holds article categories
const categoryId = ref(""); // Selected category ID for search
const state = ref(""); // Selected publish state for search
const articles = ref([]); // Holds list of articles
const pageNum = ref(1); // Current page number
const total = ref(20); // Total number of articles
const pageSize = ref(3); // Number of articles per page

// Control visibility of add/edit drawer
const visibleDrawer = ref(false);

// Model for article data in the form
const articleModel = ref({
  title: "",
  categoryId: "",
  coverImg: "",
  content: "",
  state: "",
});

// Store instance for managing token
const tokenStore = useTokenStore();

/**
 * @Description: Fetches and populates the list of article categories
 * @Author: Maojie Xu
 * @return: void
 */
const articleCategoryList = async () => {
  let result = await articleCategoryListService();
  categorys.value = result.data;
};

/**
 * @Description: Fetches and populates the list of articles with pagination and filter options
 * @Author: Maojie Xu
 * @return: void
 */
const articleList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    categoryId: categoryId.value ? categoryId.value : null,
    state: state.value ? state.value : null,
  };
  let result = await articleListService(params);
  total.value = result.data.total;
  articles.value = result.data.items;

  // Map category names to articles based on categoryId
  for (let article of articles.value) {
    for (let category of categorys.value) {
      if (article.categoryId == category.id) {
        article.categoryName = category.categoryName;
      }
    }
  }
};

/**
 * @Description: Handles page size change in pagination
 * @Author: Maojie Xu
 * @Param: size - New page size
 * @return: void
 */
const onSizeChange = (size) => {
  pageSize.value = size;
  articleList();
};

/**
 * @Description: Handles current page change in pagination
 * @Author: Maojie Xu
 * @Param: num - New page number
 * @return: void
 */
const onCurrentChange = (num) => {
  pageNum.value = num;
  articleList();
};

/**
 * @Description: Handles successful file upload and sets the cover image URL
 * @Author: Maojie Xu
 * @Param: result - The response from the upload service
 * @return: void
 */
const uploadSuccess = (result) => {
  articleModel.value.coverImg = result.data;
};

/**
 * @Description: Adds a new article or saves it as a draft
 * @Author: Maojie Xu
 * @Param: clickState - The state of the article ("Published" or "Draft")
 * @return: void
 */
const addArticle = async (clickState) => {
  articleModel.value.state = clickState;
  let result = await articleAddService(articleModel.value);
  ElMessage.success(result.msg ? result.msg : "Added successfully");
  visibleDrawer.value = false;
  articleList();
};

/**
 * @Description: Updates an existing article or saves it as a draft
 * @Author: Maojie Xu
 * @Param: clickState - The state of the article ("Published" or "Draft")
 * @return: void
 */
const updateArticle = async (clickState) => {
  articleModel.value.state = clickState;
  let result = await articleUpdateService(articleModel.value);
  ElMessage.success(result.msg ? result.msg : "Updated successfully");
  articleList();
  visibleDrawer.value = false;
  clearData();
};

/**
 * @Description: Clears form data for new article
 * @Author: Maojie Xu
 * @return: void
 */
const clearData = () => {
  articleModel.value.title = "";
  articleModel.value.categoryId = "";
  articleModel.value.coverImg = "";
  articleModel.value.content = "";
  articleModel.value.state = "";
};

/**
 * @Description: Shows the edit dialog with pre-filled data for the selected article
 * @Author: Maojie Xu
 * @Param: row - The article data to edit
 * @return: void
 */
const showDialog = (row) => {
  visibleDrawer.value = true;
  title.value = "Edit Article";
  articleModel.value = { ...row };
};

/**
 * @Description: Deletes a selected article after user confirmation
 * @Author: Maojie Xu
 * @Param: row - The article data to delete
 * @return: void
 */
const deleteArticle = async (row) => {
  ElMessageBox.confirm("Are you sure you want to delete?", "Warning", {
    confirmButtonText: "OK",
    cancelButtonText: "Cancel",
    type: "warning",
  })
    .then(async () => {
      let result = await articleDeleteService(row.id);
      ElMessage({ type: "success", message: "Delete completed" });
      articleList();
      clearData();
    })
    .catch(() => {
      ElMessage({ type: "info", message: "Delete canceled" });
    });
};

// Initial fetch of categories and articles
articleCategoryList();
articleList();
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>Article Management</span>
        <div class="extra">
          <el-button
            type="primary"
            @click="
              title = 'Add Article';
              visibleDrawer = true;
              clearData();
            "
            >Add Article</el-button
          >
        </div>
      </div>
    </template>
    <!-- Search Form -->
    <el-form inline>
      <el-form-item label="Article Category:">
        <el-select v-model="categoryId" placeholder="Please select">
          <el-option
            v-for="c in categorys"
            :key="c.id"
            :label="c.categoryName"
            :value="c.id"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="Publish State:">
        <el-select placeholder="Please select" v-model="state">
          <el-option label="Published" value="Published"></el-option>
          <el-option label="Draft" value="Draft"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="articleList">Search</el-button>
        <el-button
          @click="
            categoryId = '';
            state = '';
            articleList();
          "
        >
          Reset
        </el-button>
      </el-form-item>
    </el-form>

    <!-- Article List -->
    <el-table :data="articles" style="width: 100%">
      <el-table-column
        label="Article Title"
        width="400"
        prop="title"
      ></el-table-column>
      <el-table-column label="Category" prop="categoryName"></el-table-column>
      <el-table-column label="Publish Date" prop="createTime"></el-table-column>
      <el-table-column label="State" prop="state"></el-table-column>
      <el-table-column label="Actions" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="showDialog(row)"
          ></el-button>
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="deleteArticle(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="No data available" />
      </template>
    </el-table>

    <!-- Pagination -->
    <el-pagination
      v-model:current-page="pageNum"
      v-model:page-size="pageSize"
      :page-sizes="[3, 5, 10, 15]"
      layout="jumper, total, sizes, prev, pager, next"
      background
      :total="total"
      @size-change="onSizeChange"
      @current-change="onCurrentChange"
      style="margin-top: 20px; display: flex; justify-content: center"
    />

    <!-- Add/Edit Article Drawer -->
    <el-drawer
      v-model="visibleDrawer"
      :title="title"
      direction="rtl"
      size="50%"
    >
      <el-form :model="articleModel" label-width="100px">
        <el-form-item label="Article Title">
          <el-input
            v-model="articleModel.title"
            placeholder="Enter title"
          ></el-input>
        </el-form-item>
        <el-form-item label="Article Category">
          <el-select
            placeholder="Please select"
            v-model="articleModel.categoryId"
          >
            <el-option
              v-for="c in categorys"
              :key="c.id"
              :label="c.categoryName"
              :value="c.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Cover Image">
          <el-upload
            class="avatar-uploader"
            :auto-upload="true"
            :show-file-list="false"
            action="/api/upload"
            name="file"
            :headers="{ Authorization: tokenStore.token }"
            :on-success="uploadSuccess"
          >
            <img
              v-if="articleModel.coverImg"
              :src="articleModel.coverImg"
              class="avatar"
            />
            <el-icon v-else class="avatar-uploader-icon">
              <Plus />
            </el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="Content">
          <div class="editor">
            <quill-editor
              theme="snow"
              v-model:content="articleModel.content"
              contentType="html"
            ></quill-editor>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="
              title == 'Add Article'
                ? addArticle('Published')
                : updateArticle('Published')
            "
            >Publish</el-button
          >
          <el-button
            type="info"
            @click="
              title == 'Add Article'
                ? addArticle('Draft')
                : updateArticle('Draft')
            "
            >Save as Draft</el-button
          >
        </el-form-item>
      </el-form>
    </el-drawer>
  </el-card>
</template>

<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
.avatar-uploader {
  :deep() {
    .avatar {
      width: 178px;
      height: 178px;
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
      width: 178px;
      height: 178px;
      text-align: center;
    }
  }
}
.editor {
  width: 100%;
  :deep(.ql-editor) {
    min-height: 200px;
  }
}
</style>
