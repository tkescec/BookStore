package com.codetome.bookstore.repository.book;

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
public class BookRepositoryJdbc implements BookRepository{
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;

    public BookRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.inserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("Book")
                .usingGeneratedKeyColumns("IDBook ");
    }

    @Override
    public List<Book> getAllBooks() {
        String query = "SELECT b.IDBook, b.ISBN, b.Name as BookName, b.Price, b.Quantity, b.ImagePath, c.IDCategory, c.Name as CategoryName, a.IDAuthor, a.FirstName, a.LastName " +
                "FROM `book` as b " +
                "inner JOIN `category` as c " +
                "ON b.CategoryID = c.IDCategory " +
                "INNER JOIN `author` as a " +
                "ON b.AuthorID = a.IDAuthor";

        List<Book> bookList = jdbcTemplate.query(query,
                (result,rowNum)->new Book(
                        result.getInt("IDBook"),
                        result.getString("ISBN"),
                        result.getString("BookName"),
                        result.getDouble("Price"),
                        result.getInt("Quantity"),
                        result.getString("ImagePath"),
                        new Category(
                                result.getInt("IDCategory"),
                                result.getString("CategoryName")
                        ),
                        new Author(
                                result.getInt("IDAuthor"),
                                result.getString("FirstName"),
                                result.getString("LastName")
                        )
                ));
        return bookList;
    }

    @Override
    public Book findById(Long id) {
        return null;
    }
}
