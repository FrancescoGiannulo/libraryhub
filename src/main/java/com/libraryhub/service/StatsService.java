package com.libraryhub.service;

import com.libraryhub.dto.StatsResponse;
import com.libraryhub.entity.ReadingStatus;
import com.libraryhub.entity.User;
import com.libraryhub.repository.ReviewRepository;
import com.libraryhub.repository.UserBookRepository;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final UserBookRepository userBookRepository;
    private final ReviewRepository reviewRepository;

    public StatsService(UserBookRepository userBookRepository,
                        ReviewRepository reviewRepository) {
        this.userBookRepository = userBookRepository;
        this.reviewRepository = reviewRepository;
    }

    public StatsResponse getMyStats(User user) {
        long toRead = userBookRepository.countByUserAndStatus(user, ReadingStatus.TO_READ);
        long reading = userBookRepository.countByUserAndStatus(user, ReadingStatus.READING);
        long read = userBookRepository.countByUserAndStatus(user, ReadingStatus.READ);
        long total = toRead + reading + read;
        Double averageRating = reviewRepository.findAverageRatingByUser(user);

        return new StatsResponse(total, toRead, reading, read, averageRating);
    }
}