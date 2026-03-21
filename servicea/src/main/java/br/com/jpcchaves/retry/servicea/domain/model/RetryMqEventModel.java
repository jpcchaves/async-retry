package br.com.jpcchaves.retry.servicea.domain.model;

public class RetryMqEventModel {

    private String eventId;
    private Object payload;
    private int retryCount;
    private int maxRetries = 3;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    @Override
    public String toString() {
        return "RetryMqEventModel{" +
                "eventId='" + eventId + '\'' +
                ", payload=" + payload +
                ", retryCount=" + retryCount +
                ", maxRetries=" + maxRetries +
                '}';
    }
}
