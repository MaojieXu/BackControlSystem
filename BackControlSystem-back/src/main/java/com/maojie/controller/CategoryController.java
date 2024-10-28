package com.maojie.controller;

import com.maojie.pojo.Category;
import com.maojie.pojo.Result;
import com.maojie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * @Description: Adds a new category to the system
     * @Param: category - Category object with details of the category to be added
     * @return: Result - Success message after adding the category
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category) {
        categoryService.add(category);
        return Result.success();
    }

    /**
     * @Description: Retrieves a list of all categories
     * @Param: None
     * @return: Result<List<Category>> - Success message with a list of categories
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @GetMapping
    public Result<List<Category>> list() {
        List<Category> cs = categoryService.list();
        return Result.success(cs);
    }

    /**
     * @Description: Retrieves the details of a specific category by its ID
     * @Param: id - ID of the category to retrieve
     * @return: Result<Category> - Success message with the category details
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @GetMapping("/detail")
    public Result<Category> detail(Integer id) {
        Category c = categoryService.findById(id);
        return Result.success(c);
    }

    /**
     * @Description: Updates an existing category in the system
     * @Param: category - Category object with updated category information
     * @return: Result - Success message after updating the category
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category) {
        categoryService.update(category);
        return Result.success();
    }

    /**
     * @Description: Deletes a category by its ID
     * @Param: id - ID of the category to delete
     * @return: Result - Success message after deletion
     * @Author: Maojie Xu
     * @Date: 10/25/2024
     */
    @DeleteMapping
    public Result delete(Integer id) {
        categoryService.delete(id);
        return Result.success();
    }
}
