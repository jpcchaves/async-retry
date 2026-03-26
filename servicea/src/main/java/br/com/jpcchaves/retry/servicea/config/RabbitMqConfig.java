package br.com.jpcchaves.retry.servicea.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Slf4j
@Configuration
public class RabbitMqConfig {

    public static final String RETRY_EXCHANGE_NAME = "retry.exchange";
    public static final String RETRY__QUEUE_NAME = "retry.queue";
    public static final String RETRY_ROUTING_KEY = "example.retry";

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
        return new Queue(RETRY__QUEUE_NAME);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(retryQueue())
                .to(retryExchange())
                .with(RETRY_ROUTING_KEY)
                .noargs();
    }

}
