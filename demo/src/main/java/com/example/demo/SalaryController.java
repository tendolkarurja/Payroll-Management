package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * REST Controller for Salary operations
 * Uses use-case-specific DTOs to restrict data access:
 * - EmployeeSalaryDTO: For creation and employee view
 * - UpdateSalaryDTO: For PUT operations with bonus/deductions
 * - HRSalaryDTO: For HR authorized read operations
 */
@RestController
@RequestMapping("/api/salaries")
@CrossOrigin(origins = "http://localhost:3000")
public class SalaryController {
    
    @Autowired
    private SalaryService salaryService;

    /**
     * Get all salaries - Full details for authorized HR/admin access
     * @return List of HRSalaryDTO with all salary information
     */
    @GetMapping
    public List<HRSalaryDTO> getAllSalaries() {
        return salaryService.getAllSalaries();
    }

    /**
     * Get salary by ID - Full details for authorized access
     * @param id Salary ID
     * @return HRSalaryDTO with complete salary information
     */
    @GetMapping("/{id}")
    public HRSalaryDTO getSalaryById(@PathVariable Long id) {
        return salaryService.getSalaryById(id);
    }

    /**
     * Create new salary - Accepts EmployeeSalaryDTO and requires employeeId
     * Automatically calculates total (basic + hra + da - deductions)
     * @param employeeId Employee ID (path variable or query parameter)
     * @param employeeSalaryDTO EmployeeSalaryDTO with salary details
     * @return HRSalaryDTO with created salary details
     */
    @PostMapping("/employee/{employeeId}")
    public HRSalaryDTO createSalary(
            @PathVariable Long employeeId,
            @Valid @RequestBody EmployeeSalaryDTO employeeSalaryDTO) {
        return salaryService.createNewSalary(employeeSalaryDTO, employeeId);
    }

    /**
     * Update salary - Accepts only updatable fields (grade, basic, bonus, deductions)
     * Total is automatically recalculated when components change
     * @param id Salary ID
     * @param updateDTO UpdateSalaryDTO with fields to update
     * @return HRSalaryDTO with updated salary details
     */
    @PutMapping("/{id}")
    public HRSalaryDTO updateSalary(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSalaryDTO updateDTO) {
        return salaryService.updateSalary(updateDTO, id);
    }

    /**
     * Delete salary by ID
     * @param id Salary ID to delete
     */
    @DeleteMapping("/{id}")
    public void deleteSalary(@PathVariable Long id) {
        salaryService.deleteSalary(id);
    }

    /**
     * Get all salaries for an employee - Full HR details for authorized access
     * @param employeeId Employee ID
     * @return List of HRSalaryDTO for the employee
     */
    @GetMapping("/hr/employee/{employeeId}")
    public List<HRSalaryDTO> getSalaryByEmployeeId(@PathVariable Long employeeId) {
        return salaryService.getSalaryByEmployeeId(employeeId);
    }

    /**
     * Get salary view for employee - Employee perspective with salary components
     * Returns: basic, hra, da, deductions, total, currency, month
     * @param employeeId Employee ID
     * @return List of EmployeeSalaryDTO from employee perspective
     */
    @GetMapping("/employee/{employeeId}/view")
    public List<EmployeeSalaryDTO> getEmployeeSalaryByEmployeeId(@PathVariable Long employeeId) {
        return salaryService.getEmployeeSalaryByEmployeeId(employeeId);
    }

    /**
     * Calculate total salary from components (basic + hra + da - deductions)
     * Utility endpoint for salary calculation without creating records
     * @param employeeSalaryDTO Request body with salary components
     * @return Calculated total salary
     */
    @PostMapping("/calculate")
    public Double calculateTotalSalary(@RequestBody EmployeeSalaryDTO employeeSalaryDTO) {
        return salaryService.calculateTotalSalary(
                employeeSalaryDTO.getBasic(),
                employeeSalaryDTO.getHra(),
                employeeSalaryDTO.getDa(),
                employeeSalaryDTO.getDeductions());
    }
}
