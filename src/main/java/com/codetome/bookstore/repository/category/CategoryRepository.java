package com.codetome.bookstore.repository.category;

import com.codetome.bookstore.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    List<Category> getAllCategories();
    Optional<Category> findCategoryById(Long id);
    Number saveNewCategory(Category category);
    void updateCategory(Category category);
    void deleteCategoryById(Long id);

}
