import request from "@/utils/request.js";
import { useTokenStore } from "../stores/token";

/**
 * @Description: Service to fetch the list of article categories.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 * @return: Promise - API response containing the list of categories
 */
export const articleCategoryListService = () => {
  return request.get("/category");
};

/**
 * @Description: Service to add a new article category.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 * @Param: categoryData - Object containing category details
 * @return: Promise - API response after adding the category
 */
export const articleCategoryAddService = (categoryData) => {
  return request.post("/category", categoryData);
};

/**
 * @Description: Service to update an existing article category.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 * @Param: categoryData - Object containing updated category details
 * @return: Promise - API response after updating the category
 */
export const articleCategoryUpdateService = (categoryData) => {
  return request.put("/category", categoryData);
};

/**
 * @Description: Service to delete an article category by ID.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 * @Param: id - ID of the category to delete
 * @return: Promise - API response after deleting the category
 */
export const articleCategoryDeleteService = (id) => {
  return request.delete("/category?id=" + id);
};

/**
 * @Description: Service to fetch the list of articles based on filters.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 * @Param: params - Object containing filter parameters for the request
 * @return: Promise - API response containing the list of articles
 */
export const articleListService = (params) => {
  return request.get("/article", { params: params });
};

/**
 * @Description: Service to add a new article.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 * @Param: articleData - Object containing article details
 * @return: Promise - API response after adding the article
 */
export const articleAddService = (articleData) => {
  return request.post("/article", articleData);
};

/**
 * @Description: Service to delete an article by ID.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 * @Param: id - ID of the article to delete
 * @return: Promise - API response after deleting the article
 */
export const articleDeleteService = (id) => {
  return request.delete("/article?id=" + id);
};

/**
 * @Description: Service to update an existing article.
 * @Author: Maojie Xu
 * @Date: 10/24/2024
 * @Param: categoryData - Object containing updated article details
 * @return: Promise - API response after updating the article
 */
export const articleUpdateService = (categoryData) => {
  return request.put("/article", categoryData);
};
