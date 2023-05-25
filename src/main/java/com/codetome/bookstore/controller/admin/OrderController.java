package com.codetome.bookstore.controller.admin;

import com.codetome.bookstore.controller.BaseController;
import com.codetome.bookstore.domain.Invoice;
import com.codetome.bookstore.domain.Log;
import com.codetome.bookstore.repository.invoice.InvoiceRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("AdminOrderController")
@RequestMapping("admin")
@AllArgsConstructor
public class OrderController extends BaseController {
    private InvoiceRepository invoiceRepository;

    @GetMapping("orders")
    public String home(Model model, HttpSession session) {
        List<Invoice> orders = invoiceRepository.getAllInvoices();
        model.addAttribute("orders", orders);

        return "admin/orders";
    }
}
