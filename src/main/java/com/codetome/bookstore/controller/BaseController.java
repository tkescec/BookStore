package com.codetome.bookstore.controller;

import com.codetome.bookstore.repository.author.AuthorRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@AllArgsConstructor
public class BaseController {
    @ModelAttribute("requestUri")
    public String requestUri(final HttpServletRequest request) {
        return request.getRequestURI();
    }
}
