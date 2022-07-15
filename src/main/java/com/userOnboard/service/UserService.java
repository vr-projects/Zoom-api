package com.userOnboard.service;

import java.util.List;
import java.util.Map;

import com.userOnboard.model.User;

public interface UserService {

	List<User> getAllUsers(int limit, int offset);

	User getUserById(int id) throws Exception;

	int saveUser(User user);

	List<User> getUsersByfilter(Map<String, Object> filters);
	
}
