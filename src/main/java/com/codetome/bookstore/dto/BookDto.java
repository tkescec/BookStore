package com.codetome.bookstore.dto;

import com.codetome.bookstore.domain.Author;
import com.codetome.bookstore.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DecimalFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Integer IDBook;
    private String ISBN;
    private String Name;
    private Double Price;
    private Integer Quantity;
    private MultipartFile Image;
    private Integer CategoryID;
    private Integer AuthorID;

    public String getBase64Image() throws IOException {
        byte[] image = Base64.encodeBase64(Image.getBytes(), true);
        return  new String(image);
    }
}
