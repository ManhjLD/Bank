package com.rs.bank.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;

    private String owner;

    private Integer balance;

    private LocalDateTime created_at;

    public void setCreatedAt(LocalDateTime now) {
        this.created_at = now;
    }
}
