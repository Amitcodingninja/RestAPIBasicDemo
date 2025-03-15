package com.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeDTO {
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;

    @NotBlank(message = "Address cannot be empty")
    private String address;

    @Min(value = 1000, message = "Salary must be at least 1000")
    private int salary;

    public @NotBlank(message = "Name cannot be empty") @Size(min = 2, message = "Name must be at least 2 characters long") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name cannot be empty") @Size(min = 2, message = "Name must be at least 2 characters long") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Address cannot be empty") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(message = "Address cannot be empty") String address) {
        this.address = address;
    }

    @Min(value = 1000, message = "Salary must be at least 1000")
    public int getSalary() {
        return salary;
    }

    public void setSalary(@Min(value = 1000, message = "Salary must be at least 1000") int salary) {
        this.salary = salary;
    }
}