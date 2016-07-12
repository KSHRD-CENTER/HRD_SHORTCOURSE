package kh.com.kshrd.shortcourse.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Generation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1870141220999299997L;
	
	@JsonProperty("ID")
	private Long id;
	
	@JsonProperty("NAME")
	private String name;
	
	@JsonProperty("STATUS")
	private String status;
	
	@JsonIgnore
	private String isDefault;
	
	@JsonProperty("COURSE_TYPE")
	private CourseType courseType;
	
	@JsonIgnore
	private String createdDate;
	
	@JsonIgnore
	private User createdBy;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public CourseType getCourseType() {
		return courseType;
	}
	public void setCourseType(CourseType courseType) {
		this.courseType = courseType;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Generation [id=" + id + ", name=" + name + ", status=" + status + ", isDefault=" + isDefault
				+ ", courseType=" + courseType + ", createdDate=" + createdDate + ", createdBy=" + createdBy + "]";
	}
}
