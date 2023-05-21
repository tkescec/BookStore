package com.codetome.bookstore.repository.invoice;

import com.codetome.bookstore.domain.Invoice;
import com.codetome.bookstore.dto.InvoiceDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository {
    List<Invoice> getAllInvoices();
    Optional<Invoice> findInvoiceById(Long id);
    List<Invoice> findInvoicesByUserId(Long userId);
    Number saveNewInvoice(InvoiceDto invoice) throws IOException;
}
