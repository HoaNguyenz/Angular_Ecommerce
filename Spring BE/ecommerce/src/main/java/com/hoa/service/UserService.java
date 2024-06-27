package com.hoa.service;

import com.hoa.exception.UserException;
import com.hoa.model.User;

public interface UserService {
	
	
	public User findUserbyId(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
}
