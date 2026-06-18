package com.example.demo;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for returning salary data to clients
 * Includes all salary information for administrative/HR access
 * Suitable for authorized users who need complete salary details
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HRSalaryDTO {

    private Long salaryId;

    private String grade;

    private Double basic;

    private Double hra;

    private Double da;

    private Double total;

    private String currency;

    private Integer salaryMonth;
    
    private Long employeeId;
}
