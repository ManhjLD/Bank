package com.rs.bank.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @ManyToOne
    private Account fromAccount;
    @ManyToOne
    private Account toAccount;
    private double amount;
    private String type; // "DEPOSIT", "WITHDRAW", "TRANSFER"
    private LocalDateTime timestamp;
}
