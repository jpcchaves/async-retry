package br.com.jpcchaves.retry.servicea.domain.model.enums;

import lombok.Getter;

@Getter
public enum RetryDuration {
    FIFTY_MINUTES(900000),
    ONE_HOUR(3600000),
    SIX_HOURS(21600000);

    private final int durationMs;

    RetryDuration(int durationMs) {
        this.durationMs = durationMs;
    }
}
