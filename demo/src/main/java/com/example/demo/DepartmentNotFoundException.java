package com.example.demo;

public class DepartmentNotFoundException extends RuntimeException {
    DepartmentNotFoundException(Long id)
    {
        super("Could not find employee " + id);
    }
}
