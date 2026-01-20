package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface DepartmentService {
    public List<DepartmentDTO> getAllDepartments();
    public DepartmentDTO getDepartmentById(Long id);
    public DepartmentDTO createNewDepartment(DepartmentDTO dto);
    public DepartmentDTO updateDepartment(DepartmentDTO dto, Long id);
    public void deleteDepartment(Long id);
    public List<DepartmentDTO> getDepartmentUnderManager(String manager);
    public List<EmployeeDTO> getEmployeesInDepartment(Long deptId);
}
