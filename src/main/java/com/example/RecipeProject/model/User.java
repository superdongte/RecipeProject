package com.example.RecipeProject.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name="user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id //id가 기본키
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false, length=50)
	private String username;
	private String password;
	private String email;
	private String roles;
	
	public List<String> getRoleList(){
		if(this.roles.length() >0) {
			return Arrays.asList(this.roles.split(","));
		}
		return new ArrayList<>();
	}
}