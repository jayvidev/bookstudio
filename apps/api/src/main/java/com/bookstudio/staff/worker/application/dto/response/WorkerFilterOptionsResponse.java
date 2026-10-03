package com.bookstudio.staff.worker.application.dto.response;

import java.util.List;

import com.bookstudio.shared.response.OptionResponse;

public record WorkerFilterOptionsResponse(
    List<OptionResponse> roles
) {}
