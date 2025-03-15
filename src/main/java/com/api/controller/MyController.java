package com.api.controller;

import com.api.dto.EmployeeDTO;
import com.api.entity.EmployeeEntity;
import com.api.service.MyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/")
public class MyController {
    @Autowired
    private MyService myService;

    @PostMapping("insert")
    public ResponseEntity<?> insert(@Valid @RequestBody EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> list = new ArrayList<>();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                list.add(objectError.getDefaultMessage());
            }
            return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
        } else {


            EmployeeEntity savedEmployee = myService.insert(employeeDTO);
            Map<Object, Object> map = new HashMap<>();
            map.put("message", "Employee Saved Successfully");
            map.put("saveddata", savedEmployee);
            return new ResponseEntity<>(map, HttpStatus.CREATED);
        }

    }

    @GetMapping("employees")
    public ResponseEntity<List<EmployeeEntity>> readall() {
        List<EmployeeEntity> allList = myService.readAll();
        return ResponseEntity.ok(allList);

    }

    @GetMapping("employee/{id}")
    public ResponseEntity<?> readSingle(@PathVariable int id) {
        Optional<EmployeeEntity> data = myService.readSingle(id);
        if (data.isPresent()) {
            return ResponseEntity.ok(data.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID Does not exist");
        }

    }

    @PutMapping("employee/{id}")
    public ResponseEntity<?> updateAll(@PathVariable int id, @Valid @RequestBody EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> list = new ArrayList<>();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                list.add(objectError.getDefaultMessage());
            }
            return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
        } else {
            Optional<EmployeeEntity> data = myService.readSingle(id);
            if (data.isPresent()) {
                EmployeeEntity employeeEntity = data.get();
                EmployeeEntity updatedEmployee = myService.updateAll(employeeDTO, employeeEntity);
                Map<Object, Object> map = new HashMap<>();
                map.put("data", updatedEmployee);
                return ResponseEntity.ok(map);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID Does not exist");
            }
        }
    }
}