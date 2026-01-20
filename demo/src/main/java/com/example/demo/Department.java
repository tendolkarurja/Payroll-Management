package com.example.demo;
import com.example.demo.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"deptId", "name", "manager"})
class Department {
    
    private @Id
    @GeneratedValue Long deptId;
    private String name;
    private String manager;

    @JsonManagedReference
    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();
}