package com.codetome.bookstore.repository.category;

import com.codetome.bookstore.domain.Category;

import java.util.List;

public interface CategoryRepository {

    List<Category> getAllCategories();
    Category findById(Long id);
}
