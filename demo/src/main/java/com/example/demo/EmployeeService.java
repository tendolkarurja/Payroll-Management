package com.example.demo;
import java.util.*;

import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO dto);
    List<EmployeeDTO> getAllEmployee();
    EmployeeDTO getEmployeeByID(Long id);
    void deleteEmployee(Long id);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO dto);
    List<EmployeeDTO> getEmployeesByRole(String role);
}
