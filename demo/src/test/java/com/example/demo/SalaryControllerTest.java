package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit tests for SalaryController
 * Tests all REST endpoints with mocked SalaryService
 */
@WebMvcTest(SalaryController.class)
public class SalaryControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalaryService sService;

    @MockBean
    private SalaryRepository salaryRepo;

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private HRSalaryDTO salary1;
    private HRSalaryDTO salary2;
    private EmployeeSalaryDTO employeeSalaryDTO;
    private UpdateSalaryDTO updateSalaryDTO;

    @BeforeEach
    void setUp() {
        // Initialize test data
        salary1 = HRSalaryDTO.builder()
                .salaryId(1L)
                .grade("A")
                .basic(5000.0)
                .hra(500.0)
                .da(300.0)
                .total(5800.0)
                .currency("USD")
                .salaryMonth(5)
                .employeeId(1L)
                .build();

        salary2 = HRSalaryDTO.builder()
                .salaryId(2L)
                .grade("B")
                .basic(7000.0)
                .hra(700.0)
                .da(400.0)
                .total(8100.0)
                .currency("USD")
                .salaryMonth(6)
                .employeeId(2L)
                .build();

        employeeSalaryDTO = EmployeeSalaryDTO.builder()
                .salaryMonth(5)
                .basic(5000.0)
                .hra(500.0)
                .da(300.0)
                .deductions(100.0)
                .total(5700.0)
                .currency("USD")
                .build();

        updateSalaryDTO = UpdateSalaryDTO.builder()
                .grade("A")
                .basic(5500.0)
                .bonus(600.0)
                .deductions(150.0)
                .build();
    }

    /**
     * Test GET /api/v1/salaries - Get all salaries
     * Should return 200 OK with list of HRSalaryDTO
     */
    @Test
    void getAllSalaries_shouldReturnList() throws Exception {
        when(sService.getAllSalaries()).thenReturn(List.of(salary1, salary2));

        mockMvc.perform(get("/api/salaries")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].salaryId").value(1L))
                .andExpect(jsonPath("$[0].grade").value("A"))
                .andExpect(jsonPath("$[0].basic").value(5000.0))
                .andExpect(jsonPath("$[0].total").value(5800.0))
                .andExpect(jsonPath("$[1].salaryId").value(2L))
                .andExpect(jsonPath("$[1].grade").value("B"));
    }

    /**
     * Test GET /api/v1/salaries/{id} - Get salary by ID
     * Should return 200 OK with single HRSalaryDTO
     */
    @Test
    void getSalaryById_shouldReturnOne() throws Exception {
        when(sService.getSalaryById(1L)).thenReturn(salary1);

        mockMvc.perform(get("/api/salaries/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salaryId").value(1L))
                .andExpect(jsonPath("$.grade").value("A"))
                .andExpect(jsonPath("$.basic").value(5000.0))
                .andExpect(jsonPath("$.hra").value(500.0))
                .andExpect(jsonPath("$.da").value(300.0))
                .andExpect(jsonPath("$.total").value(5800.0))
                .andExpect(jsonPath("$.currency").value("USD"))
                .andExpect(jsonPath("$.salaryMonth").value(5))
                .andExpect(jsonPath("$.employeeId").value(1L));
    }

    /**
     * Test POST /api/v1/salaries/employee/{employeeId} - Create new salary
     * Should accept EmployeeSalaryDTO and return 200 OK with HRSalaryDTO
     */
    @Test
    void createNewSalary_shouldCreateNew() throws Exception {
        when(sService.createNewSalary(any(EmployeeSalaryDTO.class), eq(1L)))
                .thenReturn(salary1);

        mockMvc.perform(post("/api/salaries/employee/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeSalaryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salaryId").value(1L))
                .andExpect(jsonPath("$.grade").value("A"))
                .andExpect(jsonPath("$.basic").value(5000.0))
                .andExpect(jsonPath("$.total").value(5800.0));
    }

    /**
     * Test PUT /api/v1/salaries/{id} - Update existing salary
     * Should accept UpdateSalaryDTO and return 200 OK with updated HRSalaryDTO
     */
    @Test
    void updateSalary_shouldUpdateExisting() throws Exception {
        HRSalaryDTO updated = HRSalaryDTO.builder()
                .salaryId(1L)
                .grade("A")
                .basic(5500.0)
                .hra(600.0)
                .da(300.0)
                .total(6400.0)
                .currency("USD")
                .salaryMonth(5)
                .employeeId(1L)
                .build();

        when(sService.updateSalary(any(UpdateSalaryDTO.class), eq(1L)))
                .thenReturn(updated);

        mockMvc.perform(put("/api/salaries/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateSalaryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salaryId").value(1L))
                .andExpect(jsonPath("$.grade").value("A"))
                .andExpect(jsonPath("$.basic").value(5500.0))
                .andExpect(jsonPath("$.total").value(6400.0));
    }

    /**
     * Test DELETE /api/v1/salaries/{id} - Delete salary
     * Should return 200 OK with no content
     */
    @Test
    void deleteSalary_shouldDeleteExisting() throws Exception {
        doNothing().when(sService).deleteSalary(1L);

        mockMvc.perform(delete("/api/salaries/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test GET /api/v1/salaries/hr/employee/{employeeId} - Get all salaries for employee (HR view)
     * Should return 200 OK with list of HRSalaryDTO
     */
    @Test
    void getSalaryByEmployeeId_shouldReturnEmployeeSalaries() throws Exception {
        when(sService.getSalaryByEmployeeId(1L)).thenReturn(List.of(salary1));

        mockMvc.perform(get("/api/salaries/hr/employee/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].salaryId").value(1L))
                .andExpect(jsonPath("$[0].employeeId").value(1L))
                .andExpect(jsonPath("$[0].basic").value(5000.0));
    }

    /**
     * Test GET /api/v1/salaries/employee/{employeeId}/view - Get employee salary view
     * Should return 200 OK with list of EmployeeSalaryDTO (limited fields)
     */
    @Test
    void getEmployeeSalaryByEmployeeId_shouldReturnEmployeeView() throws Exception {
        when(sService.getEmployeeSalaryByEmployeeId(1L))
                .thenReturn(List.of(employeeSalaryDTO));

        mockMvc.perform(get("/api/salaries/employee/1/view")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].basic").value(5000.0))
                .andExpect(jsonPath("$[0].hra").value(500.0))
                .andExpect(jsonPath("$[0].da").value(300.0))
                .andExpect(jsonPath("$[0].deductions").value(100.0))
                .andExpect(jsonPath("$[0].total").value(5700.0));
    }

    /**
     * Test POST /api/v1/salaries/calculate - Calculate total salary
     * Should accept EmployeeSalaryDTO and return 200 OK with calculated total
     */
    @Test
    void calculateTotalSalary_shouldReturnCalculatedTotal() throws Exception {
        // basic(5000) + hra(500) + da(300) - deductions(100) = 5700
        when(sService.calculateTotalSalary(5000.0, 500.0, 300.0, 100.0))
                .thenReturn(5700.0);

        mockMvc.perform(post("/api/salaries/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeSalaryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(5700.0));
    }

    /**
     * Test empty list scenario - Get all salaries when none exist
     * Should return 200 OK with empty list
     */
    @Test
    void getAllSalaries_shouldReturnEmptyList() throws Exception {
        when(sService.getAllSalaries()).thenReturn(List.of());

        mockMvc.perform(get("/api/salaries")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
