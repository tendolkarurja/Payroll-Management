package com.example.demo;
import org.springframework.stereotype.Repository;
import java.util.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SuppressWarnings("unchecked")
@Repository
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Employee> findEmployeesWithCustomRole(String role){
    String sql = """
        SELECT * FROM employee
        WHERE role = :role
    """;
    return entityManager.createNativeQuery(sql, Employee.class)
                        .setParameter("role", role)
                        .getResultList();
}
}