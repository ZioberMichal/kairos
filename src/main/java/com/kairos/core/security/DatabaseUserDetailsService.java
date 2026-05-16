package com.kairos.core.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.kairos.core.exceptions.Exceptions;
import com.kairos.project.user.model.User;
import com.kairos.project.user.model.UserRepository;

@Component
public class DatabaseUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	public DatabaseUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw Exceptions.notFound(username);
		}
		return user;
	}
}
