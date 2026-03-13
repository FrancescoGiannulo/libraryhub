package com.libraryhub.repository;

import com.libraryhub.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//Il tag identifica l'interfaccia come repository e dunque responsabile dell'accesso ai dati del db

@Repository
public interface BookRepository extends JpaRepository<Book, Long> { //la repo gestisce dati di tipo Book identificati da PK di tipo Long

    //I metodi dichiarati sono implementati a runtime da Spring tramite proxy
    //Spring rende ogni metodo che inizia con findBy e genera query sql per il campo che segue
   //Optional forza a gestire il caso "libro non trovato"
    Optional<Book> findByIsbn(String isbn);
    //SELECT * FROM books WHERE isbn = ?

    boolean existsByIsbn(String isbn);
    //SELECT COUNT(*) > 0 FROM books WHERE isbn = ?
}

