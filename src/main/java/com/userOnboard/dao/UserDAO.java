package com.userOnboard.dao;

import java.util.List;
import java.util.Map;

import com.userOnboard.model.User;

public interface UserDAO {

	List<User> getAllUsers(int limit, int offset);
	
	int saveUser(User user);
	
	User getById (int id) throws Exception;

	List<User> getUsersByfilter(Map<String, Object> filters);
}
