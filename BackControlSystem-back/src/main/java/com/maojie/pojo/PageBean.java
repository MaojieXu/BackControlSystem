package com.maojie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: Pagination result object that holds the total count and a list of items for the current page.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean<T> {

    private Long total; // Total number of items across all pages

    private List<T> items; // List of items for the current page
}
