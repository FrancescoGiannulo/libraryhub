package com.libraryhub.repository;

import com.libraryhub.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByUserBookId(Long userBookId);

    @Query("SELECT r FROM Review r WHERE r.userBook.book.id = :bookId")
    List<Review> findByBookId(@Param("bookId") Long bookId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.userBook.book.id = :bookId")
    Double findAverageRatingByBookId(@Param("bookId") Long bookId);
}
