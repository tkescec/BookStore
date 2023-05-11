package com.codetome.bookstore.controller.admin;

import com.codetome.bookstore.controller.BaseController;
import com.codetome.bookstore.domain.Author;
import com.codetome.bookstore.domain.Book;
import com.codetome.bookstore.domain.Category;
import com.codetome.bookstore.dto.BookDto;
import com.codetome.bookstore.repository.author.AuthorRepository;
import com.codetome.bookstore.repository.book.BookRepository;
import com.codetome.bookstore.repository.category.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/book")
@AllArgsConstructor
public class BookController extends BaseController {
    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;
    private AuthorRepository authorRepository;
    @GetMapping("index")
    public String index(Model model) {
        List<Book> bookList = bookRepository.getAllBooks();
        model.addAttribute("allBooks", bookList);
        return "admin/book/index";
    }

    @GetMapping("create")
    public String create(Model model) {
        List<Category> categoryList = categoryRepository.getAllCategories();
        model.addAttribute("categories", categoryList);
        List<Author> authorList = authorRepository.getAllAuthors();
        model.addAttribute("authors", authorList);
        return "admin/book/create";
    }

    @PostMapping("store")
    public String store(@ModelAttribute BookDto book) throws IOException {
        bookRepository.saveNewBook(book);
        return "redirect:/admin/book/index";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Optional<Book> book = bookRepository.findBookById(id);
        List<Category> categoryList = categoryRepository.getAllCategories();
        model.addAttribute("categories", categoryList);
        List<Author> authorList = authorRepository.getAllAuthors();
        model.addAttribute("authors", authorList);

        if (book.isEmpty()){
            return "redirect:/admin/book/index";
        }
        model.addAttribute("book", book.get());
        return "admin/book/edit";
    }

    @PostMapping("update")
    public String update(@ModelAttribute BookDto book) throws IOException {
        bookRepository.updateBook(book);
        return "redirect:/admin/book/index";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        bookRepository.deleteBookById(id);
        return "redirect:/admin/book/index";
    }
}
