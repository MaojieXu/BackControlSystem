package com.maojie.service;

import com.maojie.pojo.Article;
import com.maojie.pojo.PageBean;

/**
 * @Description: Service interface for managing articles, providing methods to add, list, update, find, and delete articles.
 */
public interface ArticleService {

    /**
     * @Description: Adds a new article to the system.
     * @Param: article - Article object containing details of the article to be added
     * @return: void
     */
    void add(Article article);

    /**
     * @Description: Retrieves a paginated list of articles filtered by category ID and state.
     * @Param: pageNum - Current page number; pageSize - Number of items per page; categoryId - Optional category filter; state - Optional state filter
     * @return: PageBean<Article> - Paginated list of articles with total item count
     */
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    /**
     * @Description: Updates an existing article with new details.
     * @Param: article - Article object containing updated details
     * @return: void
     */
    void update(Article article);

    /**
     * @Description: Finds an article by its ID.
     * @Param: id - ID of the article to retrieve
     * @return: Article - The article object corresponding to the specified ID
     */
    Article findById(Integer id);

    /**
     * @Description: Deletes an article by its ID.
     * @Param: id - ID of the article to delete
     * @return: void
     */
    void delete(Integer id);
}
