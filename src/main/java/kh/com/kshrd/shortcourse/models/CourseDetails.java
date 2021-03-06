package kh.com.kshrd.shortcourse.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5120363671697847408L;
	
	@JsonProperty("ID")
	private Long id;

	//@JsonProperty("COURSE")
	@JsonIgnore
	private Course course;
	
	@JsonProperty("SHIFT")
	private Shift shift;
	
	//@JsonProperty("CREATED_DATE")
	@JsonIgnore
	private String createdDate;
	
	//@JsonProperty("CREATED_BY")
	@JsonIgnore
	private User createdBy;
	
	//@JsonProperty("UPDATED_DATE")
	@JsonIgnore
	private String updatedDate;
	
	//@JsonProperty("UPDATED_BY")
	@JsonIgnore
	private User updatedBy;
	
	//@JsonProperty("STATUS")
	@JsonIgnore
	private String status;
	
	@JsonProperty("START_DATE")
	private String startDate;
	
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Shift getShift() {
		return shift;
	}
	public void setShift(Shift shift) {
		this.shift = shift;
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
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public User getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "CourseDetails [id=" + id + ", course=" + course + ", shift=" + shift + ", createdDate=" + createdDate
				+ ", createdBy=" + createdBy + ", updatedDate=" + updatedDate + ", updatedBy=" + updatedBy + ", status="
				+ status + ", startDate=" + startDate + "]";
	}
}
