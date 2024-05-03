package com.nielo.demomongodb.demomongodb.controller;

import com.nielo.demomongodb.demomongodb.entity.Author;
import com.nielo.demomongodb.demomongodb.entity.Book;
import com.nielo.demomongodb.demomongodb.entity.Member;
import com.nielo.demomongodb.demomongodb.request.AuthorCreationRequest;
import com.nielo.demomongodb.demomongodb.request.BookCreationRequest;
import com.nielo.demomongodb.demomongodb.request.BookLendRequest;
import com.nielo.demomongodb.demomongodb.request.MemberCreationRequest;
import com.nielo.demomongodb.demomongodb.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/book")
    public ResponseEntity<?> readBooks(@RequestParam(required = false) String isbn) {
        if (isbn == null) {
            return ResponseEntity.ok(libraryService.readBooks());
        }
        return ResponseEntity.status(HttpStatus.OK).body(libraryService.readBook(isbn));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<Book> readBook (@PathVariable String bookId) {
        return ResponseEntity.ok(libraryService.readBookById(bookId));
    }

    @PostMapping("/book")
    public ResponseEntity<Book> createBook (@RequestBody BookCreationRequest request) {
        return ResponseEntity.ok(libraryService.createBook(request));
    }

    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<Void> deleteBook (@PathVariable String bookId) {
        libraryService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/member")
    public ResponseEntity<Member> createMember (@RequestBody MemberCreationRequest request) {
        return ResponseEntity.ok(libraryService.createMember(request));
    }

    @PatchMapping("/member/{memberId}")
    public ResponseEntity<Member> updateMember (@RequestBody MemberCreationRequest request, @PathVariable String memberId) {
        return ResponseEntity.ok(libraryService.updateMember(memberId, request));
    }

    @PostMapping("/book/lend")
    public ResponseEntity<List<String>> lendABook(@RequestBody BookLendRequest bookLendRequests) {
        return ResponseEntity.ok(libraryService.lendABook(bookLendRequests));
    }

    @PostMapping("/author")
    public ResponseEntity<Author> createAuthor (@RequestBody AuthorCreationRequest request) {
        return ResponseEntity.ok(libraryService.createAuthor(request));
    }
}
