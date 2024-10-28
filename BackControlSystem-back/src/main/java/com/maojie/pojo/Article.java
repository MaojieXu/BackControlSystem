package com.maojie.pojo;

import com.maojie.anno.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class Article {

    private Integer id; // Primary key ID

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String title; // Article title, with a length constraint of 1-10 characters

    @NotEmpty
    private String content; // Article content

    @NotEmpty
    @URL
    private String coverImg; // Cover image URL

    @State
    private String state; // Publish state, either "published" or "draft"

    @NotNull
    private Integer categoryId; // Category ID for the article

    private Integer createUser; // ID of the user who created the article

    private LocalDateTime createTime; // Timestamp for when the article was created

    private LocalDateTime updateTime; // Timestamp for when the article was last updated
}
