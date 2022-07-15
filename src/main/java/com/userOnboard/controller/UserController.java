package com.userOnboard.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.userOnboard.customResponse.ResponseHandler;
import com.userOnboard.model.User;
import com.userOnboard.service.UserService;

import io.swagger.annotations.Api;
	
@RestController
@Api(value="users", description="Endpoint for user management")
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userservice;
	
	@GetMapping("/users/{limit}/{offset}")
	@CrossOrigin(origins = "http://localhost:8081")
	// @RequestMapping(value = "/users")
	public ResponseEntity<Object> getUsers(@PathVariable  int limit, @PathVariable  int offset) {
		try {
			Object result = userservice.getAllUsers(limit,offset);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("Users", result);
			logger.debug("Limit == "+limit);
		return ResponseHandler.generateResponse("Success","Successfully retrieved  data!", HttpStatus.OK, data);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ResponseHandler.generateResponse("failure",e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}
	
	@GetMapping("/users/{id}")
	// @RequestMapping(value = "/users")
	public ResponseEntity<Object> getUserById(@PathVariable int id) {
		try {
			Object result = userservice.getUserById(id);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("Users", result);
		return ResponseHandler.generateResponse("Success","Successfully retrieved  data!", HttpStatus.OK, data);
		}catch (Exception e) {
			return ResponseHandler.generateResponse("failure",e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}
	
	@PostMapping("/users")
	// @RequestMapping(value = "/users")
	public ResponseEntity<Object> saveUser(@RequestBody User user) {
		try {
			Object result = userservice.saveUser(user);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("Users", result);
		return ResponseHandler.generateResponse("Success","Successfully retrieved  data!", HttpStatus.OK, data);
		}catch (Exception e) {
			return ResponseHandler.generateResponse("failure",e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}
	
	/*
	 * @RequestMapping(value = "/listPageable", method = RequestMethod.GET)
	 * Page<Employee> employeesPageable(Pageable pageable) { return
	 * employeeData.findAll(pageable);
	 * 
	 * }
	 */
	
	@GetMapping("/getUsersByfilter")
	public ResponseEntity<Object> getUsersByfilter() {
		try {
			Map<String, Object> filters = new  HashMap<String, Object>();
			filters.put("first_name", "first_name");
			filters.put("user_id",1);
			Object result = userservice.getUsersByfilter(filters);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("Users", result);
		return ResponseHandler.generateResponse("Success","Successfully retrieved  data!", HttpStatus.OK, data);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ResponseHandler.generateResponse("failure",e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}
}
