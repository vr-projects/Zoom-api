package com.userOnboard.controller;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userOnboard.customResponse.ResponseHandler;
import com.userOnboard.mapper.UserMapper;
import com.userOnboard.model.User;

import lombok.SneakyThrows;

@RestController
public class TestController {
	
	private UserMapper usermapper;
	
	
	
	public TestController(UserMapper usermapper) {
		this.usermapper = usermapper;
	}


	Logger logger = LoggerFactory.getLogger(TestController.class);

	
	@GetMapping("/usersByMybetis")
	public ResponseEntity<Object> getAllUsers() {
		try {
			Object result = usermapper.getAllUsers();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("Users", result);
		return ResponseHandler.generateResponse("Success","Successfully retrieved  data!", HttpStatus.OK, data);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ResponseHandler.generateResponse("failure",e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}
	
	@GetMapping("/getUserByStoredProcedure/{id}")
	public ResponseEntity<Object> getUserByStoredProcedure(@PathVariable int id) {
		try {
			User user = new User();
			user.setUser_id(id);
			 usermapper.getUserByStoredProcedure(user);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("Users", user);
		return ResponseHandler.generateResponse("Success","Successfully retrieved  data!", HttpStatus.OK, data);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ResponseHandler.generateResponse("failure",e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}
	
	@GetMapping("/usersByjson")
	// @RequestMapping(value = "/users")
	public ResponseEntity<Object> usersByjson() {
		try {
			List<User> users = usermapper.usersByjson();
			//List<User> users = new ArrayList<User>();
		//	ObjectMapper objectMapper = new ObjectMapper();
			/*
			 * while(rs.next()) { User user= objectMapper.readValue(rs.getString(1),
			 * User.class); users.add(user); System.out.println(user); }
			 */
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("Users", users);
		return ResponseHandler.generateResponse("Success","Successfully retrieved  data!", HttpStatus.OK, data);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ResponseHandler.generateResponse("failure",e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@SneakyThrows
	private  <T> T unmarshall(String json_body, Class<T> clazz){
		T t = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			 t= objectMapper.readValue(json_body, clazz);
		} catch (Exception e) {
			// TODO: handle exception
		}
	   return t;
	}
	
}
