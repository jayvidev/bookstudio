package com.bookstudio.payment.application;

import com.bookstudio.fine.FineApi;
import com.bookstudio.payment.application.dto.request.CreatePaymentRequest;
import com.bookstudio.payment.application.dto.request.UpdatePaymentRequest;
import com.bookstudio.payment.application.dto.response.PaymentDetailResponse;
import com.bookstudio.payment.application.dto.response.PaymentFilterOptionsResponse;
import com.bookstudio.payment.application.dto.response.PaymentListResponse;
import com.bookstudio.payment.domain.model.Payment;
import com.bookstudio.payment.domain.model.type.PaymentMethod;
import com.bookstudio.payment.infrastructure.repository.PaymentRepository;
import com.bookstudio.reader.ReaderApi;
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
public class PaymentService {
    private final CodeGenerator codeGenerator;

    private final PaymentRepository paymentRepository;
    private final ReaderApi readerApi;
    private final FineApi fineApi;

    public List<PaymentListResponse> getList() {
        return paymentRepository.findList();
    }

    public PaymentFilterOptionsResponse getFilterOptions() {
        return new PaymentFilterOptionsResponse(
                readerApi.getOptions());
    }

    public PaymentDetailResponse getDetailById(Long id) {
        PaymentDetailResponse base = paymentRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));

        return base.withFines(paymentRepository.findFineItemsByPaymentId(id));
    }

    @Transactional
    public PaymentListResponse create(CreatePaymentRequest request) {
        Payment payment = new Payment();
        readerApi.requireExists(request.readerId());
        payment.setReaderId(request.readerId());
        payment.replaceFines(request.fineIds());

        payment.setAmount(request.amount());
        payment.setPaymentDate(request.paymentDate());
        payment.setMethod(PaymentMethod.valueOf(request.method()));

        payment.setCode(codeGenerator.next(Payment.CODE_SERIES, payment.getPaymentDate()));

        Payment saved = paymentRepository.save(payment);

        fineApi.markPaid(request.fineIds());

        return toListResponse(saved);
    }

    @Transactional
    public PaymentListResponse update(Long id, UpdatePaymentRequest request) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));

        readerApi.requireExists(request.readerId());
        payment.setReaderId(request.readerId());
        payment.replaceFines(request.fineIds());

        payment.setAmount(request.amount());
        payment.setPaymentDate(request.paymentDate());
        payment.setMethod(PaymentMethod.valueOf(request.method()));

        Payment updated = paymentRepository.save(payment);

        fineApi.markPaid(request.fineIds());

        return toListResponse(updated);
    }

    private PaymentListResponse toListResponse(Payment payment) {
        return paymentRepository.findListItemById(payment.getId()).orElseThrow();
    }
}
