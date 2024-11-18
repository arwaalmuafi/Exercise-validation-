package com.example.valdecercise.Model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Project {
    @NotEmpty(message = "id Empty")
    @Size(min = 3,message = "should enter more then 3")
    private String ID;

    @NotEmpty(message = "title Empty")
    @Size(min = 9,message = "Length more than 8")
    private String title;

    @NotEmpty(message = "Cannot be null")
    @Size(min = 16,message = "Length more than 15")
    private String description;

    @NotEmpty(message = "Cannot be null")
    @Pattern(
            regexp = "^(Not Started|In Progress|Completed)$",
            message = "Status must be 'Not Started', 'In Progress', or 'Completed'."
    )
    private String  status;
    @NotEmpty(message = "Cannot be null")
    @Size(min = 7,message = "Length more than 7")
    private String companyName;
}