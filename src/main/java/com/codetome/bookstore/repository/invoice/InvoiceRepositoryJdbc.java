package com.codetome.bookstore.repository.invoice;

import com.codetome.bookstore.domain.Invoice;
import com.codetome.bookstore.domain.Payment;
import com.codetome.bookstore.domain.User;
import com.codetome.bookstore.dto.InvoiceDto;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Primary
@Repository
public class InvoiceRepositoryJdbc implements InvoiceRepository{

    private static final String INVOICE_TABLE_NAME = "invoice";
    private static final String INVOICE_TABLE_NAME_ID = "IDInvoice";
    private static final String SELECT_ALL_INVOICES =
                    "SELECT i.IDInvoice, p.IDPayment, p.Name, u.IDUser, u.FirstName, u.LastName, i.IssuedAt" +
                    "FROM `invoice` as i " +
                    "inner JOIN `payment` as p " +
                    "ON i.PaymentID = p.IDPayment " +
                    "INNER JOIN `user` as u " +
                    "ON i.UserID = u.IDUser";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public InvoiceRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(INVOICE_TABLE_NAME)
                .usingGeneratedKeyColumns(INVOICE_TABLE_NAME_ID);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return jdbcTemplate.query(SELECT_ALL_INVOICES, this::mapRowToInvoice);
    }

    @Override
    public Optional<Invoice> findInvoiceById(Long id) {
        String query = SELECT_ALL_INVOICES + " WHERE i." + INVOICE_TABLE_NAME_ID + " = " + id;

        return jdbcTemplate.query(query, this::mapRowToInvoice).stream().findFirst();
    }

    @Override
    public List<Invoice> findInvoicesByUserId(Long userId) {
        String query = SELECT_ALL_INVOICES + " WHERE i.UserID = " + userId;

        return jdbcTemplate.query(query, this::mapRowToInvoice);
    }

    @Override
    public Number saveNewInvoice(InvoiceDto invoice) throws IOException {
        return saveNewInvoiceDetails(invoice);
    }

    private Invoice mapRowToInvoice(ResultSet result, int rowNum) throws SQLException {
        return new Invoice(
                result.getInt(INVOICE_TABLE_NAME_ID),
                new Payment(
                        result.getInt("IDPayment"),
                        result.getString("Name")
                ),
                new User(
                        result.getInt("IDUser"),
                        result.getString("username")
                ),
                result.getTimestamp("IssuedAt")
        );
    }

    private Number saveNewInvoiceDetails(InvoiceDto invoice) throws IOException {
        Map<String, Object> invoiceDetails = new HashMap<>();

        invoiceDetails.put(INVOICE_TABLE_NAME_ID, invoice.getIDInvoice());
        invoiceDetails.put("PaymentID", invoice.getPaymentID());
        invoiceDetails.put("UserId", invoice.getUserID());
        invoiceDetails.put("IssuedAt", invoice.getIssuedAt());

        return simpleJdbcInsert.executeAndReturnKey(invoiceDetails).intValue();
    }
}
