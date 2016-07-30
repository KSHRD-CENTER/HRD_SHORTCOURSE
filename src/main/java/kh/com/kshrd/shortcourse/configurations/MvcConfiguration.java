package kh.com.kshrd.shortcourse.configurations;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		//TODO: SWAGGER ROUTING
		registry.addViewController("/swagger").setViewName("swagger/index");
		
		//TODO: BACKEND OFFICE ROUTING
		registry.addViewController("/administrator/courses").setViewName("backoffice/courses/index");
		registry.addViewController("/administrator/students").setViewName("backoffice/students/index");
		registry.addViewController("/administrator/generations").setViewName("backoffice/generations/index");
		registry.addViewController("/administrator/shifts").setViewName("backoffice/shifts/index");
		registry.addViewController("/administrator/authentication").setViewName("backoffice/users/user-login");
		registry.addViewController("/administrator/transactions").setViewName("backoffice/payments/index");
		registry.addViewController("/administrator/dashboards").setViewName("backoffice/dashboard/index");
		registry.addViewController("/administrator/users").setViewName("backoffice/users/index");
		
		//TODO: FRONTEND OFFICE ROUTING
		registry.addViewController("/").setViewName("frontoffice/index");
		registry.addViewController("/student-list").setViewName("frontoffice/student-list");
		
		//TODO: FROM LOGIN ROUTING
		registry.addViewController("/administrator/login").setViewName("backoffice/users/user-login");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}
	
	@Bean
	public PlatformTransactionManager dataSourceTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

}