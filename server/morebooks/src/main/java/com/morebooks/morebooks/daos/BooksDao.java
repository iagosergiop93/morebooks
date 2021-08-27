package com.morebooks.morebooks.daos;

import com.morebooks.morebooks.daos.mappers.BookRowMapper;
import com.morebooks.morebooks.entities.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BooksDao {

    private static final Logger LOG = LoggerFactory.getLogger(BooksDao.class);

    @Value("${daos.booksdao.getReadBooksByUserId}")
    private String getReadBooksByUserId;

    @Value("${daos.booksdao.getBookById}")
    private String getBookById;

    @Value("${daos.booksdao.insertBook}")
    private String insertBook;

    @Value("${daos.booksdao.insertReadBook}")
    private String insertReadBook;

    @Value("${daos.booksdao.insertWishBook}")
    private String insertWishBook;

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate;

    public List<Book> getReadBooksByUserId(String userId) {
        List<Book> books = null;
        try {
            SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("userid", userId);
            books = this.namedJdbcTemplate.query(getReadBooksByUserId, namedParameters, new BookRowMapper());
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }

        return books;
    }

    public Book getBookById(String bookId) {
        Book book = null;
        try {
            SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("bookid", bookId);
            book = this.namedJdbcTemplate.queryForObject(getBookById, namedParameters, new BookRowMapper());
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }

        return book;
    }

    public boolean insertBook(Book book) {
        boolean inserted = false;

        SqlParameterSource namedParameters = mapBookToInsertInDatabase(book);
        int rows = this.namedJdbcTemplate.update(insertBook, namedParameters);
        if(rows > 0) inserted = true;

        return inserted;
    }

    public boolean insertReadBook(String userId, String bookId) {
        boolean inserted = false;

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("userid", userId);
        namedParameters.addValue("bookid", bookId);
        int rows = this.namedJdbcTemplate.update(insertReadBook, namedParameters);
        if(rows > 0) inserted = true;

        return inserted;
    }

    public boolean insertWishBook(String userId, String bookId) {
        boolean inserted = false;
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("userid", userId);
            namedParameters.addValue("bookid", bookId);
            int rows = this.namedJdbcTemplate.update(insertWishBook, namedParameters);
            if(rows > 0) inserted = true;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }

        return inserted;
    }

    private SqlParameterSource mapBookToInsertInDatabase(Book book) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", book.getId());
        namedParameters.addValue("title", book.getTitle());
        namedParameters.addValue("subtitle", book.getSubtitle());

        String authorsConcatenated = book.getAuthors().stream().reduce("", (finalStr, currentStr) -> {
            return finalStr + "#" +currentStr;
        });
        namedParameters.addValue("authors", authorsConcatenated);

        namedParameters.addValue("description", book.getDescription());
        namedParameters.addValue("page_count", book.getPageCount());
        namedParameters.addValue("publisher", book.getPublisher());
        namedParameters.addValue("published_date", book.getPublishedDate());
        namedParameters.addValue("thumbnail", book.getThumbnail());

        return namedParameters;
    }

}
