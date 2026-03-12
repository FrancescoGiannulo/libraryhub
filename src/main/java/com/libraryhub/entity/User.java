package com.libraryhub.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

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
    @Builder.Default
    private List<UserBook> userBooks=new ArrayList<>();
}
