package com.codetome.bookstore.repository.author;

import com.codetome.bookstore.domain.Author;
import com.codetome.bookstore.domain.Book;
import com.codetome.bookstore.domain.Category;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class AuthorRepositoryJdbc implements AuthorRepository {
    private static final String AUTHOR_TABLE_NAME = "author";
    private static final String AUTHOR_TABLE_NAME_ID = "IDAuthor";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;

    public AuthorRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.inserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(AUTHOR_TABLE_NAME)
                .usingGeneratedKeyColumns(AUTHOR_TABLE_NAME_ID);
    }


    @Override
    public List<Author> getAllAuthors() {
        String query = "SELECT * FROM " + AUTHOR_TABLE_NAME;

        List<Author> authorList = jdbcTemplate.query(query,
                (result,rowNum)->new Author(
                        result.getInt("IDAuthor"),
                        result.getString("FirstName"),
                        result.getString("LastName")
                ));
        return authorList;
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.empty();
    }
}
