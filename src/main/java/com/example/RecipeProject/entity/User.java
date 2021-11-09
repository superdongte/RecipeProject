package com.example.RecipeProject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="user")
public class User {
	@Id //id가 기본키
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false, length=50)
	private String username;
	private String password;
	private String email;
	private String role;
	
	public User(Long Id, String username, String email) {
		this.id = id;
		this.username = username;
		this.email = email;
	}
}