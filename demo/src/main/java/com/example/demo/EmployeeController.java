package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;


@RestController
@RequestMapping("/api/employees")
@CrossOrigin("https://localhost:3000")
public class EmployeeController {

    @Autowired
    private EmployeeService eService;

    @Autowired
    private EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    //     this.assembler = assembler;
    }

    // GET all employees
    @GetMapping("/")
    List<EmployeeDTO> all(){
        return eService.getAllEmployee();
    }

    @GetMapping("/role/{role}")
    List<Employee> specificRole(@PathVariable String role){
        return repository.findEmployeesWithCustomRole(role);
    }

     // GET one employee by ID
    @GetMapping("/{id}")
    EmployeeDTO one(@PathVariable Long id){
        return eService.getEmployeeByID(id);
    }
    // POST - add a new employee
    @PostMapping("/")
    public ResponseEntity<EmployeeDTO> newEmployee(@RequestBody EmployeeDTO dto) {
        EmployeeDTO savedEmp =  eService.createEmployee(dto);
        return new ResponseEntity<EmployeeDTO>(savedEmp, HttpStatus.CREATED);
    }

    // PUT - update or create employee
    @PutMapping("/{id}")
    EmployeeDTO replaceEmployee(@RequestBody EmployeeDTO dto, @PathVariable Long id) {
        return eService.updateEmployee(id, dto);
    }

    // DELETE - remove an employee
    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Long id) {
        eService.deleteEmployee(id);
    }
    
}