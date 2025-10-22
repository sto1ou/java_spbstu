package com.example.tasks.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.UUID.randomUUID;

@Configuration
public class RabbitMQConfig {

    public static final String NEW_TASKS_EXCHANGE = "exchange";
    public static final String NEW_TASKS_QUEUE = "new-tasks-queue";
    public static final String NEW_TASKS_KEY = randomUUID().toString();
    public static final String DEAD_LETTER_QUEUE = "dead-letter-queue";
    public static final Integer MAX_RETRIES_COUNT = 2;

    @Bean
    public TopicExchange newTasksExchange() {
        return new TopicExchange(NEW_TASKS_EXCHANGE);
    }

    @Bean
    public Queue newTasksQueue() {

        return QueueBuilder.durable(NEW_TASKS_QUEUE)
                           .withArgument("x-dead-letter-exchange", "")
                           .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                           .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    @Bean
    public Binding newTasksBinding() {
        return BindingBuilder.bind(newTasksQueue()).to(newTasksExchange()).with(NEW_TASKS_KEY);
    }
}