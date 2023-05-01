package com.codetome.bookstore.repository.book;

import com.codetome.bookstore.domain.Book;
import com.codetome.bookstore.dto.BookDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> getAllBooks();
    Optional<Book> findBookById(Long id);
    Number saveNewBook(BookDto book) throws IOException;
    void updateBook(BookDto book);
    void deleteBookById(Long id);
}
