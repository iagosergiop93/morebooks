package com.morebooks.morebooks.validators.books;

import com.morebooks.morebooks.domain.requests.BooksReadInsertRequest;
import com.morebooks.morebooks.validators.Validator;
import org.springframework.stereotype.Component;

@Component
public class BooksReadInsertValidator implements Validator<BooksReadInsertRequest> {

    @Override
    public void validate(BooksReadInsertRequest request) throws Exception {
        if(request == null || request.getUserId() == null ||
                request.getUserId().isBlank()) {
            throw new Exception("Missing userId");
        }

        if(request.getBookId() == null ||
                request.getBookId().isBlank()) {
            throw new Exception("Missing bookId");
        }
    }
}
