package com.codetome.bookstore.repository.book;

import com.codetome.bookstore.domain.Author;
import com.codetome.bookstore.domain.Book;
import com.codetome.bookstore.domain.Category;
import com.codetome.bookstore.dto.BookDto;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Primary
@Repository
public class BookRepositoryJdbc implements BookRepository{
    private static final String BOOK_TABLE_NAME = "book";
    private static final String BOOK_TABLE_NAME_ID = "IDBook";
    private static final String SELECT_ALL_BOOKS =
            "SELECT b.IDBook, b.ISBN, b.Name as BookName, b.Price, b.Quantity, b.Image, c.IDCategory, c.Name as CategoryName, a.IDAuthor, a.FirstName, a.LastName " +
            "FROM `book` as b " +
            "inner JOIN `category` as c " +
            "ON b.CategoryID = c.IDCategory " +
            "INNER JOIN `author` as a " +
            "ON b.AuthorID = a.IDAuthor";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public BookRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(BOOK_TABLE_NAME)
                .usingGeneratedKeyColumns(BOOK_TABLE_NAME_ID);
    }

    @Override
    public List<Book> getAllBooks() {
        return jdbcTemplate.query(SELECT_ALL_BOOKS, this::mapRowToBook);
    }

    @Override
    public Optional<Book> findBookById(Long id) {

        String query = SELECT_ALL_BOOKS + " WHERE b." + BOOK_TABLE_NAME_ID + " = " + id;

        return jdbcTemplate.query(query, this::mapRowToBook).stream().findFirst();
    }

    @Override
    public Number saveNewBook(BookDto book) throws IOException { return saveNewBookDetails(book); }

    @Override
    public void updateBook(BookDto book) {

    }

    @Override
    public void deleteBookById(Long id) {

    }

    private Book mapRowToBook(ResultSet result, int rowNum) throws SQLException {
        return new Book(
                result.getInt(BOOK_TABLE_NAME_ID),
                result.getString("ISBN"),
                result.getString("BookName"),
                result.getDouble("Price"),
                result.getInt("Quantity"),
                result.getString("Image"),
                new Category(
                        result.getInt("IDCategory"),
                        result.getString("CategoryName")
                ),
                new Author(
                        result.getInt("IDAuthor"),
                        result.getString("FirstName"),
                        result.getString("LastName")
                )
        );
    }

    private Number saveNewBookDetails(BookDto book) throws IOException {
        Map<String, Object> bookDetails = new HashMap<>();

        bookDetails.put(BOOK_TABLE_NAME_ID, book.getIDBook());
        bookDetails.put("ISBN", book.getISBN());
        bookDetails.put("Name", book.getName());
        bookDetails.put("Price", book.getPrice());
        bookDetails.put("Quantity", book.getQuantity());
        bookDetails.put("Image", book.getBase64Image());
        bookDetails.put("CategoryID", book.getCategoryID());
        bookDetails.put("AuthorID", book.getAuthorID());

        return simpleJdbcInsert.execute(bookDetails);
    }
}
