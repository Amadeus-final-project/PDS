package com.example.pds.model.employees.driver.driverDTO;


import com.example.pds.model.vacations.VacationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class DriverRequestVacationDTO {

    private int id;

    @NotBlank(message = "Please type vacation start date.")
    private LocalDate startDate;

    @NotBlank(message = "Please type vacation end date.")
    private LocalDate endDate;

    @NotBlank(message = "Please type vacation description.")
    private String description;

    @NotBlank(message = "Please select vacation type.")
    private String vacationType;
}
