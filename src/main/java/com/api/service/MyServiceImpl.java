package com.api.service;

import com.api.dto.EmployeeDTO;
import com.api.entity.EmployeeEntity;
import com.api.repository.EmployeeRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;

@Service
public class MyServiceImpl implements MyService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;


    private final Validator validator;

    public MyServiceImpl() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        this.validator = validator;
    }

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

    @Override
    public List<String> validation(Map<String, Object> map) {
        List<String> errorList = new ArrayList<>();
        EmployeeDTO tempDTO = new EmployeeDTO(); // DTO instance for validation

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            try {
                Field field = EmployeeDTO.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(tempDTO, fieldValue); // Set value dynamically

                // Validate the temporary object
                Set<ConstraintViolation<EmployeeDTO>> violations = validator.validateProperty(tempDTO, fieldName);
                for (ConstraintViolation<EmployeeDTO> violation : violations) {
                    errorList.add(violation.getMessage());
                }
            } catch (NoSuchFieldException e) {
                errorList.add("Invalid Field: " + fieldName);
            } catch (IllegalAccessException e) {
                errorList.add("Access Error: " + fieldName);
            } catch (Exception e) {
                errorList.add("Invalid Value for Field: " + fieldName);
            }
        }
        return errorList;
    }


    @Override
    public void partialUpdate(EmployeeEntity employeeEntity, Map<String, Object> map) {
        map.forEach((key, value) -> {
            switch (key) {
                case "name":
                    if (value instanceof String) {
                        employeeEntity.setName((String) value);
                    }
                    break;
                case "address":
                    if (value instanceof String) {
                        employeeEntity.setAddress((String) value);
                    }
                    break;
                case "salary":
                    if (value instanceof Number) {
                        employeeEntity.setSalary(((Number) value).intValue());
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        //  DB mein persist karna zaroori hai!
        employeeRepository.save(employeeEntity);
    }


    @Override
    public void deleteSingle(Integer id) {
        employeeRepository.deleteById(id);
    }


}