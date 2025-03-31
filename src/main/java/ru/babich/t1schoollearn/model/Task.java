package ru.babich.t1schoollearn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private Long id;
    private String title;
    private String description;
    private Long userId;

}
