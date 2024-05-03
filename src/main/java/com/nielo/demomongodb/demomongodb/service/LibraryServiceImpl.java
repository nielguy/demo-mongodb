package com.nielo.demomongodb.demomongodb.service;

import com.nielo.demomongodb.demomongodb.entity.*;
import com.nielo.demomongodb.demomongodb.exception.EntityNotFoundException;
import com.nielo.demomongodb.demomongodb.repository.AuthorRepository;
import com.nielo.demomongodb.demomongodb.repository.BookRepository;
import com.nielo.demomongodb.demomongodb.repository.LendRepository;
import com.nielo.demomongodb.demomongodb.repository.MemberRepository;
import com.nielo.demomongodb.demomongodb.request.AuthorCreationRequest;
import com.nielo.demomongodb.demomongodb.request.BookCreationRequest;
import com.nielo.demomongodb.demomongodb.request.BookLendRequest;
import com.nielo.demomongodb.demomongodb.request.MemberCreationRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final AuthorRepository authorRepository;
    private final LendRepository lendRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Override
    public Book readBookById(String id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        }
        throw new EntityNotFoundException("Cant find any book under given ID");
    }

    @Override
    public List<Book> readBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book readBook(String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if (book.isPresent()) {
            return book.get();
        }
        throw new EntityNotFoundException("Cant find any book under given ISBN");
    }

    @Override
    public Book createBook(BookCreationRequest book) {
        Optional<Author> author = authorRepository.findById(book.getAuthorId());
        if (!author.isPresent()) {
            throw new EntityNotFoundException("Author Not Found");
        }
        Book bookToCreate = new Book();
        BeanUtils.copyProperties(book, bookToCreate);
        bookToCreate.setAuthor(author.get());
        return bookRepository.save(bookToCreate);
    }

    @Override
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Member createMember(MemberCreationRequest member) {
        Member memberObject = new Member();
        BeanUtils.copyProperties(member, memberObject);
        memberObject.setStatus(MemberStatus.ACTIVE);
        return memberObject;
    }

    @Override
    public Member updateMember(String id, MemberCreationRequest member) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (!optionalMember.isPresent()) {
            throw new EntityNotFoundException("Member not present in the database");
        }
        Member memberObject = optionalMember.get();
        memberObject.setLastName(member.getLastName());
        memberObject.setFirstName(member.getFirstName());
        return memberRepository.save(memberObject);
    }

    @Override
    public Author createAuthor(AuthorCreationRequest author) {
        Author authorObject = new Author();
        BeanUtils.copyProperties(author, authorObject);
        return authorRepository.save(authorObject);
    }

    @Override
    public List<String> lendABook(BookLendRequest request) {
        Optional<Member> memberForId = memberRepository.findById(request.getMemberId());
        if (!memberForId.isPresent()) {
            throw new EntityNotFoundException("Member not present in the database");
        }
        Member member = memberForId.get();
        if (member.getStatus() != MemberStatus.ACTIVE) {
            throw new EntityNotFoundException("User is not active to proceed a lending.");
        }
        List<String> booksApprovedToBurrow = new ArrayList<>();
        request.getBookIds().forEach(bookId -> {
            Optional<Book> bookForId = bookRepository.findById(bookId);
            if (!bookForId.isPresent()) {
                throw new EntityNotFoundException("Cant find any book under given ID");
            }
            Optional<Lend> burrowedBooks = lendRepository.findByBookAndStatus(bookForId.get(), LendStatus.BURROWED);
            if(!burrowedBooks.isPresent()) {
                booksApprovedToBurrow.add(bookForId.get().getName());
                Lend lend = new Lend();
                lend.setMember(memberForId.get());
                lend.setBook(bookForId.get());
                lend.setStatus(LendStatus.BURROWED);
                lend.setStartOn(Instant.now());
                lend.setDueOn(Instant.now().plus(30, ChronoUnit.DAYS));
                lendRepository.save(lend);
            }
        });

        return booksApprovedToBurrow;
    }
}
