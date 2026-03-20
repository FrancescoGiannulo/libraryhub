package com.libraryhub.service;

import com.libraryhub.dto.ReviewRequest;
import com.libraryhub.dto.ReviewResponse;
import com.libraryhub.entity.Review;
import com.libraryhub.entity.User;
import com.libraryhub.entity.UserBook;
import com.libraryhub.exception.DuplicateResourceException;
import com.libraryhub.exception.ResourceNotFoundException;
import com.libraryhub.exception.UnauthorizedException;
import com.libraryhub.repository.ReviewRepository;
import com.libraryhub.repository.UserBookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserBookRepository userBookRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         UserBookRepository userBookRepository) {
        this.reviewRepository = reviewRepository;
        this.userBookRepository = userBookRepository;
    }

    @Transactional
    public ReviewResponse addReview(ReviewRequest request, User user) {
        UserBook userBook = userBookRepository.findById(request.getUserBookId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "UserBook not found with id: " + request.getUserBookId()));

        if (!userBook.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("You don't have permission to review this book");
        }

        if (reviewRepository.existsByUserBook(userBook)) {
            throw new DuplicateResourceException("You have already reviewed this book");
        }

        Review review = Review.builder()
                .userBook(userBook)
                .rating(request.getRating())
                .comment(request.getComment())
                .build();

        return toResponse(reviewRepository.save(review));
    }

    @Transactional
    public ReviewResponse updateReview(Long reviewId, ReviewRequest request, User user) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Review not found with id: " + reviewId));

        if (!review.getUserBook().getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("You don't have permission to update this review");
        }

        review.setRating(request.getRating());
        review.setComment(request.getComment());

        return toResponse(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByBook(Long bookId) {
        return reviewRepository.findByBookId(bookId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ReviewResponse toResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getUserBook().getId(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt().toString()
        );
    }
}
