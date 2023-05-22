package com.codetome.bookstore.controller;

import com.codetome.bookstore.domain.Category;
import com.codetome.bookstore.domain.User;
import com.codetome.bookstore.dto.*;
import com.codetome.bookstore.repository.book.BookRepository;
import com.codetome.bookstore.repository.category.CategoryRepository;
import com.codetome.bookstore.repository.invoice.InvoiceRepository;
import com.codetome.bookstore.repository.item.ItemRepository;
import com.codetome.bookstore.repository.user.UserRepository;
import com.codetome.bookstore.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class PaypalController extends BaseController {
    private PaypalService service;
    private InvoiceRepository invoiceRepository;
    private ItemRepository itemRepository;
    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    private static final String CART_SESSION = "_cart";
    public static final String SUCCESS_URL = "checkout/pay/success";
    public static final String CANCEL_URL = "checkout/pay/cancel";

    @GetMapping("/checkout/pay")
    public String payment(HttpSession session) {
        CartDto cart = (CartDto) session.getAttribute(CART_SESSION);
        OrderDto order = new OrderDto(cart.getTotal(), "EUR", "paypal", "sale", "");
        try {
            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                    order.getIntent(), order.getDescription(), "http://localhost:8080/" + CANCEL_URL,
                    "http://localhost:8080/" + SUCCESS_URL);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay(Model model, HttpSession session) {
        CartDto cart = (CartDto) session.getAttribute(CART_SESSION);
        session.setAttribute(CART_SESSION, null);
        model.addAttribute("cart", cart);
        model.addAttribute("localDateTime", LocalDateTime.now());
        List<Category> categoryList = categoryRepository.getAllCategories();
        model.addAttribute("allCategories", categoryList);

        return "checkout/cancel";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, Model model, HttpSession session) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Optional<User> user = userRepository.findUserByUsername(auth.getName());
            Payment payment = service.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                InvoiceDto invoiceDto = new InvoiceDto(
                        2,
                        user.get().getIDUser(),
                        new Timestamp(System.currentTimeMillis())
                );
                Integer invoiceID = (Integer) invoiceRepository.saveNewInvoice(invoiceDto);

                CartDto cart = (CartDto) session.getAttribute(CART_SESSION);
                List cartItems = cart.getItems();
                Iterator<CartItemDto> iterator = cartItems.iterator();

                while (iterator.hasNext()) {
                    CartItemDto cartItem = iterator.next();
                    ItemDto itemDto = new ItemDto(
                            cartItem.getQuantity(),
                            cartItem.getBook().getPrice(),
                            cartItem.getTotal(),
                            invoiceID,
                            cartItem.getBook().getIDBook()
                    );

                    Number itemID = itemRepository.saveNewItem(itemDto);
                    Integer newQuantity =
                            (cartItem.getBook().getQuantity() - cartItem.getQuantity()) < 0
                                    ? 0
                                    : cartItem.getBook().getQuantity() - cartItem.getQuantity();

                    BookDto bookDto = new BookDto(
                            cartItem.getBook().getIDBook(),
                            cartItem.getBook().getISBN(),
                            cartItem.getBook().getName(),
                            cartItem.getBook().getPrice(),
                            newQuantity,
                            cartItem.getBook().getCategory().getIDCategory(),
                            cartItem.getBook().getAuthor().getIDAuthor()
                    );

                    bookRepository.updateBook(bookDto);

                }
                List<Category> categoryList = categoryRepository.getAllCategories();
                model.addAttribute("allCategories", categoryList);
                model.addAttribute("order", cart);
                model.addAttribute("payment", payment);
                model.addAttribute("localDateTime", LocalDateTime.now());
                session.setAttribute(CART_SESSION, null);

                return "checkout/success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/";
    }
}
