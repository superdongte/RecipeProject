package com.example.RecipeProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RecipeProject.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	 User findByUsername(String username);
}
