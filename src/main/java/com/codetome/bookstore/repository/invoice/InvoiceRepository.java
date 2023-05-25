package com.codetome.bookstore.repository.invoice;

import com.codetome.bookstore.domain.Invoice;
import com.codetome.bookstore.dto.InvoiceDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository {
    List<Invoice> getAllInvoices();
    Optional<Invoice> findInvoiceById(Integer id);
    List<Invoice> findInvoicesByUserId(Integer userId);
    Number saveNewInvoice(InvoiceDto invoice) throws IOException;
}
