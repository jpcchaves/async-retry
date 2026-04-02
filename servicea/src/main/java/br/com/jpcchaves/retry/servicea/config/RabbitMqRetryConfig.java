package br.com.jpcchaves.retry.servicea.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Slf4j
@Configuration
public class RabbitMqRetryConfig {

    public static final String RETRY_EXCHANGE_NAME = "retry.exchange";
    public static final String RETRY_QUEUE_NAME = "retry.queue";
    public static final String RETRY_ROUTING_KEY = "retry.key";

    public static final String DQL_RETRY_EXCHANGE_NAME = "retry.dlx.exchange";
    public static final String DLQ_RETRY_QUEUE_NAME = "retry.dlq";
    public static final String DLQ_RETRY_ROUTING_KEY = "retry.dlq";

    @Bean
    public CustomExchange retryExchange() {
        var args = new HashMap<String, Object>();
        args.put("x-delayed-type", "direct");

        return new CustomExchange(
                RETRY_EXCHANGE_NAME,
                "x-delayed-message",
                true,
                false,
                args
        );
    }

    @Bean
    public Queue retryQueue() {
        var args = new HashMap<String, Object>();
        args.put("x-dead-letter-exchange", RETRY_EXCHANGE_NAME);
        args.put("x-dead-letter-routing-key", DLQ_RETRY_ROUTING_KEY);

        return new Queue(RETRY_QUEUE_NAME, true, false, false, args);
    }

    @Bean
    public Binding retryBinding() {
        return BindingBuilder
                .bind(retryQueue())
                .to(retryExchange())
                .with(RETRY_ROUTING_KEY)
                .noargs();
    }

    @Bean
    public Queue retryDlqQueue() {
        return new Queue(DLQ_RETRY_QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange retryDlxExchange() {
        return new DirectExchange(DQL_RETRY_EXCHANGE_NAME);
    }

    @Bean
    public Binding retryDlqBinding() {
        return BindingBuilder
                .bind(retryDlqQueue())
                .to(retryDlxExchange())
                .with(DLQ_RETRY_ROUTING_KEY);
    }
}
