package com.example.tasks.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TaskCreateRequest {

    @NotBlank(message = "Название задачи не может быть пустым")
    private String name;

    @NotNull(message = "Конечный timestamp не может отсутствовать")
    private LocalDateTime target;

    @NotNull(message = "ID пользователя не может отсутствовать")
    @Positive(message = "ID пользователя не может быть отрицательным")
    private Long user;

    private String status;
}
