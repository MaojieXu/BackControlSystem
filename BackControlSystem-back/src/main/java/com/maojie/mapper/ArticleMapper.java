package com.maojie.mapper;

import com.maojie.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    /**
     * @Description: Inserts a new article record into the database
     * @Param: article - Article object containing article details to be inserted
     * @return: void
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @Insert("INSERT INTO article (title, content, cover_img, state, category_id, create_user, create_time, update_time) " +
            "VALUES (#{title}, #{content}, #{coverImg}, #{state}, #{categoryId}, #{createUser}, #{createTime}, #{updateTime})")
    void add(Article article);

    /**
     * @Description: Retrieves a list of articles filtered by user ID, category ID, and state
     * @Param: userId - ID of the user who created the article; categoryId - ID of the article's category; state - State of the article (e.g., published, draft)
     * @return: List<Article> - A list of articles matching the specified criteria
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    List<Article> list(Integer userId, Integer categoryId, String state);

    /**
     * @Description: Updates an existing article record in the database
     * @Param: article - Article object containing updated article details
     * @return: void
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @Update("UPDATE article SET title=#{title}, content=#{content}, cover_img=#{coverImg}, state=#{state}, category_id=#{categoryId}, update_time=#{updateTime} WHERE id=#{id}")
    void update(Article article);

    /**
     * @Description: Finds an article by its ID
     * @Param: id - ID of the article to retrieve
     * @return: Article - The article object corresponding to the specified ID
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @Select("SELECT * FROM article WHERE id=#{id}")
    Article findById(Integer id);

    /**
     * @Description: Deletes an article by its ID
     * @Param: id - ID of the article to delete
     * @return: void
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @Delete("DELETE FROM article WHERE id=#{id}")
    void delete(Integer id);
}
