package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EmployeeSalaryDTO {
    private Integer salaryMonth;
    private Double basic;
    private Double hra;
    private Double da;
    private Double deductions;
    private Double total;
    private String currency;
    }
