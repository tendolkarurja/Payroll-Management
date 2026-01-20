package com.example.demo;
import org.springframework.data.jpa.repository.JpaRepository;

interface DepartmentRepository extends JpaRepository<Department, Long>, DepartmentRepositoryCustom {
    
}
