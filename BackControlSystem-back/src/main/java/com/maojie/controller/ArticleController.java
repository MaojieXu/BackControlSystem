package com.maojie.controller;

import com.maojie.pojo.Article;
import com.maojie.pojo.PageBean;
import com.maojie.pojo.Result;
import com.maojie.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * @Description: Adds a new article to the system
     * @Param: article - Article object containing article details
     * @return: Result - Success message after adding the article
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @PostMapping
    public Result add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success();
    }

    /**
     * @Description: Updates an existing article in the system
     * @Param: article - Article object with updated article information
     * @return: Result - Success message after updating the article
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @PutMapping
    public Result update(@RequestBody @Validated Article article) {
        articleService.update(article);
        return Result.success("Article update successful");
    }

    /**
     * @Description: Retrieves the details of a specific article by its ID
     * @Param: id - ID of the article to retrieve
     * @return: Result<Article> - Success message with the article's details
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @GetMapping("/detail")
    public Result<Article> detail(Integer id) {
        Article a = articleService.findById(id);
        return Result.success(a);
    }

    /**
     * @Description: Deletes an article by its ID
     * @Param: id - ID of the article to delete
     * @return: Result - Success message after deletion
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @DeleteMapping
    public Result delete(Integer id) {
        articleService.delete(id);
        return Result.success();
    }

    /**
     * @Description: Retrieves a paginated list of articles with optional filters for category and state
     * @Param: pageNum - Current page number, pageSize - Number of articles per page, categoryId - Optional category ID filter, state - Optional state filter
     * @return: Result<PageBean<Article>> - Success message with the paginated list of articles
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum, Integer pageSize,
                                          @RequestParam(required = false) Integer categoryId,
                                          @RequestParam(required = false) String state) {
        PageBean<Article> pb = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pb);
    }
}
