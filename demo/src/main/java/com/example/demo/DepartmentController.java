package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin("https://localhost:3000")
public class DepartmentController {

    @Autowired
    private DepartmentService deptService;

    // ✅ CREATE department (with employees) using DTO
    @PostMapping("/")
    public DepartmentDTO createDepartment(@RequestBody DepartmentDTO dto) {
        return deptService.createNewDepartment(dto);
    }

    // ✅ READ all departments
    @GetMapping("/")
    public List<DepartmentDTO> getAllDepartments() {
        return deptService.getAllDepartments();
    }
    // ✅ READ department by ID
    @GetMapping("/{id}")
    public DepartmentDTO getDepartmentById(@PathVariable Long id) {
        return deptService.getDepartmentById(id);
    }

    // ✅ UPDATE department
    @PutMapping("/{id}")
    public DepartmentDTO updateDepartment(@RequestBody DepartmentDTO dto, @PathVariable Long id) {
        return deptService.updateDepartment(dto, id);
       
    }

    // ✅ DELETE department
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        deptService.deleteDepartment(id);
        return ResponseEntity.noContent().build(); // <-- 204 No Content
}


    // ✅ Get all departments under a manager (custom query)
    @GetMapping("/manager/{manager}")
    public List<DepartmentDTO> getDepartmentsUnderManager(@PathVariable String manager) {
        return deptService.getDepartmentUnderManager(manager);
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeDTO> getEmployeesInDepartment(@PathVariable Long id) {
        return deptService.getEmployeesInDepartment(id);
    }

}
