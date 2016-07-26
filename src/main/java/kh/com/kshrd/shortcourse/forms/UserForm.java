package kh.com.kshrd.shortcourse.forms;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserForm {
	@JsonProperty("EMAIL")
	private String email;
	@JsonProperty("PASSWORD")
	private String password;
	@JsonProperty("ROLE")
	private String role;
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserForm [email=" + email + ", password=" + password + ", createdDate=" + " role=" + role
				+ "]";
	}
	
	public static class UpdateUserForm extends UserForm{
		@JsonProperty("ID")
		private int id;
		@JsonProperty("STATUS")
		private String status;
		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(int id) {
			this.id = id;
		}
		/**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}
		/**
		 * @param status the status to set
		 */
		public void setStatus(String status) {
			this.status = status;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "UpdateUserForm [id=" + id + ", status=" + status + "]";
		}
	}
}
