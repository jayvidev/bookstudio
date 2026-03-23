package com.bookstudio.reader.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

import java.time.LocalDate;

import com.bookstudio.reader.domain.model.type.ReaderGender;
import com.bookstudio.reader.domain.model.type.ReaderStatus;
import com.bookstudio.reader.domain.model.type.ReaderType;

@Entity
@Table(name = "readers")
@Data
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = false, updatable = false)
    private String code;

    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, length = 9)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private ReaderGender gender;

    @Enumerated(EnumType.STRING)
    private ReaderType type;

    @Enumerated(EnumType.STRING)
    private ReaderStatus status;

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }
}