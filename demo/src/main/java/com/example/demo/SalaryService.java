package com.example.demo;

import java.util.List;

/**
 * Service interface for Salary entity with use-case-specific DTOs
 * - EmployeeSalaryDTO: For creation and employee view operations
 * - UpdateSalaryDTO: For update operations with bonus/deductions
 * - HRSalaryDTO: For HR read operations (full details)
 */
public interface SalaryService {

    /**
     * Get salary by ID - returns full HR details for authorized access
     */
    HRSalaryDTO getSalaryById(Long id);

    /**
     * Get all salaries - returns full HR details for authorized access
     */
    List<HRSalaryDTO> getAllSalaries();

    /**
     * Create new salary - accepts EmployeeSalaryDTO with required fields
     */
    HRSalaryDTO createNewSalary(EmployeeSalaryDTO dto, Long employeeId);

    /**
     * Update salary - accepts only updatable fields (bonus, deductions)
     */
    HRSalaryDTO updateSalary(UpdateSalaryDTO dto, Long id);

    /**
     * Delete salary
     */
    void deleteSalary(Long id);

    /**
     * Get salary by employee ID - returns HRSalaryDTO for authorized access
     */
    List<HRSalaryDTO> getSalaryByEmployeeId(Long employeeId);

    /**
     * Get salary view for employee - returns EmployeeSalaryDTO (employee perspective)
     */
    List<EmployeeSalaryDTO> getEmployeeSalaryByEmployeeId(Long employeeId);

    /**
     * Calculate total salary from components
     */
    Double calculateTotalSalary(Double basic, Double hra, Double da, Double deductions);
}
