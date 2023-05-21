package com.codetome.bookstore.repository.item;

import com.codetome.bookstore.domain.Item;
import com.codetome.bookstore.dto.ItemDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    List<Item> getAllItems();
    Optional<Item> findItemById(Long id);
    List<Item> findItemsByInvoiceId(Long invoiceId);
    Number saveNewItem(ItemDto item) throws IOException;
}
