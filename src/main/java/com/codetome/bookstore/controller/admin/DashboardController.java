package com.codetome.bookstore.controller.admin;


import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin")
@AllArgsConstructor
public class DashboardController extends BaseController {
    @GetMapping("dashboard")
    public String home(Model model, HttpSession session) {
        return "admin/dashboard";
    }
}
