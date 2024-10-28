package com.maojie.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.maojie.mapper.ArticleMapper;
import com.maojie.pojo.Article;
import com.maojie.pojo.PageBean;
import com.maojie.service.ArticleService;
import com.maojie.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * @Description: Adds a new article to the database, setting the creation and update timestamps and the user ID of the creator.
     * @Param: article - Article object containing details to be added
     * @return: void
     */
    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);
        articleMapper.add(article);
    }

    /**
     * @Description: Retrieves a paginated list of articles filtered by category ID and state, along with total item count.
     * @Param: pageNum - Current page number; pageSize - Number of items per page; categoryId - Optional category filter; state - Optional state filter
     * @return: PageBean<Article> - Paginated list of articles with total count
     */
    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        PageBean<Article> pb = new PageBean<>();

        PageHelper.startPage(pageNum, pageSize);

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.list(userId, categoryId, state);
        Page<Article> p = (Page<Article>) as;
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        return pb;
    }

    /**
     * @Description: Updates an existing article, setting the update timestamp before saving changes to the database.
     * @Param: article - Article object with updated details
     * @return: void
     */
    @Override
    public void update(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

    /**
     * @Description: Finds an article by its ID.
     * @Param: id - ID of the article to retrieve
     * @return: Article - The article object corresponding to the specified ID
     */
    @Override
    public Article findById(Integer id) {
        return articleMapper.findById(id);
    }

    /**
     * @Description: Deletes an article by its ID.
     * @Param: id - ID of the article to delete
     * @return: void
     */
    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }
}
