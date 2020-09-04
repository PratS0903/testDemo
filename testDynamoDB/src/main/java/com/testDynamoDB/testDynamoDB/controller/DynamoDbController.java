package com.testDynamoDB.testDynamoDB.controller;

import com.testDynamoDB.testDynamoDB.dtos.StudentDTO;
import com.testDynamoDB.testDynamoDB.service.StudentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dynamoDb")
public class DynamoDbController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/saveStudent")
    public StudentDTO insertIntoDynamoDB(@RequestBody StudentDTO dto) {
        return  studentService.insertIntoDynamoDB(dto);
    }
    
    @GetMapping("/getAllStudents")
    public List<StudentDTO> getAllFromDynamoDB()
    {
    	return studentService.getAllFromDynamoDB();
    }
    
    @PutMapping("/updateStudent")
    public StudentDTO updateIntoDynamoDB(@RequestBody StudentDTO dto) {
        return  studentService.updateIntoDynamoDB(dto);
    }
    
    @DeleteMapping("/deleteStudent")
    public String deleteFromDynamoDB(@RequestParam Long id)
    {
    	String status = studentService.deleteFromDynamoDB(id);
    	return status;
    }
    
    @GetMapping("/getStudentById")
    public StudentDTO getFromDynamoDB(@RequestParam Long id)
    {
    	return studentService.getFromDynamoDB(id);
    }
}
