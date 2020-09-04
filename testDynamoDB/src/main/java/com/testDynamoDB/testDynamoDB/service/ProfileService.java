package com.testDynamoDB.testDynamoDB.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.testDynamoDB.testDynamoDB.dtos.ProfileDTO;
import com.testDynamoDB.testDynamoDB.dtos.StudentDTO;
import com.testDynamoDB.testDynamoDB.entity.Profile;
import com.testDynamoDB.testDynamoDB.entity.Student;

public class ProfileService {
	
	 @Autowired
	   private DynamoDBMapper mapper;
	    
	 @Autowired AmazonDynamoDB amazonDynamoDB;
	    
	 public String createProfile(Profile profile) {
	        
	        //DAO layer
	        mapper.save(profile);
	        System.out.println("Profile is saved in table");
	        return "Success";
	    }
	 
		public Profile getProfilesById(String user_id) {
			// TODO Auto-generated method stub
			DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
			Table table = dynamoDB.getTable("profile");
			
			 GetItemSpec spec = new GetItemSpec().withPrimaryKey("user_id" , user_id);
			 Profile profile = new Profile();
			 try {
		            System.out.println("Attempting to read the item...");
		            Item outcome = table.getItem(spec);
		            
		            
		            profile.setProfile_id(outcome.getString("profile_id"));
		            profile.setUser_id(outcome.getString("user_id"));
		            profile.setAttract_loop_id(outcome.getString("attract_loop_id"));
		            profile.setBanner_carousel_id(outcome.getString("banner_carousel_id"));
		            profile.setIdle_timer(outcome.getString("idle_timer"));
		            profile.setLanguage(outcome.getString("language"));
		            profile.setLogo_content_id(outcome.getString("logo_content_id"));
		            profile.setOperating_system(outcome.getString("operating_system"));
		            profile.setPrice_content_id(outcome.getString("price_content_id"));
		            profile.setProfile_configurations(outcome.getString("profile_configuartions"));
		            profile.setPromotional_content_id(outcome.getString("promotional_content_id"));
		            profile.setSegment_id(outcome.getString("segment_id"));
		            profile.setUpdated_date(outcome.getString("updated_date"));
		            System.out.println("GetItem succeeded: " + outcome);

		        }
			 
			 catch (Exception e) {
		            System.err.println("Unable to read item: " + user_id);
		            System.err.println(e.getMessage());
		        }
			return profile;
		}
	}

