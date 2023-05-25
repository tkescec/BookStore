package com.codetome.bookstore.controller.user;

import com.codetome.bookstore.controller.BaseController;
import com.codetome.bookstore.domain.Book;
import com.codetome.bookstore.domain.Category;
import com.codetome.bookstore.domain.Invoice;
import com.codetome.bookstore.domain.User;
import com.codetome.bookstore.dto.CartDto;
import com.codetome.bookstore.repository.book.BookRepository;
import com.codetome.bookstore.repository.category.CategoryRepository;
import com.codetome.bookstore.repository.invoice.InvoiceRepository;
import com.codetome.bookstore.repository.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller("UserOrderController")
@RequestMapping("user")
@AllArgsConstructor
public class OrderController extends BaseController {
    private static final String CART_SESSION = "_cart";
    private InvoiceRepository invoiceRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;

    @GetMapping("/orders")
    public String home(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findUserByUsername(auth.getName());
        List<Invoice> invoiceList = invoiceRepository.findInvoicesByUserId(user.get().getIDUser());
        model.addAttribute("orders", invoiceList);

        List<Category> categoryList = categoryRepository.getAllCategories();
        model.addAttribute("allCategories", categoryList);
        CartDto cart = (CartDto) session.getAttribute(CART_SESSION);
        model.addAttribute("cart", cart);

        return "user/orders";
    }
}
