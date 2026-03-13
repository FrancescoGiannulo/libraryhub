package com.libraryhub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//La relazione Book rappresenta l'associazione singola tra un'utente ed un libro
//mantenendo la possibilità che il libro stesso sia associato a più utenti


@Entity
@Table(name = "books")
@Getter //Evita decine di righe di boilerplate code
@Setter //rendendo impliciti metodi getter  e setter
@Builder    //permette di creare istanze tramite Book b=Book.builder().title("").author().build();
@NoArgsConstructor
@AllArgsConstructor //richiesto internamente da builder
public class Book {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //generazione degli id delegata a SQL, che li definisce per incremento
    private Long id;

    @Column(unique = true, length = 20) //le colonne verranno create come VARCHAR(20)
    private String isbn;

    @Column(nullable=false)
    private String author;

    @Column(nullable=false)
    private String title;

    @Column(name = "cover_url", length = 500)
    private String coverUrl;

    @Column(length = 100)
    private String genre;

    @Column(name = "published_year")
    private Integer publishedYear;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //Relazione OneToMany: un libro è associato anche a molti utenti
    //book, definita in UserBook, è chiave esterna per questa tabella
    //La cascade asasicura che qualsiasi operazione su book viene propagata anche a userbook
    @Builder.Default
    private List<UserBook> userBooks=new ArrayList<>();
}
