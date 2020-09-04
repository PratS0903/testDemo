package com.testDynamoDB.testDynamoDB.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.testDynamoDB.testDynamoDB.dtos.StudentDTO;
import com.testDynamoDB.testDynamoDB.entity.Student;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private DynamoDBMapper mapper;
    
    @Autowired AmazonDynamoDB amazonDynamoDB;
    
    //converting from entity to DTO
    public StudentDTO convertDto(Student student){
        return new StudentDTO(
                student.getStudentId(),
                student.getFirstName(),
                student.getLastName()
        );
    }
    
    //Converting from DTO to entity
    public Student convertEnity(StudentDTO dto)
    {
    	Student student = new Student();
        student.setStudentId(dto.getStudentId());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
		return student;
    }

    public StudentDTO insertIntoDynamoDB(StudentDTO dto) {
        Student student = convertEnity(dto);
        
        //DAO layer
        mapper.save(student);
        System.out.println("Student is saved in table");
        return convertDto(student);
    }

    public List<StudentDTO> getAllFromDynamoDB()
    {
    	DynamoDBScanExpression scan = new DynamoDBScanExpression();

    	List<Student> students =mapper.scan(Student.class, scan);
    	
    	List<StudentDTO> studentDTOs = new ArrayList<StudentDTO>();
    	System.out.println("All Student's data: ");
    	for(Student st: students)
    	{
    		System.out.println(st);
    		StudentDTO studentdto = new StudentDTO();
    		studentdto = convertDto(st);
    		studentDTOs.add(studentdto);
    	}
    	
    	return studentDTOs;
    }

	public StudentDTO updateIntoDynamoDB(StudentDTO dto) {
		// TODO Auto-generated method stub
		Student student = convertEnity(dto);
		
		DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
		Table table = dynamoDB.getTable("student");
		UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("studentId", dto.getStudentId())
	            .withUpdateExpression("set firstName = :r, lastName=:p")
	            .withValueMap(new ValueMap().withString(":r", dto.getFirstName()).withString(":p", dto.getLastName()))
	            .withReturnValues(ReturnValue.UPDATED_NEW);
		
//		mapper.save
		UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
//		System.out.println("Student " + student.getStudentId()  + " has been updated");
		 System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
		return convertDto(student);
		
	}

	public String deleteFromDynamoDB(Long id) {
		// TODO Auto-generated method stub
		String status = null;
		DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
		Table table = dynamoDB.getTable("student");
		
		DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
	            .withPrimaryKey("studentId", id).withConditionExpression("studentId= :val")
	            .withValueMap(new ValueMap().withNumber(":val", id));
		
		 try {
	            System.out.println("Attempting a conditional delete...");
	            table.deleteItem(deleteItemSpec);
	           status = "DeleteItem succeeded";
	        }
	        catch (Exception e) {
	            status = "Unable to delete item ";
	            System.err.println(e.getMessage());
	        }
		return status;
	}

	public StudentDTO getFromDynamoDB(Long id) {
		// TODO Auto-generated method stub
		DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
		Table table = dynamoDB.getTable("student");
		
		 GetItemSpec spec = new GetItemSpec().withPrimaryKey("studentId" , id);
		 StudentDTO st = new StudentDTO();
		 try {
	            System.out.println("Attempting to read the item...");
	            Item outcome = table.getItem(spec);
	            
	            
	            st.setStudentId(outcome.getLong("studentId"));
	            st.setFirstName(outcome.getString("firstName"));
	            st.setLastName(outcome.getString("lastName"));
	            
	            System.out.println("GetItem succeeded: " + outcome);

	        }
		 
		 catch (Exception e) {
	            System.err.println("Unable to read item: " + id);
	            System.err.println(e.getMessage());
	        }
		return st;
	}
}