package com.codetome.bookstore.repository.category;

import com.codetome.bookstore.domain.Category;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Primary
@Repository
public class CategoryRepositoryJdbc implements CategoryRepository {

    private static final String CATEGORY_TABLE_NAME = "category";
    private static final String CATEGORY_TABLE_NAME_ID = "IDCategory";
    private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM `category`";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public CategoryRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(CATEGORY_TABLE_NAME)
                .usingGeneratedKeyColumns(CATEGORY_TABLE_NAME_ID);
    }

    @Override
    public List<Category> getAllCategories() { return jdbcTemplate.query(SELECT_ALL_CATEGORIES, this::mapRowToCategory); }

    @Override
    public Optional<Category> findCategoryById(Long id) {
        String query = SELECT_ALL_CATEGORIES + " WHERE " + CATEGORY_TABLE_NAME_ID + " = " + id;

        return jdbcTemplate.query(query, this::mapRowToCategory).stream().findFirst();
    }

    @Override
    public Number saveNewCategory(Category category) { return saveNewCategoryDetails(category); }

    @Override
    public void updateCategory(Category category) { updateCategoryDetails(category); }

    @Override
    public void deleteCategoryById(Long id) {
        try {
            String query = "DELETE FROM " + CATEGORY_TABLE_NAME + " WHERE " + CATEGORY_TABLE_NAME_ID + " = " + id;
            jdbcTemplate.execute(query);
        } catch (Exception e) {
            System.out.printf("An error occurred while deleting the category");
        }
    }

    private Category mapRowToCategory(ResultSet result, int rowNum) throws SQLException {
        return new Category(
                result.getInt(CATEGORY_TABLE_NAME_ID),
                result.getString("Name")
        );
    }

    private Number saveNewCategoryDetails(Category category) {
        Map<String, Object> categoryDetails = new HashMap<>();

        categoryDetails.put(CATEGORY_TABLE_NAME_ID, category.getIDCategory());
        categoryDetails.put("Name", category.getName());

        return simpleJdbcInsert.execute(categoryDetails);
    }

    private void updateCategoryDetails(Category category) {
        try {
            String query = "UPDATE category SET Name = ? WHERE " + CATEGORY_TABLE_NAME_ID + " = ?";
            jdbcTemplate.update(query, category.getName(), category.getIDCategory());
        } catch (Exception e) {
            System.out.printf("An error occurred while updating the category");
        }
    }
}
