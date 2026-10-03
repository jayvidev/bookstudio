package com.bookstudio.circulation.reservation.application;

import com.bookstudio.circulation.reservation.application.dto.request.CreateReservationRequest;
import com.bookstudio.circulation.reservation.application.dto.request.ReservationFilter;
import com.bookstudio.circulation.reservation.application.dto.request.UpdateReservationRequest;
import com.bookstudio.circulation.reservation.application.dto.response.ReservationDetailResponse;
import com.bookstudio.circulation.reservation.application.dto.response.ReservationFilterOptionsResponse;
import com.bookstudio.circulation.reservation.application.dto.response.ReservationListResponse;
import com.bookstudio.circulation.reservation.domain.model.Reservation;
import com.bookstudio.circulation.reservation.domain.model.type.ReservationStatus;
import com.bookstudio.circulation.reservation.infrastructure.repository.ReservationRepository;
import com.bookstudio.inventory.CopyApi;
import com.bookstudio.membership.ReaderApi;
import com.bookstudio.shared.api.PageResponse;
import com.bookstudio.shared.code.CodeGenerator;
import com.bookstudio.shared.exception.ResourceNotFoundException;
import com.bookstudio.shared.paging.PageProjection;
import com.bookstudio.shared.paging.SortWhitelist;
import com.bookstudio.shared.paging.Specs;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class ReservationService {
    private static final SortWhitelist SORTABLE = SortWhitelist.of("id", "code", "reservationDate");

    private final CodeGenerator codeGenerator;

    private final ReservationRepository reservationRepository;
    private final ReaderApi readerApi;
    private final CopyApi copyApi;

    public PageResponse<ReservationListResponse> getPage(ReservationFilter filter, Pageable pageable) {
        Specification<Reservation> spec = Specification.allOf(
                Specs.containsIgnoreCase(filter.search(), "code"),
                Specs.equal("readerId", filter.readerId()),
                Specs.equal("copyId", filter.copyId()),
                Specs.equal("status", filter.status()),
                Specs.onOrAfter("reservationDate", filter.from()),
                Specs.onOrBefore("reservationDate", filter.to()));

        return PageProjection.of(
                reservationRepository.findAll(spec, SORTABLE.validate(pageable)),
                Reservation::getId,
                reservationRepository::findListByIds,
                ReservationListResponse::id);
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
