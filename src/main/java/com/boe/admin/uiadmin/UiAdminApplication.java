package com.boe.admin.uiadmin;

import javax.validation.Validator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
@MapperScan(basePackages = "com.boe.admin.uiadmin.dao", annotationClass = Repository.class)
@EnableCaching
public class UiAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(UiAdminApplication.class, args);
	}
	
	@Bean
    public Validator getValidator() {
        return new LocalValidatorFactoryBean();
    }


}
