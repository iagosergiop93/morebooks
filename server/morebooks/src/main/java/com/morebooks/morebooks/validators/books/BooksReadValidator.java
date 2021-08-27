package com.morebooks.morebooks.validators.books;

import com.morebooks.morebooks.domain.requests.BooksReadRequest;
import com.morebooks.morebooks.validators.Validator;
import org.springframework.stereotype.Component;

@Component
public class BooksReadValidator implements Validator<BooksReadRequest> {

    @Override
    public void validate(BooksReadRequest request) throws Exception {
        if(request == null || request.getUserId() == null ||
                request.getUserId().isBlank()) {
            throw new Exception("Missing userId");
        }
    }
}
