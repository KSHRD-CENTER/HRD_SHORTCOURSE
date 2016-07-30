package kh.com.kshrd.shortcourse.configurations;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import kh.com.kshrd.shortcourse.models.User;


public class UserSession {

	public static User getUserSession(){
		try{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			return (User) authentication.getPrincipal();
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
}
