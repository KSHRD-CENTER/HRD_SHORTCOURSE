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
		registry.addViewController("/administrator/reports").setViewName("backoffice/reports/index");
		
		//TODO: FRONT END OFFICE ROUTING SHORT COURSE
		registry.addViewController("/").setViewName("frontoffice/index");
		registry.addViewController("/student-list").setViewName("frontoffice/student-list");
		
		//TODO: FRONT END OFFICE ROUTING PRE COURSE
		registry.addViewController("/precourses").setViewName("frontoffice/pre-courses/index");
		registry.addViewController("/precourses/student-list").setViewName("frontoffice/pre-courses/student-list");
		
		//TODO: FROM LOGIN ROUTING
		registry.addViewController("/administrator/login").setViewName("backoffice/users/user-login");
		
		registry.addViewController("/404").setViewName("error/404");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/certificate/**").addResourceLocations("file:/opt/certificate/pdf/");
		registry.addResourceHandler("/payment/**").addResourceLocations("file:/opt/payment/pdf/");
	}
	
	@Bean
	public PlatformTransactionManager dataSourceTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

}