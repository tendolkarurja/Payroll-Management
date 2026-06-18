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
 * DTO for updating salary records
 * Restricts access to only updatable fields (no employeeId, no salaryId)
 * All fields are optional to support partial updates
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UpdateSalaryDTO {
    @NotBlank(message = "Salary grade is required")
    private String grade;

    @DecimalMin(value = "0.0", inclusive = false, message = "Basic salary must be greater than 0")
    private Double basic;

    @DecimalMin(value = "0.0", inclusive = false, message = "Bonus must be greater than 0")
    private Double bonus;

    @DecimalMin(value = "0.0", inclusive = false, message = "Deductions must be greater than 0")
    private Double deductions;
}
