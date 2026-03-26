package br.com.jpcchaves.retry.servicea.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetryMqEventModel<T> {

    private String eventId;
    private T payload;
    private int attemptCount;
    private Throwable lastException;
    private List<Throwable> exceptions = new ArrayList<>();
}
