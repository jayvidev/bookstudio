package com.bookstudio.circulation.reservation.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.circulation.reservation.application.dto.response.ReservationDetailResponse;
import com.bookstudio.circulation.reservation.application.dto.response.ReservationListResponse;
import com.bookstudio.circulation.reservation.domain.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    String LIST_SELECT = """
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
        JOIN Reader rd ON rd.id = r.readerId
        JOIN Copy c ON c.id = r.copyId
        """;

    @Query(LIST_SELECT + "ORDER BY r.id DESC")
    List<ReservationListResponse> findList();

    @Query(LIST_SELECT + "WHERE r.id = :id")
    Optional<ReservationListResponse> findListItemById(Long id);

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
        JOIN Reader rd ON rd.id = r.readerId
        JOIN Copy c ON c.id = r.copyId
        WHERE r.id = :id
    """)
    Optional<ReservationDetailResponse> findDetailById(Long id);
}
