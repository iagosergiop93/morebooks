package com.morebooks.morebooks.domain.requests;

import lombok.Data;

@Data
public class BooksReadInsertRequest {

    private String userId;
    private String bookId;

}
