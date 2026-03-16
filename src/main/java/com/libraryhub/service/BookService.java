package com.libraryhub.service;

import com.libraryhub.dto.BookRequest;
import com.libraryhub.dto.BookResponse;
import com.libraryhub.entity.Book;
import com.libraryhub.entity.ReadingStatus;
import com.libraryhub.entity.User;
import com.libraryhub.entity.UserBook;
import com.libraryhub.exception.DuplicateResourceException;
import com.libraryhub.exception.ResourceNotFoundException;
import com.libraryhub.exception.UnauthorizedException;
import com.libraryhub.repository.BookRepository;
import com.libraryhub.repository.UserBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserBookRepository userBookRepository;

    public BookService(BookRepository bookRepository, UserBookRepository userBookRepository) {
        this.bookRepository = bookRepository;
        this.userBookRepository = userBookRepository;
    }

    public BookResponse addBook(BookRequest request, User user) {
        // Cerca il libro per ISBN o crealo se non esiste
        Book book = bookRepository.findByIsbn(request.getIsbn())
                .orElseGet(() -> {
                    Book newBook = Book.builder()
                            .isbn(request.getIsbn())
                            .title("Unknown title")
                            .author("Unknown author")
                            .build();
                    return bookRepository.save(newBook);
                });

        // Verifica che l'utente non abbia già questo libro
        if (userBookRepository.existsByUserAndBook(user, book)) {
            throw new DuplicateResourceException("Book already in your library");
        }

        UserBook userBook = UserBook.builder()
                .user(user)
                .book(book)
                .status(ReadingStatus.TO_READ)
                .build();

        UserBook saved = userBookRepository.save(userBook);
        return toResponse(saved);
    }

    public List<BookResponse> getMyBooks(User user) {
        return userBookRepository.findByUser(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public BookResponse updateStatus(Long userBookId, ReadingStatus status, User user) {
        UserBook userBook = userBookRepository.findById(userBookId)
                .orElseThrow(() -> new ResourceNotFoundException("UserBook not found with id: " + userBookId));

        if (!userBook.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("You don't have permission to update this book");
        }

        userBook.setStatus(status);
        UserBook saved = userBookRepository.save(userBook);
        return toResponse(saved);
    }

    public void deleteBook(Long userBookId, User user) {
        UserBook userBook = userBookRepository.findById(userBookId)
                .orElseThrow(() -> new ResourceNotFoundException("UserBook not found with id: " + userBookId));

        if (!userBook.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("You don't have permission to delete this book");
        }

        userBookRepository.delete(userBook);
    }

    private BookResponse toResponse(UserBook userBook) {
        Book book = userBook.getBook();
        return new BookResponse(
                userBook.getId(),
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getCoverUrl(),
                book.getGenre(),
                book.getPublishedYear(),
                book.getDescription(),
                userBook.getStatus().name()
        );
    }
}