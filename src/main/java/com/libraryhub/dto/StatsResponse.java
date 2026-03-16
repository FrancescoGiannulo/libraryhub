package com.libraryhub.dto;

public class StatsResponse {

    private long totalBooks;
    private long toRead;
    private long reading;
    private long read;
    private Double averageRating;

    public StatsResponse(long totalBooks, long toRead, long reading,
                         long read, Double averageRating) {
        this.totalBooks = totalBooks;
        this.toRead = toRead;
        this.reading = reading;
        this.read = read;
        this.averageRating = averageRating;
    }

    public long getTotalBooks() { return totalBooks; }
    public long getToRead() { return toRead; }
    public long getReading() { return reading; }
    public long getRead() { return read; }
    public Double getAverageRating() { return averageRating; }
}
