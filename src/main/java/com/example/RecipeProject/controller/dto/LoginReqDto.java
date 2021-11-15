package com.example.RecipeProject.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReqDto {
	private String username;
	private String password;
	private String roles;
	private String msg;
	private String code;
}
