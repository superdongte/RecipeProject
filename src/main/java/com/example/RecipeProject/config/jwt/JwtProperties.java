package com.example.RecipeProject.config.jwt;

public interface JwtProperties {
	String SECRET = "cors서버";
	int EXPIRATION_TIME = 864000000;
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
}
