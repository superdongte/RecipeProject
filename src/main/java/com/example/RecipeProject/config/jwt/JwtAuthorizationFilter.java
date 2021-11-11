package com.example.RecipeProject.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.RecipeProject.auth.PrincipalDetails;
import com.example.RecipeProject.model.User;
import com.example.RecipeProject.repository.UserRepository;



public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

	private UserRepository userRepository;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
			UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository=userRepository;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws IOException, ServletException{
				
		String header = request.getHeader(JwtProperties.HEADER_STRING);
		
		//헤더가 있는지 확인
		if(header==null
				||!header.startsWith(JwtProperties.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		System.out.println("header :" + header);
		String token=request.getHeader(JwtProperties.HEADER_STRING)
				.replace(JwtProperties.TOKEN_PREFIX,"");
		
		String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
				.build().verify(token).getClaim("username").asString();
		if(username !=null) {
			User user=userRepository.findByUsername(username);
			PrincipalDetails principalDetails = new PrincipalDetails(user);
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					principalDetails,null,principalDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(request, response);
	}
}
