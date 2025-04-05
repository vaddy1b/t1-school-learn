package ru.babich.t1schoollearn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private Long userId;
}
