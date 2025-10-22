package com.example.tasks.mq.rabbit;

import com.example.tasks.repository.notifications.model.NotificationEntity;
import com.example.tasks.repository.tasks.model.TaskEntity;
import com.example.tasks.service.notifications.INotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.tasks.configuration.RabbitMQConfig.DEAD_LETTER_QUEUE;
import static com.example.tasks.configuration.RabbitMQConfig.MAX_RETRIES_COUNT;
import static com.example.tasks.configuration.RabbitMQConfig.NEW_TASKS_QUEUE;
import static com.example.tasks.repository.GlobalStatus.PENDING;
import static java.lang.System.currentTimeMillis;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
public class RabbitMQMessageListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private INotificationService iNotificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = NEW_TASKS_QUEUE, concurrency = "2-4")
    public void receive(final String data) throws JsonProcessingException {

        log.debug("Received message: {}", data);

        final TaskEntity task = objectMapper.convertValue(objectMapper.readTree(data), TaskEntity.class);

        iNotificationService.save(
                NotificationEntity.builder()
                                  .user(task.getUser())
                                  .status(PENDING)
                                  .created(currentTimeMillis())
                                  .data("New task: " + task.getId())
                                  .build()
        );
    }

    @RabbitListener(queues = DEAD_LETTER_QUEUE)
    public void handleErrorMessage(final Message message) {

        log.error("Received failed message: {}", message.toString());

        processFailedMessagesRetryHeaders(message);
    }

    private void processFailedMessagesRetryHeaders(final Message message) {

        final Integer retriesCnt = ofNullable(
                (Integer)message.getMessageProperties().getHeaders().get("x-retries-count")
        ).orElse(1);

        if (retriesCnt > MAX_RETRIES_COUNT) {
            return;
        }

        log.debug("Retrying message for the {} time", retriesCnt);

        message.getMessageProperties().getHeaders().put("x-retries-count", retriesCnt - 1);

        rabbitTemplate.send(NEW_TASKS_QUEUE, message.getMessageProperties().getReceivedRoutingKey(), message);
    }
}