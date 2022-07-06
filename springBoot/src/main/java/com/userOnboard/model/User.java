package com.userOnboard.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	Integer user_id;
	String email_id;
	String fireBase_id;
	String first_name;	
	String middle_name;
	String last_name;
	List<Contact_info> contacts;
	
	
}
