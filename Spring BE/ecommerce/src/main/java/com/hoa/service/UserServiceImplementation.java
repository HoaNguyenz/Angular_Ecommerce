package com.hoa.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hoa.config.JwtProvider;
import com.hoa.exception.UserException;
import com.hoa.model.User;
import com.hoa.repository.UserRepository;


@Service
public class UserServiceImplementation implements UserService {

	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	
	public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
		this.userRepository = userRepository;
	}
	
	@Override
	public User findUserbyId(Long userId) throws UserException {
		
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			return user.get();
		}
		
		throw new UserException("User not found by id - " + userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		
		User user = userRepository.findByEmail(email);
		
		if(user == null) {
			throw new UserException("User not found with email - " + email);
		}
		return user;
	}

}
