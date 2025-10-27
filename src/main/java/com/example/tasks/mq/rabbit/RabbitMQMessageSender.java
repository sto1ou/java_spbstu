package com.example.tasks.mq.rabbit;

import com.example.tasks.mq.MessageSender;
import com.example.tasks.repository.tasks.model.TaskEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.example.tasks.configuration.RabbitMQConfig.NEW_TASKS_EXCHANGE;
import static com.example.tasks.configuration.RabbitMQConfig.NEW_TASKS_KEY;

@Slf4j
@Service
public class RabbitMQMessageSender implements MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void send(final String data) {

        rabbitTemplate.convertAndSend(NEW_TASKS_EXCHANGE, NEW_TASKS_KEY, data);

        log.debug("Sent message: {}", data);
    }

    @Async
    @Override
    public void send(final TaskEntity task) throws JsonProcessingException {
        send(objectMapper.writeValueAsString(task));
    }
}
