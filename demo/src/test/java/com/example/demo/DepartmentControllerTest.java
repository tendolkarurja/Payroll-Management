package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService dService;

    @MockBean
    private DepartmentRepository deptRepo;

    @MockBean
    private EmployeeRepository empRepo;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllDepartments_shouldReturnList() throws Exception {
        List<DepartmentDTO> departments = List.of(
                new DepartmentDTO(1L, "HR", "A", new ArrayList<>()),
                new DepartmentDTO(2L, "IT", "B", new ArrayList<>())
        );

        when(dService.getAllDepartments()).thenReturn(departments);

        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk());
    }

    @Test
    void getDepartmentById_shouldReturnOne() throws Exception {
    DepartmentDTO dto = new DepartmentDTO(1L, "HR", "A", new ArrayList<>());

    when(dService.getDepartmentById(1L)).thenReturn(dto);

    mockMvc.perform(get("/departments/1"))
            .andExpect(status().isOk());
    }

    @Test
    void addDepartment_shouldCreateNewDepartment() throws Exception {
        DepartmentDTO dto = new DepartmentDTO(null, "Legal", "C", new ArrayList<>());
        Department savedDept = new Department(1L, "Legal", "C", new ArrayList<>());

        when(dService.createNewDepartment(any(DepartmentDTO.class))).thenReturn(dto);
        when(deptRepo.save(any(Department.class))).thenReturn(savedDept);

        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());  // or .isCreated() if you return 201
    }

    @Test
    void updateDepartment_shouldUpdateDetails() throws Exception {
        DepartmentDTO dto = new DepartmentDTO(null, "HR", "Updated", new ArrayList<>());
        DepartmentDTO updated = new DepartmentDTO(1L, "HR", "Updated", new ArrayList<>());

        when(dService.updateDepartment(eq(dto), eq(1L))).thenReturn(updated);

        mockMvc.perform(put("/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteDepartment_shouldReturnNoContent() throws Exception {
        doNothing().when(dService).deleteDepartment(1L);

        mockMvc.perform(delete("/departments/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getEmployeesInDepartment_shouldReturnList() throws Exception {
        List<EmployeeDTO> employees = List.of(
                new EmployeeDTO("John Doe", "Analyst", 1L),
                new EmployeeDTO("Jane Smith", "Developer", 2L)
        );

    when(dService.getEmployeesInDepartment(1L)).thenReturn(employees);

        mockMvc.perform(get("/departments/1/employees"))
                .andExpect(status().isOk());
    }

    @Test
    void getDepartmentsByManager_shouldReturnList() throws Exception {
        List<DepartmentDTO> depts = List.of(
                new DepartmentDTO(1L, "HR", "Alice", new ArrayList<>()),
                new DepartmentDTO(2L, "IT", "Alice", new ArrayList<>())
        );

        when(dService.getDepartmentUnderManager("Alice")).thenReturn(depts);

        mockMvc.perform(get("/departments/manager/Alice"))
                .andExpect(status().isOk());
    }
}
