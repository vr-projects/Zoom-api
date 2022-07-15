package com.userOnboard;


import javax.sql.DataSource;

import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication; 
import  org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

import com.userOnboard.model.User;


@SpringBootApplication
@MappedTypes(User.class)
@MapperScan("com.userOnboard.mapper")
public class UserDataApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(UserDataApplication.class, args); }

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(UserDataApplication.class);
	}

}


