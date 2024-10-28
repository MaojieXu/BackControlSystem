package com.maojie.service.impl;

import com.maojie.mapper.CategoryMapper;
import com.maojie.pojo.Category;
import com.maojie.service.CategoryService;
import com.maojie.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * @Description: Adds a new category to the database, setting the creation and update timestamps and the user ID of the creator.
     * @Param: category - Category object containing details to be added
     * @return: void
     */
    @Override
    public void add(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        category.setCreateUser(userId);
        categoryMapper.add(category);
    }

    /**
     * @Description: Retrieves a list of categories created by the current user.
     * @Param: None
     * @return: List<Category> - A list of categories created by the logged-in user
     */
    @Override
    public List<Category> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return categoryMapper.list(userId);
    }

    /**
     * @Description: Finds a category by its ID.
     * @Param: id - ID of the category to retrieve
     * @return: Category - The category object corresponding to the specified ID
     */
    @Override
    public Category findById(Integer id) {
        return categoryMapper.findByid(id);
    }

    /**
     * @Description: Updates an existing category, setting the update timestamp before saving changes to the database.
     * @Param: category - Category object with updated details
     * @return: void
     */
    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    /**
     * @Description: Deletes a category by its ID.
     * @Param: id - ID of the category to delete
     * @return: void
     */
    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }
}
