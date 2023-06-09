package com.codetome.bookstore.controller.admin;


import com.codetome.bookstore.controller.BaseController;
import com.codetome.bookstore.domain.Log;
import com.codetome.bookstore.repository.author.AuthorRepository;
import com.codetome.bookstore.repository.log.LogRepository;
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
public class DashboardController extends BaseController {
    private LogRepository logRepository;
    @GetMapping("dashboard")
    public String home(Model model, HttpSession session) {
        List<Log> logs = logRepository.getAllLogs();
        model.addAttribute("logs", logs);

        return "admin/dashboard";
    }
}
