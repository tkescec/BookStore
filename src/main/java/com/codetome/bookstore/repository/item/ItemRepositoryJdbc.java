package com.codetome.bookstore.repository.item;

import com.codetome.bookstore.domain.*;
import com.codetome.bookstore.dto.InvoiceDto;
import com.codetome.bookstore.dto.ItemDto;
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
public class ItemRepositoryJdbc implements ItemRepository {

    private static final String ITEM_TABLE_NAME = "item";
    private static final String ITEM_TABLE_NAME_ID = "IDItem";
    private static final String SELECT_ALL_ITEMS =
                    "SELECT it.IDItem, it.Quantity, it.PriceByPiece, it.TotalPrice, inv.IDInvoice, inv.IssuedAt, b.IDBook, b.Name" +
                    "FROM `item` as it " +
                    "INNER JOIN `invoice` as inv " +
                    "ON it.InvoiceID = inv.IDInvoice " +
                    "INNER JOIN `book` as b " +
                    "ON it.BookID = b.IDBook";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public ItemRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(ITEM_TABLE_NAME)
                .usingGeneratedKeyColumns(ITEM_TABLE_NAME_ID);
    }

    @Override
    public List<Item> getAllItems() {
        return jdbcTemplate.query(SELECT_ALL_ITEMS, this::mapRowToItem);
    }

    @Override
    public Optional<Item> findItemById(Long id) {
        String query = SELECT_ALL_ITEMS + " WHERE i." + ITEM_TABLE_NAME_ID + " = " + id;

        return jdbcTemplate.query(query, this::mapRowToItem).stream().findFirst();
    }

    @Override
    public List<Item> findItemsByInvoiceId(Long invoiceId) {
        String query = SELECT_ALL_ITEMS + " WHERE it.InvoiceID = " + invoiceId;

        return jdbcTemplate.query(query, this::mapRowToItem);
    }

    @Override
    public Number saveNewItem(ItemDto item) throws IOException {
        return saveNewItemDetails(item);
    }

    private Item mapRowToItem(ResultSet result, int rowNum) throws SQLException {
        return new Item(
                result.getInt(ITEM_TABLE_NAME_ID),
                result.getInt("Quantity"),
                result.getDouble("PriceByPiece"),
                result.getDouble("TotalPrice"),
                new Invoice(
                        result.getInt("IDInvoice"),
                        result.getTimestamp("IssuedAt")
                ),
                new Book(
                        result.getInt("IDBook"),
                        result.getString("Name")
                )
        );
    }

    private Number saveNewItemDetails(ItemDto item) throws IOException {
        Map<String, Object> itemDetails = new HashMap<>();

        itemDetails.put(ITEM_TABLE_NAME_ID, item.getIDItem());
        itemDetails.put("Quantity", item.getQuantity());
        itemDetails.put("PriceByPiece", item.getPriceByPiece());
        itemDetails.put("TotalPrice", item.getTotalPrice());
        itemDetails.put("InvoiceID", item.getInvoiceID());
        itemDetails.put("BookID", item.getBookID());

        return simpleJdbcInsert.execute(itemDetails);
    }
}
