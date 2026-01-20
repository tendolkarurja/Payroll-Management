package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository deptRepo;


    // ✅ GET ALL
    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = deptRepo.findAll();
        return departments.stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ✅ GET BY ID
    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        Department dept = deptRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return convertToDTO(dept);
    }

    // ✅ CREATE
    @Override
    public DepartmentDTO createNewDepartment(DepartmentDTO dto) {
        Department dept = convertToEntity(dto);
        Department saved = deptRepo.save(dept);
        return convertToDTO(saved);
    }

    // ✅ UPDATE
    @Override
    public DepartmentDTO updateDepartment(DepartmentDTO dto, Long id) {
        Department existing = deptRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        existing.setName(dto.getName());
        existing.setManager(dto.getManager());

        List<Employee> employees = new ArrayList<>();
        if (dto.getEmployees() != null) {
            for (EmployeeDTO edto : dto.getEmployees()) {
                Employee emp = new Employee();
                emp.setName(edto.getName());
                emp.setRole(edto.getRole());
                emp.setDepartment(existing);
                employees.add(emp);
            }
        }
        existing.setEmployees(employees);
        Department updated = deptRepo.save(existing);
        return convertToDTO(updated);
    }

    // ✅ DELETE
    @Override
    public void deleteDepartment(Long id) {
        deptRepo.deleteById(id);
    }

    // ✅ GET BY MANAGER
    @Override
    public List<DepartmentDTO> getDepartmentUnderManager(@PathVariable String manager) {
        List<Department> depts = deptRepo.deptUnder(manager);
        return depts.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
public List<EmployeeDTO> getEmployeesInDepartment(Long deptId) {
    Department dept = deptRepo.findById(deptId)
            .orElseThrow(() -> new RuntimeException("Department not found"));

    List<Employee> employees = dept.getEmployees();

    return employees.stream().map(this::mapToDTO).collect(Collectors.toList());
}

    private EmployeeDTO mapToDTO(Employee emp) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setDeptId(emp.getId());
        dto.setName(emp.getName());
        dto.setRole(emp.getRole());
        if (emp.getDepartment() != null)
            dto.setDeptId(emp.getDepartment().getDeptId());
        return dto;
    }

    private DepartmentDTO convertToDTO(Department dept) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setDeptId(dept.getDeptId());
        dto.setName(dept.getName());
        dto.setManager(dept.getManager());

        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        if (dept.getEmployees() != null) {
            for (Employee emp : dept.getEmployees()) {
                EmployeeDTO edto = new EmployeeDTO();
                edto.setDeptId(emp.getId());
                edto.setName(emp.getName());
                edto.setRole(emp.getRole());
                employeeDTOs.add(edto);
            }
        }
        dto.setEmployees(employeeDTOs);
        return dto;
    }

    private Department convertToEntity(DepartmentDTO dto) {
        Department dept = new Department();
        dept.setName(dto.getName());
        dept.setManager(dto.getManager());

        List<Employee> employees = new ArrayList<>();
        if (dto.getEmployees() != null) {
            for (EmployeeDTO edto : dto.getEmployees()) {
                Employee emp = new Employee();
                emp.setName(edto.getName());
                emp.setRole(edto.getRole());
                emp.setDepartment(dept);
                employees.add(emp);
            }
        }
        dept.setEmployees(employees);
        return dept;
    }
}
