package com.nielo.demomongodb.demomongodb.service;

import com.nielo.demomongodb.demomongodb.entity.Author;
import com.nielo.demomongodb.demomongodb.entity.Book;
import com.nielo.demomongodb.demomongodb.entity.Member;
import com.nielo.demomongodb.demomongodb.request.AuthorCreationRequest;
import com.nielo.demomongodb.demomongodb.request.BookCreationRequest;
import com.nielo.demomongodb.demomongodb.request.BookLendRequest;
import com.nielo.demomongodb.demomongodb.request.MemberCreationRequest;

import java.util.List;

public interface LibraryService {

    Book readBookById(String id);

    List<Book> readBooks();

    Book readBook(String isbn);

    Book createBook(BookCreationRequest book);

    void deleteBook(String id);

    Member createMember(MemberCreationRequest member);

    Member updateMember(String id, MemberCreationRequest member);

    Author createAuthor(AuthorCreationRequest author);

    List<String> lendABook(BookLendRequest request);


}
