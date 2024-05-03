package com.nielo.demomongodb.demomongodb.request;

import lombok.Data;

@Data
public class BookCreationRequest {

    private String name;
    private String isbn;
    private String authorId;
}
