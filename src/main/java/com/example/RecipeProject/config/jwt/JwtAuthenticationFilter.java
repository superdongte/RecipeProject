package com.example.RecipeProject.config.jwt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.RecipeProject.auth.PrincipalDetails;
import com.example.RecipeProject.controller.dto.LoginReqDto;
import com.example.RecipeProject.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
		throws AuthenticationException{
		LoginReqDto loginReqDto = null;
		//1. 유저네임 패스워드를 받아서
		//2. 정상인지 아닌지확인
		System.out.println("jstathtcaiton: 진입");
		try {
			System.out.println("requestValue"+request.getInputStream());
			ObjectMapper om = new ObjectMapper(); //request에 있는 username과 패스워드를 피싱해서 자바object로 받기
			loginReqDto = om.readValue(request.getInputStream(), LoginReqDto.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("왜 안되는거지?");
		}
		System.out.println("JwtAuthenticationFilter" + loginReqDto);
		
		UsernamePasswordAuthenticationToken authenticationToken=
				new UsernamePasswordAuthenticationToken(
						loginReqDto.getUsername(), 
						loginReqDto.getPassword());
		System.out.println("JwtAuthenticationFilter: 토근생성 완료");
		
		Authentication authentication =
				authenticationManager.authenticate(authenticationToken);
		PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
		System.out.println("Authentication:" + principalDetails.getUser().getUsername());
		
		return authentication;
	}
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException{
		
		PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
		String jwtToken=JWT.create()
				.withSubject(principalDetails.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
				.withClaim("id", principalDetails.getUser().getId())
				.withClaim("username", principalDetails.getUser().getUsername())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		
		response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
		System.out.println("토근생성된건가"+JwtProperties.TOKEN_PREFIX+jwtToken);
		
//		json으로 보내기
		ObjectMapper om = new ObjectMapper();
		User userEntity = new User(principalDetails.getUser().getId(),principalDetails.getUser().getUsername(), principalDetails.getUser().getPassword(), 
				 principalDetails.getUser().getEmail(),principalDetails.getUser().getRoles());
		
		LoginReqDto cmRestDto = new LoginReqDto(userEntity.getUsername(), userEntity.getPassword(), userEntity.getRoles(),"suceess","code");
		String cmRestDtoJson = om.writeValueAsString(cmRestDto);
		System.out.println("저게 왜저기"+cmRestDto);
		PrintWriter out = response.getWriter();
		out.print(cmRestDtoJson);
		System.out.println(cmRestDtoJson);
		out.flush();
	} 
	
}
