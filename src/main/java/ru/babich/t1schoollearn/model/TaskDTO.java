package ru.babich.t1schoollearn.model;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class TaskDTO {
    private Long id;

    private String title;

    private String description;

    private Long userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private String status;
}
