package kh.com.kshrd.shortcourse.filtering;

public class UserFilter {
	private String name;
	private String status;
	
	public UserFilter(){
		name = "";
		status = "";
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param string the status to set
	 */
	public void setStatus(String string) {
		this.status = string;
	}
}
