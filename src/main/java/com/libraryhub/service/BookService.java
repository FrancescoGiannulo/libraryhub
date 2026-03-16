package com.libraryhub.service;

import com.libraryhub.dto.BookRequest;
import com.libraryhub.dto.BookResponse;
import com.libraryhub.dto.StatusUpdateRequest;
import com.libraryhub.entity.Book;
import com.libraryhub.entity.ReadingStatus;
import com.libraryhub.entity.User;
import com.libraryhub.entity.UserBook;
import com.libraryhub.exception.DuplicateResourceException;
import com.libraryhub.exception.ResourceNotFoundException;
import com.libraryhub.exception.UnauthorizedException;
import com.libraryhub.repository.BookRepository;
import com.libraryhub.repository.UserBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserBookRepository userBookRepository;
    private final OpenLibraryClient openLibraryClient;

    public BookResponse addBook(BookRequest request, User user) {
        Book book = bookRepository.findByIsbn(request.getIsbn())
                .orElseGet(() -> {
                    // Chiama Open Library — lancia ResourceNotFoundException se ISBN non trovato
                    var dto = openLibraryClient.fetchByIsbn(request.getIsbn());

                    Book newBook = new Book();
                    newBook.setIsbn(request.getIsbn());
                    newBook.setTitle(dto.getTitle() != null ? dto.getTitle() : "Unknown Title");
                    newBook.setAuthor(dto.getFirstAuthorName());
                    newBook.setGenre(dto.getFirstSubjectName());
                    newBook.setPublishedYear(dto.extractYear());
                    String coverUrl = dto.getCoverMediumUrl() != null
                            ? dto.getCoverMediumUrl()
                            : "https://covers.openlibrary.org/b/isbn/" + request.getIsbn() + "-M.jpg";
                    newBook.setCoverUrl(coverUrl);
                    return bookRepository.save(newBook);
                });

        if (userBookRepository.existsByUserAndBook(user, book)) {
            throw new DuplicateResourceException("Book already in your library");
        }

        UserBook userBook = new UserBook();
        userBook.setUser(user);
        userBook.setBook(book);
        userBook.setStatus(ReadingStatus.TO_READ);

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
        return toResponse(userBookRepository.save(userBook));
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