package com.codetome.bookstore.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class BaseController {
    @ModelAttribute("requestUri")
    public String requestUri(final HttpServletRequest request) {
        return request.getRequestURI();
    }
}
