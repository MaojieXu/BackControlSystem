<script setup>
// Importing required modules and components
import { Edit, Delete } from "@element-plus/icons-vue";
import { ref } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";

// Importing article category API services
import {
  articleCategoryListService,
  articleCategoryAddService,
  articleCategoryUpdateService,
  articleCategoryDeleteService,
} from "../../api/article";

// Reactive reference to hold the list of categories
const categorys = ref([]);

/**
 * @Description: Fetches the list of article categories and assigns it to the `categorys` reference.
 * @return: void
 */
const articleCategoryList = async () => {
  let result = await articleCategoryListService();
  categorys.value = result.data;
};
articleCategoryList(); // Initial fetch of categories on component load

// Control visibility of the Add/Edit category dialog
const dialogVisible = ref(false);

// Data model for adding/editing categories
const categoryModel = ref({
  categoryName: "",
  categoryAlias: "",
});

// Validation rules for the category form
const rules = {
  categoryName: [
    {
      required: true,
      message: "Please enter the category name",
      trigger: "blur",
    },
  ],
  categoryAlias: [
    {
      required: true,
      message: "Please enter the category alias",
      trigger: "blur",
    },
  ],
};

// Reusable reference to hold dialog title (either "Add Category" or "Edit Category")
const title = ref("");

/**
 * @Description: Shows the dialog for editing an existing category, populating the form with the selected row data.
 * @Param: row - The category data to edit
 * @return: void
 */
const showDialog = (row) => {
  dialogVisible.value = true;
  title.value = "Edit Category";
  categoryModel.value.categoryName = row.categoryName;
  categoryModel.value.categoryAlias = row.categoryAlias;
  categoryModel.value.id = row.id;
};

/**
 * @Description: Adds a new category using the API and refreshes the list.
 * @return: void
 */
const addCategory = async () => {
  let result = await articleCategoryAddService(categoryModel.value);
  ElMessage.success(result.msg ? result.msg : "Category added successfully");
  articleCategoryList(); // Refreshes the category list
  dialogVisible.value = false;
};

/**
 * @Description: Updates an existing category using the API and refreshes the list.
 * @return: void
 */
const updateCategory = async () => {
  let result = await articleCategoryUpdateService(categoryModel.value);
  ElMessage.success(result.msg ? result.msg : "Category updated successfully");
  articleCategoryList(); // Refreshes the category list
  dialogVisible.value = false;
};

/**
 * @Description: Clears the category form data (used when switching between adding and editing).
 * @return: void
 */
const clearData = () => {
  categoryModel.value.categoryName = "";
  categoryModel.value.categoryAlias = "";
};

/**
 * @Description: Deletes a selected category after confirming with the user.
 * @Param: row - The category data to delete
 * @return: void
 */
const deleteCategory = async (row) => {
  ElMessageBox.confirm("Are you sure you want to delete?", "Warning", {
    confirmButtonText: "OK",
    cancelButtonText: "Cancel",
    type: "warning",
  })
    .then(async () => {
      let result = await articleCategoryDeleteService(row.id);
      ElMessage({
        type: "success",
        message: "Category deleted successfully",
      });
      articleCategoryList(); // Refreshes the category list
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "Deletion canceled",
      });
    });
};
</script>

<template>
  <el-card class="page-container">
    <!-- Card Header with title and Add Category button -->
    <template #header>
      <div class="header">
        <span>Article Categories</span>
        <div class="extra">
          <el-button
            type="primary"
            @click="
              dialogVisible = true;
              title = 'Add Category';
              clearData();
            "
          >
            Add Category
          </el-button>
        </div>
      </div>
    </template>

    <!-- Table displaying category data -->
    <el-table :data="categorys" style="width: 100%">
      <el-table-column label="Index" width="100" type="index">
      </el-table-column>
      <el-table-column
        label="Category Name"
        prop="categoryName"
      ></el-table-column>
      <el-table-column
        label="Category Alias"
        prop="categoryAlias"
      ></el-table-column>
      <el-table-column label="Actions" width="100">
        <!-- Action buttons for editing and deleting categories -->
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
            @click="deleteCategory(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="No data available" />
      </template>
    </el-table>

    <!-- Dialog for adding or editing a category -->
    <el-dialog v-model="dialogVisible" :title="title" width="30%">
      <el-form
        :model="categoryModel"
        :rules="rules"
        label-width="100px"
        style="padding-right: 30px"
      >
        <el-form-item label="Category Name" prop="categoryName">
          <el-input
            v-model="categoryModel.categoryName"
            minlength="1"
            maxlength="10"
          ></el-input>
        </el-form-item>
        <el-form-item label="Category Alias" prop="categoryAlias">
          <el-input
            v-model="categoryModel.categoryAlias"
            minlength="1"
            maxlength="15"
          ></el-input>
        </el-form-item>
      </el-form>
      <!-- Dialog footer with cancel and confirm buttons -->
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">Cancel</el-button>
          <el-button
            type="primary"
            @click="title == 'Add Category' ? addCategory() : updateCategory()"
          >
            Confirm
          </el-button>
        </span>
      </template>
    </el-dialog>
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
</style>
