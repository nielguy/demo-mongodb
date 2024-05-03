package com.nielo.demomongodb.demomongodb.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "book_collection")
public class Book {

    @Id
    private String id;
    private String name;
    private String isbn;
    @DBRef
    private Author author;
}
