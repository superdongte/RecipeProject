package com.example.RecipeProject.config.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.example.RecipeProject.config.auth.PrincipalDetails;
import com.example.RecipeProject.controller.dto.LoginReqDto;
import com.example.RecipeProject.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	
	
	public Authentication attempAuthentication(HttpServletRequest request, HttpServletResponse response)
		throws AuthenticationException{
		
		LoginReqDto loginReqDto = new LoginReqDto();
		//1. 유저네임 패스워드를 받아서
		//2. 정상인지 아닌지확인
		try {
			ObjectMapper om = new ObjectMapper();
			User user = om.readValue(request.getInputStream(), User.class);
			System.out.println(user);
			loginReqDto.setUsername(user.getUsername());
			loginReqDto.setPassword(user.getPassword());
			//로그인 실패시 null 리턴
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginReqDto.getUsername(), loginReqDto.getPassword());	
		
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("로그인 완료"+ principalDetails.getUsername());
		return authentication;
		} catch (Exception e) {
			return null;
		}
	}
	
}
