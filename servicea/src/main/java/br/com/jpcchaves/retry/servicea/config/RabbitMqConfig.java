package br.com.jpcchaves.retry.servicea.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMqConfig {

    public static final String RETRY_LISTENER_QUEUE_NAME = "process.queue";
    public static final String RETRY_QUEUE_5MIN_NAME = "process.retry.5m";
    public static final String RETRY_QUEUE_1HR_NAME = "process.retry.1h";
    public static final String RETRY_QUEUE_1D_NAME = "process.retry.1d";

    public static final String DLQ = "process.dlq";

    @Bean
    public Queue retryListenerQueue() {
        log.info("Creating retry listener queue");
        return QueueBuilder.durable(RETRY_LISTENER_QUEUE_NAME).build();
    }

    @Bean
    public Queue retry5m() {
        log.info("Creating 5m retry queue");

        return QueueBuilder.durable(RETRY_QUEUE_5MIN_NAME)
                .withArgument("x-message-ttl", 300000)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", RETRY_LISTENER_QUEUE_NAME)
                .build();
    }

    @Bean
    public Queue retry1h() {
        log.info("Creating 1hr retry queue");

        return QueueBuilder.durable(RETRY_QUEUE_1HR_NAME)
                .withArgument("x-message-ttl", 3600000)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", RETRY_LISTENER_QUEUE_NAME)
                .build();
    }

    @Bean
    public Queue retry1d() {
        log.info("Creating 1d retry queue");

        return QueueBuilder.durable(RETRY_QUEUE_1D_NAME)
                .withArgument("x-message-ttl", 86400000)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", RETRY_LISTENER_QUEUE_NAME)
                .build();
    }

    @Bean
    public Queue dlq() {
        return QueueBuilder.durable(DLQ).build();
    }
}
