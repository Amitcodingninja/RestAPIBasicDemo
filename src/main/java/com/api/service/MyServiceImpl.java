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
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldvalue = entry.getValue();

            try {
                Field field = EmployeeDTO.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                EmployeeDTO employeeDTO = new EmployeeDTO();
                field.set(employeeDTO, fieldvalue);
                Set<ConstraintViolation<EmployeeDTO>> voilations = validator.validate(employeeDTO);
                for (ConstraintViolation<EmployeeDTO> voilation : voilations) {
                    errorList.add(voilation.getMessage());
                }


            } catch (NoSuchFieldException e) {
                errorList.add("Key is not correct: " + fieldName);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }


//        List<String> errorList = new ArrayList<>();
//        Set<String> fieldNames = new HashSet<>(Arrays.asList("name", "address", "salary"));
//        for (Entry<String, Object>:map.entrySet()){
//            String key = entry.getKey();
//            Object value = entry.getValue();
//            if (!fieldNames.contains(key)) {
//                errorList.add("Key is not correct " + key);
//            }
//            if (key.equals("name")) {
//                String name = (String) value;
//                if (name.length() < 2 || name.length() > 30) {
//                    errorList.add("Name Size Validation ERROR");
//                }
//            }
//            if (key.equals("address")) {
//                String address = (String) value;
//                if (address.length() < 2 || address.length() > 100) {
//                    errorList.add("Address Size Validation ERROR");
//                }
//            }
//            if (key.equals("salary")) {
//                String salary = (String) value;
//                if (salary.length() < 2 || salary.length() > 100) {
//                    errorList.add("Invalid Salary");
//                }
//            }
//
//        }

        return null;
    }

}