package com.codetome.bookstore.controller.admin;

import com.codetome.bookstore.domain.Book;
import com.codetome.bookstore.domain.Category;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin")
@AllArgsConstructor
public class DashboardController {
    @GetMapping("dashboard")
    public String home(Model model, HttpSession session) {
        return "admin/dashboard";
    }
}
