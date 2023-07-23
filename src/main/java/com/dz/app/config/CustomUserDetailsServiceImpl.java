package com.dz.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dz.app.entity.UserEntity;
import com.dz.app.repository.UserRepository;

public class CustomUserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private  UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//fetching user from database
		UserEntity user = userRepository.getUserByUserName(username);
		if(user==null) {
			throw new UsernameNotFoundException("user not found in db ...");
		}
		
		CustomUserDetails customUserDetails=new CustomUserDetails(user);
		return customUserDetails;
	}

}
