package com.example.demo;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    public Long deptId;
    public String name;
    public String manager;
    public List<EmployeeDTO> employees;
}
