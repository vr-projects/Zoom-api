package com.userOnboard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.userOnboard.dao.UserDAO;
import com.userOnboard.model.User;
import com.userOnboard.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userdao;
	
	/*
	 * public UserServiceImpl(UserDAO userdao) { super(); this.userdao = userdao; }
	 */

	@Override
	@Transactional
	public List<User> getAllUsers(int limit, int offset) {
		return userdao.getAllUsers( limit,  offset);
	}

	@Override
	@Transactional
	public User getUserById(int id) throws Exception {
		try {
			return userdao.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
			
		}
	}

	@Override
	@Transactional
	public int saveUser(User user) {
		return userdao.saveUser(user);
	}

}
