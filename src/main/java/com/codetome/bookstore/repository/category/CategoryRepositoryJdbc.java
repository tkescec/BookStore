package com.codetome.bookstore.repository.category;

import com.codetome.bookstore.domain.Author;
import com.codetome.bookstore.domain.Book;
import com.codetome.bookstore.domain.Category;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class CategoryRepositoryJdbc implements CategoryRepository {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;

    public CategoryRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.inserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("Category")
                .usingGeneratedKeyColumns("IDCategory");
    }

    @Override
    public List<Category> getAllCategories() {
        String query = "SELECT * FROM `category`";

        List<Category> categoryList = jdbcTemplate.query(query,
                (result,rowNum)->new Category(
                        result.getInt("IDCategory"),
                        result.getString("Name")
                ));

        return categoryList;
    }

    @Override
    public Category findById(Long id) {
        return null;
    }
}
