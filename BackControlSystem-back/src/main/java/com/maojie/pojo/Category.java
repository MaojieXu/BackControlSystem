package com.maojie.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Builder;
import lombok.Data;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

@Data
public class Category {

    @NotNull(groups = Update.class)
    private Integer id; // Primary key ID

    @NotEmpty
    private String categoryName; // Category name

    @NotEmpty
    private String categoryAlias; // Category alias

    private Integer createUser; // ID of the user who created the category

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // Timestamp for when the category was created

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime; // Timestamp for when the category was last updated

    /**
     * Validation group for adding a new category.
     */
    public interface Add extends Default {}

    /**
     * Validation group for updating an existing category.
     */
    public interface Update extends Default {}
}
