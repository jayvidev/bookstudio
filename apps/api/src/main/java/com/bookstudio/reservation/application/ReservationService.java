package com.bookstudio.reservation.application;

import com.bookstudio.copy.CopyApi;
import com.bookstudio.reader.ReaderApi;
import com.bookstudio.reservation.application.dto.request.CreateReservationRequest;
import com.bookstudio.reservation.application.dto.request.UpdateReservationRequest;
import com.bookstudio.reservation.application.dto.response.ReservationDetailResponse;
import com.bookstudio.reservation.application.dto.response.ReservationFilterOptionsResponse;
import com.bookstudio.reservation.application.dto.response.ReservationListResponse;
import com.bookstudio.reservation.domain.model.Reservation;
import com.bookstudio.reservation.domain.model.type.ReservationStatus;
import com.bookstudio.reservation.infrastructure.repository.ReservationRepository;
import com.bookstudio.shared.code.CodeGenerator;
import com.bookstudio.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class ReservationService {
    private final CodeGenerator codeGenerator;

    private final ReservationRepository reservationRepository;
    private final ReaderApi readerApi;
    private final CopyApi copyApi;

    public List<ReservationListResponse> getList() {
        return reservationRepository.findList();
    }

    public ReservationFilterOptionsResponse getFilterOptions() {
        return new ReservationFilterOptionsResponse(
                readerApi.getOptions());
    }

    public ReservationDetailResponse getDetailById(Long id) {
        return reservationRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID: " + id));
    }

    @Transactional
    public ReservationListResponse create(CreateReservationRequest request) {
        Reservation reservation = new Reservation();
        readerApi.requireExists(request.readerId());
        copyApi.requireExists(request.copyId());

        reservation.setReaderId(request.readerId());
        reservation.setCopyId(request.copyId());

        reservation.setReservationDate(request.reservationDate());
        reservation.setStatus(ReservationStatus.valueOf(request.status()));

        reservation.setCode(codeGenerator.next(Reservation.CODE_SERIES, reservation.getReservationDate()));

        Reservation saved = reservationRepository.save(reservation);

        return toListResponse(saved);
    }

    @Transactional
    public ReservationListResponse update(Long id, UpdateReservationRequest request) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID: " + id));

        readerApi.requireExists(request.readerId());
        copyApi.requireExists(request.copyId());

        reservation.setReaderId(request.readerId());
        reservation.setCopyId(request.copyId());

        reservation.setReservationDate(request.reservationDate());
        reservation.setStatus(ReservationStatus.valueOf(request.status()));

        Reservation updated = reservationRepository.save(reservation);
        return toListResponse(updated);
    }

    private ReservationListResponse toListResponse(Reservation reservation) {
        return reservationRepository.findListItemById(reservation.getId()).orElseThrow();
    }
}
