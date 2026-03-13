package com.libraryhub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //chiave primaria

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_book_id", nullable = false)    //il join viene effettuato con la chiave primaria della tabella referenziata
    private UserBook userBook;

    @Min(1)
    @Max(5) //questi check sono effettuati da Spring, non dal DB
    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT")  //forza tipo SQL text al posto di VARCHAR (defaault)
    private String comment;

    @CreationTimestamp  //la creazione è delegata a hibernate, poi passata in input al DB
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

