package com.api.service;

import com.api.dto.EmployeeDTO;
import com.api.entity.EmployeeEntity;
import com.api.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MyServiceImpl implements MyService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public EmployeeEntity insert(EmployeeDTO employeeDTO) {
        EmployeeEntity entity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        entity.setCreatedDate(LocalDate.now());
        return employeeRepository.save(entity);
    }

    @Override
    public List<EmployeeEntity> readAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<EmployeeEntity> readSingle(int id) {
        return employeeRepository.findById(id);
    }

    @Override
    public EmployeeEntity updateAll(EmployeeDTO employeeDTO, EmployeeEntity employeeEntity) {
        modelMapper.map(employeeDTO, employeeEntity);
        employeeEntity.setCreatedDate(LocalDate.now());
        return employeeRepository.save(employeeEntity);
    }

}