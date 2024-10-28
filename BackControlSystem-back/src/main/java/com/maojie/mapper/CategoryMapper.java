package com.maojie.mapper;

import com.maojie.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * @Description: Inserts a new category record into the database
     * @Param: category - Category object containing category details to be inserted
     * @return: void
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @Insert("INSERT INTO category (category_name, category_alias, create_user, create_time, update_time) " +
            "VALUES (#{categoryName}, #{categoryAlias}, #{createUser}, #{createTime}, #{updateTime})")
    void add(Category category);

    /**
     * @Description: Retrieves a list of categories created by a specific user
     * @Param: userId - ID of the user who created the categories
     * @return: List<Category> - A list of categories created by the specified user
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @Select("SELECT * FROM category WHERE create_user = #{userId}")
    List<Category> list(Integer userId);

    /**
     * @Description: Finds a category by its ID
     * @Param: id - ID of the category to retrieve
     * @return: Category - The category object corresponding to the specified ID
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @Select("SELECT * FROM category WHERE id = #{id}")
    Category findByid(Integer id);

    /**
     * @Description: Updates an existing category record in the database
     * @Param: category - Category object containing updated category details
     * @return: void
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @Update("UPDATE category SET category_name=#{categoryName}, category_alias=#{categoryAlias}, update_time=#{updateTime} WHERE id=#{id}")
    void update(Category category);

    /**
     * @Description: Deletes a category by its ID
     * @Param: id - ID of the category to delete
     * @return: void
     * @Author: Maojie Xu
     * @Date: 10/24/2024
     */
    @Delete("DELETE FROM category WHERE id = #{id}")
    void delete(Integer id);
}
