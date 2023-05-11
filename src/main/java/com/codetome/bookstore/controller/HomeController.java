package com.codetome.bookstore.controller;

import com.codetome.bookstore.domain.Book;
import com.codetome.bookstore.domain.Category;
import com.codetome.bookstore.dto.CartDto;
import com.codetome.bookstore.dto.CartItemDto;
import com.codetome.bookstore.repository.book.BookRepository;
import com.codetome.bookstore.repository.category.CategoryRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class HomeController extends BaseController{
    private static final String CART_SESSION = "_cart";
    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        List<Book> bookList = bookRepository.getAllBooks();
        model.addAttribute("allBooks", bookList);
        List<Category> categoryList = categoryRepository.getAllCategories();
        model.addAttribute("allCategories", categoryList);
        CartDto cart = (CartDto) session.getAttribute(CART_SESSION);
        model.addAttribute("cart", cart);

        return "home";
    }

    @GetMapping("category/{id}")
    public String category(@PathVariable("id") Long id, Model model, HttpSession session) {
        Optional<List<Book>> bookList = bookRepository.findBooksByCategoryId(id);
        model.addAttribute("allBooks", bookList.get());
        List<Category> categoryList = categoryRepository.getAllCategories();
        model.addAttribute("allCategories", categoryList);
        CartDto cart = (CartDto) session.getAttribute(CART_SESSION);
        model.addAttribute("cart", cart);

        if (bookList.get().isEmpty()){
            return "redirect:/";
        }

        return "category";
    }

    @GetMapping("book/{id}")
    public String book(@PathVariable("id") Long id, Model model, HttpSession session) {
        Optional<Book> book = bookRepository.findBookById(id);
        
        List<Category> categoryList = categoryRepository.getAllCategories();
        model.addAttribute("allCategories", categoryList);
        CartDto cart = (CartDto) session.getAttribute(CART_SESSION);
        model.addAttribute("cart", cart);

        if (book.isEmpty()){
            return "redirect:/";
        }

        model.addAttribute("book", book.get());
        return "book";
    }

    @GetMapping("addToCart/{id}")
    public String addBookToCart(@PathVariable("id") Long id, Model model, HttpSession session) {
        Optional<Book> book = bookRepository.findBookById(id);

        if (book.isEmpty()){
            return "redirect:/";
        }

        CartDto cart = (CartDto) session.getAttribute(CART_SESSION);
        if (cart == null){
            cart = new CartDto();

        }

        List cartItems = cart.getItems();
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        CartItemDto item = new CartItemDto(1, book.get());
        cartItems.add(item);
        cart.setItems(cartItems);

        session.setAttribute(CART_SESSION, cart);

        return "redirect:/cart";
    }
    @GetMapping("cart")
    public String cart(Model model, HttpSession session) {
        List<Category> categoryList = categoryRepository.getAllCategories();
        model.addAttribute("allCategories", categoryList);

        CartDto cart = (CartDto) session.getAttribute(CART_SESSION);
        model.addAttribute("cart", cart);

        return "cart";
    }
}
