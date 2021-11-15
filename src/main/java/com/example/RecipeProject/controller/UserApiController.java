package com.example.RecipeProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeProject.model.User;
import com.example.RecipeProject.repository.UserRepository;

@RestController
public class UserApiController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/token")
	public String token() {
		return "<h1>token</h1>";
	}
	@PostMapping("/join")
	public User join(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return user;
	}
	@GetMapping("/api/user")
	public String user() {
		return "<h1>user</h1>";
	}
	@GetMapping("/api/admin")
	public String admin() {
		return "<h1>admin</h1>";
	}
	
	@GetMapping("/api/manager")
	public String manager() {
		return "<h1>manager</h1>";
	}
	
	
}
