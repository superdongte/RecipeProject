package com.example.RecipeProject.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.RecipeProject.repository.UserRepository;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

	private UserRepository userRepository;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager,UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository=userRepository;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws IOException, ServletException{
		
		System.out.println("인증여부 상관없이 필터링");
		
		String jwtHeder = request.getHeader(JwtProps.HEADER);
		System.out.println("jwtHeader" + jwtHeder);
		
		//헤더가 있는지 확인
		if(jwtHeder == null || !jwtHeder.startsWith("Bearer")) {
			chain.doFilter(request, response);
			return;
		}
		String username = null;
		//토큰 검증
		String jwtToken = request.getHeader(JwtProps.HEADER).replace(JwtProps.AUTH, "");
		System.out.println("========================걸리는 부분");
		
	}
}
