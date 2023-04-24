package com.codetome.bookstore.controller;

import com.codetome.bookstore.domain.Book;
import com.codetome.bookstore.domain.Category;
import com.codetome.bookstore.repository.book.BookRepository;
import com.codetome.bookstore.repository.category.CategoryRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController{
    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        List<Book> bookList = bookRepository.getAllBooks();
        model.addAttribute("allBooks", bookList);
        List<Category> categoryList = categoryRepository.getAllCategories();
        model.addAttribute("allCategories", categoryList);

        return "home";
    }
}
