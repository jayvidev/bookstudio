package com.bookstudio.reservation.domain.model;

import java.time.LocalDate;

import com.bookstudio.reservation.domain.model.type.ReservationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import com.bookstudio.shared.code.CodeSeries;

@Entity
@Table(name = "reservations")
@Data
public class Reservation {
    public static final CodeSeries CODE_SERIES = new CodeSeries("RES");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String code;

    @Column(name = "reader_id", nullable = false)
    private Long readerId;

    @Column(name = "copy_id", nullable = false)
    private Long copyId;

    private LocalDate reservationDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
