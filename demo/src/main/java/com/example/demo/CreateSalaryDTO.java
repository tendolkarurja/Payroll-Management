package com.example.demo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for creating new salary records
 * Restricts access to only fields needed for salary creation
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSalaryDTO {

    @NotBlank(message = "Salary grade is required")
    private String grade;

    @NotNull(message = "Basic salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Basic salary must be greater than 0")
    private Double basic;

    @NotNull(message = "HRA is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "HRA must be greater than or equal to 0")
    private Double hra;

    @NotNull(message = "DA is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "DA must be greater than or equal to 0")
    private Double da;

    @NotBlank(message = "Currency is required")
    private String currency;

    @NotNull(message = "Salary month is required")
    @Min(value = 1, message = "Salary month must be between 1 and 12")
    private Integer salaryMonth;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;
}
