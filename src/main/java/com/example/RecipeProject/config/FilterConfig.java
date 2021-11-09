package com.example.RecipeProject.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.RecipeProject.config.filter.MyFilter;

@Configuration
public class FilterConfig {
	
	@Bean
	public FilterRegistrationBean<MyFilter> filter1(){
		FilterRegistrationBean<MyFilter> bean = new FilterRegistrationBean<>(new MyFilter());
		bean.addUrlPatterns("/*"); //모든 요청에서 다 필터가 걸러라
		bean.setOrder(0); //낮은 번호가 필터중 가장 먼저
		return bean;
	
	}
}
