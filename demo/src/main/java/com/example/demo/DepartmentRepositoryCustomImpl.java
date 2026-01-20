package com.example.demo;
import java.util.*;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SuppressWarnings("unchecked")
@Repository
public class DepartmentRepositoryCustomImpl implements DepartmentRepositoryCustom{

        
        @PersistenceContext
        private EntityManager entityManager;

        @Override
        public List<Department> deptUnder(String manager){
            String sql = """
                    SELECT *
                    FROM department
                    WHERE manager = :manager;
                    """;

            return entityManager.createNativeQuery(sql, Department.class).setParameter("manager", manager).getResultList();
        }
    }

