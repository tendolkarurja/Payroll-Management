package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService deptService;

    // ✅ CREATE department (with employees) using DTO
    @PostMapping("/departments")
    public DepartmentDTO createDepartment(@RequestBody DepartmentDTO dto) {
        return deptService.createNewDepartment(dto);
    }

    // ✅ READ all departments
    @GetMapping("/departments")
    public List<DepartmentDTO> getAllDepartments() {
        return deptService.getAllDepartments();
    }
    // ✅ READ department by ID
    @GetMapping("/departments/{id}")
    public DepartmentDTO getDepartmentById(@PathVariable Long id) {
        return deptService.getDepartmentById(id);
    }

    // ✅ UPDATE department
    @PutMapping("/departments/{id}")
    public DepartmentDTO updateDepartment(@RequestBody DepartmentDTO dto, @PathVariable Long id) {
        return deptService.updateDepartment(dto, id);
       
    }

    // ✅ DELETE department
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        deptService.deleteDepartment(id);
        return ResponseEntity.noContent().build(); // <-- 204 No Content
}


    // ✅ Get all departments under a manager (custom query)
    @GetMapping("/departments/manager/{manager}")
    public List<DepartmentDTO> getDepartmentsUnderManager(@PathVariable String manager) {
        return deptService.getDepartmentUnderManager(manager);
    }

    @GetMapping("/departments/{id}/employees")
    public List<EmployeeDTO> getEmployeesInDepartment(@PathVariable Long id) {
        return deptService.getEmployeesInDepartment(id);
    }

}
