package com.bookstudio.billing.fine.application;

import java.math.BigDecimal;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/**
 * @param dailyOverdueFine amount charged per day an item is returned late
 */
@Validated
@ConfigurationProperties("app.billing")
public record BillingProperties(@NotNull @DecimalMin("0.00") BigDecimal dailyOverdueFine) {
}
