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
		super.addViewControllers(registry);
		registry.addViewController("/swagger").setViewName("swagger/index");
		registry.addViewController("/administrator/courses").setViewName("backoffice/courses/index");
		registry.addViewController("/administrator/students").setViewName("backoffice/students/index");
		registry.addViewController("/administrator/generations").setViewName("backoffice/generations/index");
		registry.addViewController("/administrator/shifts").setViewName("backoffice/shifts/index");
		registry.addViewController("/administrator/authentication").setViewName("backoffice/users/user-login");
		
		//TODO: FRONT OFFICE ROUTING
		registry.addViewController("/").setViewName("frontoffice/index");
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