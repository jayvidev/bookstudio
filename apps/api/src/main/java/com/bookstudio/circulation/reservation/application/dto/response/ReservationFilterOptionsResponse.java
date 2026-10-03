package com.bookstudio.circulation.reservation.application.dto.response;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

public record ReservationFilterOptionsResponse(
    List<OptionResponse> readers
) {}
