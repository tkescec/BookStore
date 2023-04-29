package com.codetome.bookstore.controller.admin;

import com.codetome.bookstore.domain.Category;
import com.codetome.bookstore.repository.category.CategoryRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin/category")
@AllArgsConstructor
public class CategoryController  extends BaseController{

    private CategoryRepository categoryRepository;
    @GetMapping("index")
    public String index(Model model, HttpSession session) {
        List<Category> categoryList = categoryRepository.getAllCategories();
        model.addAttribute("allCategories", categoryList);
        return "admin/category/index";
    }

    @GetMapping("create")
    public String create(Category category) {
        return "admin/category/create";
    }
    @PostMapping("store")
    public String store(Category category, Model model) {

        //userRepository.save(user);
        return "redirect:/admin/category/index.html";
    }
}
