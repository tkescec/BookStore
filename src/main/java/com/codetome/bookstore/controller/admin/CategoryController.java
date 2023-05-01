package com.codetome.bookstore.controller.admin;

import com.codetome.bookstore.domain.Category;
import com.codetome.bookstore.repository.category.CategoryRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/category")
@AllArgsConstructor
public class CategoryController extends BaseController{

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
        categoryRepository.saveNewCategory(category);
        return "redirect:/admin/category/index";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Optional<Category> category = categoryRepository.findCategoryById(id);
        if (category.isEmpty()){
            return "redirect:/admin/category/index";
        }
        model.addAttribute("category", category.get());
        return "admin/category/edit";
    }

    @PostMapping("update")
    public String update(Category category, Model model) {
        categoryRepository.updateCategory(category);
        return "redirect:/admin/category/index";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        categoryRepository.deleteCategoryById(id);
        return "redirect:/admin/category/index";
    }
}
