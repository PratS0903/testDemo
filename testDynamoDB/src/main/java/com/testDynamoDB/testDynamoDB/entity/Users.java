package com.testDynamoDB.testDynamoDB.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "users")
public class Users {
	
	public String user_id;
	public String user_name;
	

}
