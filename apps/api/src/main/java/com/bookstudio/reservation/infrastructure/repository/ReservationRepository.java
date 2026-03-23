package com.bookstudio.reservation.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.reservation.application.dto.response.ReservationDetailResponse;
import com.bookstudio.reservation.application.dto.response.ReservationListResponse;
import com.bookstudio.reservation.domain.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("""
        SELECT 
            r.id AS id,
            r.code AS code,

            rd.id AS readerId,
            rd.code AS readerCode,
            CONCAT(rd.firstName, ' ', rd.lastName) AS readerFullName,

            c.code AS copyCode,

            r.reservationDate AS reservationDate,
            r.status AS status
        FROM Reservation r
        JOIN r.reader rd
        JOIN r.copy c
        ORDER BY r.id DESC
    """)
    List<ReservationListResponse> findList();

    @Query("""
        SELECT 
            r.id AS id,
            r.code AS code,

            rd.id AS readerId,
            rd.code AS readerCode,
            CONCAT(rd.firstName, ' ', rd.lastName) AS readerFullName,

            c.id AS copyId,
            c.code AS copyCode,
            c.barcode AS copyBarcode,
            c.status AS copyStatus,

            r.reservationDate AS reservationDate,
            r.status AS status
        FROM Reservation r
        JOIN r.reader rd
        JOIN r.copy c
        WHERE r.id = :id
    """)
    Optional<ReservationDetailResponse> findDetailById(Long id);
}
