package com.blesk.accountservice.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQ {

    @Value("${blesk.rabbitmq.authorization-exchange}")
    private String authorizationExchange;

    @Value("${blesk.rabbitmq.dead-letter-exchange}")
    private String deadLetterExchange;

    @Value("${blesk.rabbitmq.authorization-queue}")
    private String authorizationQueue;

    @Value("${blesk.rabbitmq.dead-letter-queue}")
    private String deadLetterQueue;

    @Value("${blesk.rabbitmq.authorization-routing-key}")
    private String authorizationRoutingkey;

    @Value("${blesk.rabbitmq.dead-letter-routing-key}")
    private String deadLetterRoutingkey;

    @Value("${blesk.rabbitmq.dead-letter-routing-key-argument}")
    private String deadLetterRoutingKeyArgument;

    @Value("${blesk.rabbitmq.dead-letter-exchange-argument}")
    private String deadLetterExchangeArgument;

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(deadLetterExchange);
    }

    @Bean
    DirectExchange authorizationExchange() {
        return new DirectExchange(authorizationExchange);
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(deadLetterQueue).build();
    }

    @Bean
    Queue authorizationQueue() {
        return QueueBuilder.durable(authorizationQueue).withArgument(deadLetterExchangeArgument, deadLetterExchange)
                .withArgument(deadLetterRoutingKeyArgument, deadLetterRoutingkey).build();
    }

    @Bean
    Binding deadLetterQueueBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(deadLetterRoutingkey);
    }

    @Bean
    Binding authorizationQueueBinding() {
        return BindingBuilder.bind(authorizationQueue()).to(authorizationExchange()).with(authorizationRoutingkey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}