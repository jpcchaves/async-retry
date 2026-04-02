package br.com.jpcchaves.retry.servicea.domain.model.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RetryDuration {
    FIVE_MINUTES(1, 300000),
    ONE_HOUR(2, 3600000),
    SIX_HOURS(3, 21600000);

    private final int retryCount;
    private final int durationMs;

    RetryDuration(int retryCount, int durationMs) {
        this.retryCount = retryCount;
        this.durationMs = durationMs;
    }

    public static int getDelayFromRetryCount(int retryCount) {
        return Arrays.stream(values())
                .filter(v -> v.getRetryCount() == retryCount)
                .findFirst()
                .map(RetryDuration::getDurationMs)
                .orElseThrow(() -> new IllegalArgumentException("Invalid retry count"));
    }
}
