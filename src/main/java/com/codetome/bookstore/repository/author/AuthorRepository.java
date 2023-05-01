package com.codetome.bookstore.repository.author;

import com.codetome.bookstore.domain.Author;
import com.codetome.bookstore.domain.Book;
import com.codetome.bookstore.domain.Category;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> getAllAuthors();
    Optional<Author> findById(Long id);
}
