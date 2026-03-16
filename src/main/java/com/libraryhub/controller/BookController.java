package com.libraryhub.controller;

import com.libraryhub.dto.BookRequest;
import com.libraryhub.dto.BookResponse;
import com.libraryhub.dto.StatusUpdateRequest;
import com.libraryhub.entity.User;
import com.libraryhub.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookResponse> addBook(
            @RequestBody @Valid BookRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(request, user));
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookResponse>> getMyBooks(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(bookService.getMyBooks(user));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<BookResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody @Valid StatusUpdateRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(bookService.updateStatus(id, request.getStatus(), user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        bookService.deleteBook(id, user);
        return ResponseEntity.noContent().build();
    }
}
