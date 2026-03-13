package com.libraryhub.repository;

import com.libraryhub.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {//Gestisce dati Review identificati da PK Long

    Optional<Review> findByUserBookId(Long userBookId); //Già visto in BookRepository

    //SELECT r.* FROM reviews r
    //JOIN user_books ub ON r.user_book_id = ub.id
    //JOIN books b ON ub.book_id = b.id
    //WHERE b.id = ?
    @Query("SELECT r FROM Review r WHERE r.userBook.book.id = :bookId")
    List<Review> findByBookId(@Param("bookId") Long bookId);

    //Query uguale sia in JPQL che SQL
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.userBook.book.id = :bookId")
    Double findAverageRatingByBookId(@Param("bookId") Long bookId);
}
