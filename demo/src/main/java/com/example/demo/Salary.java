package com.example.demo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
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
public class Salary {
    private @Id
    @GeneratedValue Long salaryId;

    private String grade;
    private Double basic;
    private Double hra;
    private Double da;
    private Double total;
    private String currency;
    private Double deductions;
    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private Integer salaryMonth;
}
