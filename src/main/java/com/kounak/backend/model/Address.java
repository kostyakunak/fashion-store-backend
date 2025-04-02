package com.kounak.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipient_first_name", nullable = false)
    private String recipientFirstName;

    @Column(name = "recipient_last_name", nullable = false)
    private String recipientLastName;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}