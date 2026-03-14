package com.libraryhub.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;    //chiave primaria

    @Column(nullable=false, unique=true, length=50)
    private String username;

    @Column(nullable=false)
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    //a un utente sono associate più interfacce con i libri (una per libro)
    @Builder.Default
    private List<UserBook> userBooks=new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {    //restituisce il ruolo dell'utente
        return List.of();
    }

    @Override
    public String getPassword() {   //lombok lo implementa di default, ma userdetails lo richiede in override
        return password;
    }

    @Override
    public String getUsername() {   //stessa cosa di getpassword
        return email;
    }
}
