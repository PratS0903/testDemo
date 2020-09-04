package com.testDynamoDB.testDynamoDB.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.testDynamoDB.testDynamoDB.entity.Profile;
import com.testDynamoDB.testDynamoDB.service.ProfileService;

@RestController
@RequestMapping("/v1")
public class ProfileController {
	
	
	@Autowired
    private ProfileService profileService;
	
	 @PostMapping("/profiles")
	    public String createProfile(@RequestBody Profile profile) {
	        return  profileService.createProfile(profile);
	 }
	    
	    @GetMapping("/profiles")
	    public Profile getProfilesById(@RequestParam String user_id)
	    {
	    	return profileService.getProfilesById(user_id);
	    }

}
