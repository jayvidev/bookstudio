package com.bookstudio.loan.application;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.bookstudio.copy.CopyApi;
import com.bookstudio.loan.domain.model.CopyEffect;

/**
 * Collects the effects that loan changes have on copies and applies them in
 * the copy module in one go: releases first, so a copy freed by one change can
 * be lent by another in the same request.
 */
final class CopyChanges {
    private final Map<CopyEffect, List<Long>> copyIdsByEffect = new EnumMap<>(CopyEffect.class);

    void record(Long copyId, CopyEffect effect) {
        if (effect != CopyEffect.NONE) {
            copyIdsByEffect.computeIfAbsent(effect, e -> new ArrayList<>()).add(copyId);
        }
    }

    void applyTo(CopyApi copyApi) {
        apply(CopyEffect.RELEASE, copyApi::release);
        apply(CopyEffect.MARK_LOST, copyApi::markLost);
        apply(CopyEffect.LEND, copyApi::lend);
    }

    private void apply(CopyEffect effect, Consumer<List<Long>> action) {
        List<Long> copyIds = copyIdsByEffect.get(effect);
        if (copyIds != null) {
            action.accept(copyIds);
        }
    }
}
