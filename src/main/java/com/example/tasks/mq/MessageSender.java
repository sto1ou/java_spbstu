package com.example.tasks.mq;

import com.example.tasks.repository.tasks.model.TaskEntity;

public interface MessageSender {

    void send(final String data);

    void send(final TaskEntity task) throws Exception;
}
