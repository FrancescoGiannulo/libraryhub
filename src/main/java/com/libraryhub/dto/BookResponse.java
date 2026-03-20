package com.libraryhub.dto;

public class BookResponse {

    private Long userBookId;
    private Long bookId;
    private String isbn;
    private String title;
    private String author;
    private String coverUrl;
    private String genre;
    private Integer publishedYear;
    private String description;
    private String status;
    private Long reviewId;
    private Integer rating;
    private String comment;

    public BookResponse(Long userBookId, Long bookId, String isbn, String title,
                        String author, String coverUrl, String genre,
                        Integer publishedYear, String description, String status,
                        Long reviewId, Integer rating, String comment) {
        this.userBookId = userBookId;
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.coverUrl = coverUrl;
        this.genre = genre;
        this.publishedYear = publishedYear;
        this.description = description;
        this.status = status;
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
    }

    public Long getUserBookId() { return userBookId; }
    public Long getBookId() { return bookId; }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCoverUrl() { return coverUrl; }
    public String getGenre() { return genre; }
    public Integer getPublishedYear() { return publishedYear; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public Long getReviewId() { return reviewId; }
    public Integer getRating() { return rating; }
    public String getComment() { return comment; }
}
