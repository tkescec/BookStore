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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class CartController extends BaseController {

    private static final String CART_SESSION = "_cart";
    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;
    @GetMapping("cart/addToCart/{id}")
    public String addBookToCart(@PathVariable("id") Long id, Model model, HttpSession session) {
        Optional<Book> book = bookRepository.findBookById(id);

        if (book.isEmpty() || book.get().getQuantity() < 0){
            return "redirect:/";
        }

        CartDto cart = (CartDto) session.getAttribute(CART_SESSION);
        if (cart == null){
            cart = new CartDto();
        }
        CartItemDto item;
        List cartItems = cart.getItems();
        if (cartItems == null) {
            cartItems = new ArrayList<>();

            item = new CartItemDto(1, book.get());
            cartItems.add(item);

        } else {
            boolean isNew = true;
            for (int i = 0; i < cartItems.size(); i++) {
                item = (CartItemDto) cartItems.get(i);
                if (item.getBook().equals(book.get())) {
                    if (item.getQuantity() < item.getBook().getQuantity() ){
                        item.setQuantity(item.getQuantity() + 1);
                        cartItems.set(i, item);
                    }
                    isNew = false;
                    break;
                }
            }

            if (isNew){
                item = new CartItemDto(1, book.get());
                cartItems.add(item);
            }
        }

        cart.setItems(cartItems);
        session.setAttribute(CART_SESSION, cart);

        return "redirect:/cart";
    }
    @GetMapping("/cart")
    public String cart(Model model, HttpSession session) {
        List<Category> categoryList = categoryRepository.getAllCategories();
        model.addAttribute("allCategories", categoryList);

        CartDto cart = (CartDto) session.getAttribute(CART_SESSION);
        model.addAttribute("cart", cart);

        return "cart";
    }

    @GetMapping("cart/deleteFromCart/{id}")
    @ResponseBody
    public String deleteFromCart(@PathVariable("id") Long id, Model model, HttpSession session) {
        Optional<Book> book = bookRepository.findBookById(id);

        if (book.isEmpty()){
            return "false";
        }

        CartDto cart = (CartDto) session.getAttribute(CART_SESSION);
        if (cart == null){
            return "false";
        }

        List cartItems = cart.getItems();
        Iterator<CartItemDto> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getBook().equals(book.get())) {
                iterator.remove();
            }
        }
        return "true";
    }

    @GetMapping("cart/increaseItemQuantityInCart/{id}")
    @ResponseBody
    public String increaseItemQuantityInCart(@PathVariable("id") Long id, Model model, HttpSession session) {
        Optional<Book> book = bookRepository.findBookById(id);

        if (book.isEmpty()){
            return "false";
        }

        CartDto cart = (CartDto) session.getAttribute(CART_SESSION);
        if (cart == null){
            return "false";
        }

        List cartItems = cart.getItems();
        Iterator<CartItemDto> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            CartItemDto cartItem = iterator.next();
            if (cartItem.getBook().equals(book.get())) {
                if (cartItem.getQuantity() < book.get().getQuantity() ){
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    return "true";
                }
            }
        }
        return "false";
    }

    @GetMapping("cart/decreaseItemQuantityInCart/{id}")
    @ResponseBody
    public String decreaseItemQuantityInCart(@PathVariable("id") Long id, Model model, HttpSession session) {
        Optional<Book> book = bookRepository.findBookById(id);

        if (book.isEmpty()){
            return "false";
        }

        CartDto cart = (CartDto) session.getAttribute(CART_SESSION);
        if (cart == null){
            return "false";
        }

        List cartItems = cart.getItems();
        Iterator<CartItemDto> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            CartItemDto cartItem = iterator.next();
            if (cartItem.getBook().equals(book.get())) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                if (cartItem.getQuantity() < 1 ){
                    iterator.remove();
                }
                return "true";
            }
        }
        return "false";
    }
}
