package com.libraryhub.repository;

import com.libraryhub.entity.ReadingStatus;
import com.libraryhub.entity.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    List<UserBook> findByUserId(Long userId);

    List<UserBook> findByUserIdAndStatus(Long userId, ReadingStatus status);

    Optional<UserBook> findByUserIdAndBookId(Long userId, Long bookId);

    boolean existsByUserIdAndBookId(Long userId, Long bookId);
}
