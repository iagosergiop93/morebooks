package com.morebooks.morebooks.daos.mappers;

import com.morebooks.morebooks.entities.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getString("id"));
        book.setTitle(rs.getString("title"));
        book.setSubtitle(rs.getString("subtitle"));

        String concatenatedAuthors = rs.getString("authors");
        if(concatenatedAuthors != null && !concatenatedAuthors.isBlank()) {
            book.setAuthors(Arrays.asList(concatenatedAuthors.split("#")));
        }

        book.setPublisher(rs.getString("publisher"));
        book.setPublishedDate(rs.getString("published_date"));
        book.setDescription(rs.getString("description"));
        book.setPageCount(rs.getInt("page_count"));
        book.setThumbnail(rs.getString("thumbnail"));

        return book;
    }
}
