package com.rs.bank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Account {
    @Id
    private String accountNumber;

    @ManyToOne
    private Customer owner;

    private Integer balance;

    private LocalDateTime createdAt;
}
