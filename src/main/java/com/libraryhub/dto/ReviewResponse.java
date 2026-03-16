package com.libraryhub.dto;

public class ReviewResponse {

    private Long id;
    private Long userBookId;
    private Integer rating;
    private String comment;
    private String createdAt;

    public ReviewResponse(Long id, Long userBookId, Integer rating,
                          String comment, String createdAt) {
        this.id = id;
        this.userBookId = userBookId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public Long getUserBookId() { return userBookId; }
    public Integer getRating() { return rating; }
    public String getComment() { return comment; }
    public String getCreatedAt() { return createdAt; }
}
