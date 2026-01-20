package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService eService;

    @MockBean
    private EmployeeRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    
    void getAllEmployees_shouldReturnList() throws Exception {
        EmployeeDTO emp1 = new EmployeeDTO("Alice", "Assistant", null);
        EmployeeDTO emp2 = new EmployeeDTO("Bob", "Manager", null);

        when(eService.getAllEmployee()).thenReturn(List.of(emp1, emp2));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk());
    }

    @Test
    void getEmployeeById_shouldReturnOne() throws Exception {
        EmployeeDTO emp = new EmployeeDTO("Charlie", "Analyst", null);

        when(eService.getEmployeeByID(1L)).thenReturn(emp);

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk());
    }

    @Test
    void addEmployee_shouldCreateNew() throws Exception {
        EmployeeDTO dto = new EmployeeDTO("David", "Tester", 1L);
        when(eService.createEmployee(any(EmployeeDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateEmployee_shouldModifyData() throws Exception {
        EmployeeDTO dto = new EmployeeDTO("Eve", "Lead", 1L);
        EmployeeDTO updatedEmp = new EmployeeDTO("Eve", "Lead", null);

        when(eService.updateEmployee(eq(1L), any(EmployeeDTO.class))).thenReturn(updatedEmp);

        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteEmployee_shouldReturnNoContent() throws Exception {
        doNothing().when(eService).deleteEmployee(1L);

        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isOk());
    }

    @Test
    void specificRole_shouldReturnFilteredList() throws Exception {
        Employee emp1 = new Employee(1L, "A", "Dev", null);
        Employee emp2 = new Employee(2L, "B", "Dev", null);

        when(repository.findEmployeesWithCustomRole("Dev")).thenReturn(List.of(emp1, emp2));

        mockMvc.perform(get("/employees/role/Dev"))
                .andExpect(status().isOk());
    }
}
