package com.example.vaildecercise.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor

public class Event {
    @NotEmpty(message = "id Empty")
    @Size(min = 3,message = "Length more than 2")
    private String ID;

    @NotEmpty(message = "id Empty")
    @Size(min = 16 ,message = "Length more than 15")
    private String description;

    @NotNull(message = "Cannot be null")
    @Min(3)
    private int capacity;



   @NotNull(message = "start date must be not null or empty")
   @FutureOrPresent(message = "start date must be in the future")
   @JsonFormat (pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "end date must be not null or empty")
    @FutureOrPresent(message = "end date must be in the future")
    @JsonFormat (pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
