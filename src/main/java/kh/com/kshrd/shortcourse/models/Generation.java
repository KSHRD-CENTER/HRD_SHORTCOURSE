package kh.com.kshrd.shortcourse.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Generation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1870141220999299997L;
	
	@JsonProperty("ID")
	private Long id;
	
	@JsonProperty("GENERATION")
	private String generation;
	
	@JsonProperty("IS_DEFAULT")
	private String isDefault;
	
	@JsonProperty("COURSE_TYPE")
	private CourseType courseType;
	
	@JsonProperty("CREATED_DATE")
	private String createdDate;
	
	@JsonProperty("CREATED_BY")
	private User createdBy;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGeneration() {
		return generation;
	}
	public void setGeneration(String generation) {
		this.generation = generation;
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
	@Override
	public String toString() {
		return "Generation [id=" + id + ", generation=" + generation + ", isDefault=" + isDefault + ", courseType="
				+ courseType + ", createdDate=" + createdDate + ", createdBy=" + createdBy + "]";
	}
}
