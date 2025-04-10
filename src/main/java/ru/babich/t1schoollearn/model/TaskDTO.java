package ru.babich.t1schoollearn.model;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class TaskDTO {
    private Long id;

    private String title;

    private String description;

    private Long userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private String status;
}
