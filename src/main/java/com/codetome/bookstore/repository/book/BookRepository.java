package com.codetome.bookstore.repository.book;

import com.codetome.bookstore.domain.Book;

import java.util.List;

public interface BookRepository {
    List<Book> getAllBooks();
    Book findById(Long id);
}
