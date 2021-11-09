package com.example.RecipeProject.config.jwt;

public interface JwtProps {
	public static final String SUBJECT = "tourToken";
	public static final String SECRET = "seckey";
	public static final String AUTH = "Bearer ";
	public static final String HEADER = "Authorization";
	public static final Long EXPIRESAT = 60000*10L;
}
