package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for Salary entity using use-case-specific DTOs
 * - EmployeeSalaryDTO: For creation and employee view
 * - UpdateSalaryDTO: For updates with bonus/deductions
 * - HRSalaryDTO: For HR authorized access
 */
@Service
@Transactional
@Slf4j
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private SalaryRepository salaryRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    /**
     * Get salary by ID - returns HRSalaryDTO for authorized access
     */
    @Override
    @Transactional(readOnly = true)
    public HRSalaryDTO getSalaryById(Long id) {
        log.debug("Fetching salary with ID: {}", id);
        Salary salary = salaryRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Salary not found with ID: {}", id);
                    return new RuntimeException("Salary ID not found");
                });
        return convertToHRDTO(salary);
    }

    /**
     * Get all salaries - returns HRSalaryDTO for authorized access
     */
    @Override
    @Transactional(readOnly = true)
    public List<HRSalaryDTO> getAllSalaries() {
        log.debug("Fetching all salaries");
        List<Salary> salaries = salaryRepo.findAll();
        return salaries.stream()
                .map(this::convertToHRDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create new salary - accepts EmployeeSalaryDTO and requires employeeId
     */
    @Override
    public HRSalaryDTO createNewSalary(EmployeeSalaryDTO dto, Long employeeId) {
        log.info("Creating new salary for employee ID: {}", employeeId);

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> {
                    log.error("Employee not found with ID: {}", employeeId);
                    return new RuntimeException("Employee not found");
                });

        Salary salary = new Salary();
        salary.setBasic(dto.getBasic());
        salary.setHra(dto.getHra() != null ? dto.getHra() : 0.0);
        salary.setDa(dto.getDa() != null ? dto.getDa() : 0.0);
        salary.setDeductions(dto.getDeductions() != null ? dto.getDeductions() : 0.0);

        // Calculate total salary: basic + hra + da - deductions
        Double total = calculateTotalSalary(salary.getBasic(), salary.getHra(), salary.getDa(), salary.getDeductions());
        salary.setTotal(total);

        salary.setCurrency(dto.getCurrency() != null ? dto.getCurrency() : "INR");
        salary.setSalaryMonth(dto.getSalaryMonth());
        salary.setEmployee(employee);
        salary.setGrade(""); // Grade can be updated later

        Salary saved = salaryRepo.save(salary);
        log.info("Salary created successfully with ID: {}", saved.getSalaryId());
        return convertToHRDTO(saved);
    }

    /**
     * Update salary - accepts UpdateSalaryDTO with bonus and deductions
     * Note: bonus and deductions are used to update salary components
     */
    @Override
    public HRSalaryDTO updateSalary(UpdateSalaryDTO dto, Long id) {
        log.info("Updating salary with ID: {}", id);

        Salary salary = salaryRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Salary not found with ID: {}", id);
                    return new RuntimeException("Salary ID not found");
                });

        // Update allowed fields
        if (dto.getGrade() != null) {
            salary.setGrade(dto.getGrade());
        }
        if (dto.getBasic() != null) {
            salary.setBasic(dto.getBasic());
        }
        if (dto.getBonus() != null) {
            salary.setHra(dto.getBonus()); // bonus maps to HRA for update operations
        }
        if (dto.getDeductions() != null) {
            salary.setDeductions(dto.getDeductions());
        }

        // Recalculate total whenever salary components change
        Double total = calculateTotalSalary(salary.getBasic(), salary.getHra(), salary.getDa(), salary.getDeductions());
        salary.setTotal(total);

        Salary updated = salaryRepo.save(salary);
        log.info("Salary updated successfully with ID: {}", id);
        return convertToHRDTO(updated);
    }

    /**
     * Delete salary
     */
    @Override
    public void deleteSalary(Long id) {
        log.info("Deleting salary with ID: {}", id);
        if (!salaryRepo.existsById(id)) {
            log.error("Salary not found with ID: {}", id);
            throw new RuntimeException("Salary ID not found");
        }
        salaryRepo.deleteById(id);
        log.info("Salary deleted successfully with ID: {}", id);
    }

    /**
     * Get salary by employee ID - returns HRSalaryDTO for authorized HR access
     */
    @Override
    @Transactional(readOnly = true)
    public List<HRSalaryDTO> getSalaryByEmployeeId(Long employeeId) {
        log.debug("Fetching salaries for employee ID: {}", employeeId);

        if (!employeeRepo.existsById(employeeId)) {
            log.error("Employee not found with ID: {}", employeeId);
            throw new RuntimeException("Employee not found");
        }

        return salaryRepo.findAll()
                .stream()
                .filter(salary -> salary.getEmployee() != null && salary.getEmployee().getId().equals(employeeId))
                .map(this::convertToHRDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get salary view for employee - returns EmployeeSalaryDTO (employee perspective)
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeSalaryDTO> getEmployeeSalaryByEmployeeId(Long employeeId) {
        log.debug("Fetching employee salary view for employee ID: {}", employeeId);

        if (!employeeRepo.existsById(employeeId)) {
            log.error("Employee not found with ID: {}", employeeId);
            throw new RuntimeException("Employee not found");
        }

        return salaryRepo.findAll()
                .stream()
                .filter(salary -> salary.getEmployee() != null && salary.getEmployee().getId().equals(employeeId))
                .map(this::convertToEmployeeDTO)
                .collect(Collectors.toList());
    }

    /**
     * Calculate total salary: basic + hra + da - deductions
     */
    @Override
    public Double calculateTotalSalary(Double basic, Double hra, Double da, Double deductions) {
        if (basic == null) {
            return 0.0;
        }
        double hraAmount = hra != null ? hra : 0.0;
        double daAmount = da != null ? da : 0.0;
        double deductionsAmount = deductions != null ? deductions : 0.0;
        double total = basic + hraAmount + daAmount - deductionsAmount;
        log.debug("Calculated total salary: basic={}, hra={}, da={}, deductions={}, total={}", 
                  basic, hraAmount, daAmount, deductionsAmount, total);
        return total;
    }

    /**
     * Convert Salary entity to HRSalaryDTO (full details for HR/authorized users)
     */
    private HRSalaryDTO convertToHRDTO(Salary salary) {
        if (salary == null) {
            return null;
        }
        HRSalaryDTO dto = new HRSalaryDTO();
        dto.setSalaryId(salary.getSalaryId());
        dto.setGrade(salary.getGrade());
        dto.setBasic(salary.getBasic());
        dto.setHra(salary.getHra());
        dto.setDa(salary.getDa());
        dto.setTotal(salary.getTotal());
        dto.setCurrency(salary.getCurrency());
        dto.setSalaryMonth(salary.getSalaryMonth());

        if (salary.getEmployee() != null) {
            dto.setEmployeeId(salary.getEmployee().getId());
        }

        return dto;
    }

    /**
     * Convert Salary entity to EmployeeSalaryDTO (employee perspective)
     */
    private EmployeeSalaryDTO convertToEmployeeDTO(Salary salary) {
        if (salary == null) {
            return null;
        }
        EmployeeSalaryDTO dto = new EmployeeSalaryDTO();
        dto.setBasic(salary.getBasic());
        dto.setHra(salary.getHra());
        dto.setDa(salary.getDa());
        dto.setDeductions(salary.getDeductions());
        dto.setTotal(salary.getTotal());
        dto.setCurrency(salary.getCurrency());
        dto.setSalaryMonth(salary.getSalaryMonth());

        return dto;
    }
}
