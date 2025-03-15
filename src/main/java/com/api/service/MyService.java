package com.api.service;

import com.api.dto.EmployeeDTO;
import com.api.entity.EmployeeEntity;

import java.util.List;
import java.util.Optional;

public interface MyService {
    EmployeeEntity insert(EmployeeDTO employeeDTO);

    List<EmployeeEntity> readAll();

    Optional<EmployeeEntity> readSingle(int id);

    EmployeeEntity updateAll(EmployeeDTO employeeDTO, EmployeeEntity employeeEntity);
}