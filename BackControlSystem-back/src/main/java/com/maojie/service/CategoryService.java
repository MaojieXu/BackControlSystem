package com.maojie.service;

import com.maojie.pojo.Category;

import java.util.List;

/**
 * @Description: Service interface for managing categories, providing methods to add, list, update, find, and delete categories.
 */
public interface CategoryService {

    /**
     * @Description: Adds a new category to the system.
     * @Param: category - Category object containing details of the category to be added
     * @return: void
     */
    void add(Category category);

    /**
     * @Description: Retrieves a list of categories created by the current user.
     * @Param: None
     * @return: List<Category> - A list of categories
     */
    List<Category> list();

    /**
     * @Description: Finds a category by its ID.
     * @Param: id - ID of the category to retrieve
     * @return: Category - The category object corresponding to the specified ID
     */
    Category findById(Integer id);

    /**
     * @Description: Updates an existing category with new details.
     * @Param: category - Category object containing updated details
     * @return: void
     */
    void update(Category category);

    /**
     * @Description: Deletes a category by its ID.
     * @Param: id - ID of the category to delete
     * @return: void
     */
    void delete(Integer id);
}
