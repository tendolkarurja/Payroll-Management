package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository empRepo;

    @Autowired
    DepartmentRepository deptRepo;

    // ✅ Create Employee from DTO and return DTO
    @Override
    public EmployeeDTO createEmployee(EmployeeDTO dto) {
        Employee emp = new Employee();
        emp.setName(dto.getName());
        emp.setRole(dto.getRole());

        if (dto.getDeptId() != null) {
            Department dept = deptRepo.findById(dto.getDeptId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            emp.setDepartment(dept);
        }

        Employee saved = empRepo.save(emp);
        return convertToDTO(saved);
    }

    // ✅ Get All Employees as DTO list
    @Override
    public List<EmployeeDTO> getAllEmployee() {
        return empRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Get Employee by ID as DTO
    @Override
    public EmployeeDTO getEmployeeByID(Long id) {
        Employee emp = empRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return convertToDTO(emp);
    }

    // ✅ Update Employee using DTO and return DTO
    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {
        Employee existing = empRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existing.setName(dto.getName());
        existing.setRole(dto.getRole());

        if (dto.getDeptId() != null) {
            Department dept = deptRepo.findById(dto.getDeptId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            existing.setDepartment(dept);
        }

        Employee updated = empRepo.save(existing);
        return convertToDTO(updated);
    }

    // ✅ Delete Employee
    @Override
    public void deleteEmployee(Long id) {
        empRepo.deleteById(id);
    }

    @Override
    public List<EmployeeDTO> getEmployeesByRole(String role){
        List<Employee> employees = empRepo.findEmployeesWithCustomRole(role);
        return employees.stream().map(this :: convertToDTO).toList();
    }

    // 🔄 Convert Entity → DTO
    private EmployeeDTO convertToDTO(Employee emp) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setName(emp.getName());
        dto.setRole(emp.getRole());
        dto.setDeptId(emp.getDepartment() != null ? emp.getDepartment().getDeptId() : null);
        return dto;
    }
}
