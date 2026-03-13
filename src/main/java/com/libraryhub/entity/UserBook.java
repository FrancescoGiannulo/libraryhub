package com.libraryhub.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_books", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "book_id"}))
//unique constraint assicura che non esistano coppie (utente, libro) uguali. per ogni utente esiste una sola relazione (al più)
// con un singolo libro
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBook {
    //include tutti i lati inversi delle relazioni con tabelle
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable=false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReadingStatus status;

    @CreationTimestamp
    @Column(name = "added_at", nullable = false, updatable = false)
    private LocalDateTime addedAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @OneToOne(mappedBy = "userBook", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Review review;
}
