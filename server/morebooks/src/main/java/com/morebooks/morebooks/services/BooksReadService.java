package com.morebooks.morebooks.services;

import com.morebooks.morebooks.clients.GoogleVolumeClient;
import com.morebooks.morebooks.clients.domain.Volume;
import com.morebooks.morebooks.daos.BooksDao;
import com.morebooks.morebooks.daos.UsersDao;
import com.morebooks.morebooks.entities.Book;
import com.morebooks.morebooks.entities.User;
import com.morebooks.morebooks.utils.VolumeToBookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class BooksReadService {

    private static Logger LOG = LoggerFactory.getLogger(BooksReadService.class);

    @Autowired
    private BooksDao booksDao;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private GoogleVolumeClient googleVolumeClient;

    public List<Book> getListOfReadBooks(String userId) {
        return booksDao.getReadBooksByUserId(userId);
    }

    public void insertBookToReadBooksList(String userId, String bookId) {
        // validate userId is valid
        User user = usersDao.getUserById(userId);

        CompletableFuture<Volume> volumeFromApiFuture = getVolumeByIdAsync(bookId);
        CompletableFuture<Book> bookFromDatabaseFuture = getBookByIdAsync(bookId);

        CompletableFuture.allOf(volumeFromApiFuture, bookFromDatabaseFuture).join();

        Volume volumeFromApi = volumeFromApiFuture.join();
        Book bookFromDatabase = bookFromDatabaseFuture.join();

        if(volumeFromApi == null && bookFromDatabase == null) {
            throw new RuntimeException("Invalid book");
        }
        if(volumeFromApi != null && bookFromDatabase == null) {
            Book book = VolumeToBookMapper.volumeToBook(volumeFromApi);
            booksDao.insertBook(book);
        }

        booksDao.insertReadBook(userId, bookId);
    }

    @Async("asyncExecutor")
    private CompletableFuture<Volume> getVolumeByIdAsync(String bookId) {
        Volume volume = null;
        try {
            ResponseEntity<Volume> responseEntity = googleVolumeClient.getVolumeById(bookId);
            if(responseEntity.getStatusCode().is2xxSuccessful()) {
                volume = responseEntity.getBody();
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }

        return CompletableFuture.completedFuture(volume);
    }

    @Async("asyncExecutor")
    private CompletableFuture<Book> getBookByIdAsync(String bookId) {
        Book book = null;
        try {
            book = booksDao.getBookById(bookId);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }

        return CompletableFuture.completedFuture(book);
    }

}
