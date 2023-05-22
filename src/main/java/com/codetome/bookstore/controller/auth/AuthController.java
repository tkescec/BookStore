package com.codetome.bookstore.controller.auth;

import com.codetome.bookstore.controller.BaseController;
import com.codetome.bookstore.domain.Category;
import com.codetome.bookstore.dto.CartDto;
import com.codetome.bookstore.repository.category.CategoryRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController extends BaseController {
    private static final String CART_SESSION = "_cart";
    private CategoryRepository categoryRepository;
    @GetMapping("login")
    public String openLoginPage(Model model, HttpSession session) {
        List<Category> categoryList = categoryRepository.getAllCategories();
        model.addAttribute("allCategories", categoryList);
        CartDto cart = (CartDto) session.getAttribute(CART_SESSION);
        model.addAttribute("cart", cart);

        return "auth/login";
    }
}
