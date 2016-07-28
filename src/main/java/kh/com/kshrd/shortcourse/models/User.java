package kh.com.kshrd.shortcourse.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User implements Serializable, UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5066949843279447417L;

	@JsonProperty("ID")
	private Long id;
	
	@JsonProperty("EMAIL")
	private String email;
	
	@JsonProperty("PASSWORD")
	private String password;
	
	@JsonProperty("ROLE")
	private String role;
	
	@JsonProperty("CREATED_DATE")
	private String createdDate;
	
	//@JsonProperty("STATUS")
	@JsonIgnore
	private String status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", role=" + role + ", createdDate="
				+ createdDate + ", status=" + status + "]";
	}
	@Override
	@JsonProperty("AUTHORITIES")
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		return authorities;
	}
	@Override
	public String getUsername() {
		return email;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return (status.equals("1") ? true:false);
	}
}
